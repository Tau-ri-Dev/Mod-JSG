package dev.tauri.jsg.common.stargate.manager.dialing;

import dev.tauri.jsg.api.config.ingame.option.StargateConfigOptions;
import dev.tauri.jsg.api.integration.StargateComputerEvents;
import dev.tauri.jsg.api.registry.JSGScheduledTaskTypes;
import dev.tauri.jsg.api.stargate.ChevronEnum;
import dev.tauri.jsg.api.stargate.EnumStargateState;
import dev.tauri.jsg.api.stargate.animation.EnumSpinDirection;
import dev.tauri.jsg.api.stargate.result.StargateChevronEngageResult;
import dev.tauri.jsg.api.stargate.result.StargateManualDialingAttemptResult;
import dev.tauri.jsg.common.blockentity.stargate.StargateMilkyWayBaseBE;
import dev.tauri.jsg.common.registry.tags.JSGItemTags;
import dev.tauri.jsg.common.stargate.animation.incoming.IncomingAnimation;
import dev.tauri.jsg.common.stargate.animation.incoming.MilkyWayIncomingAnimation;
import dev.tauri.jsgcore.common.entity.ScheduledTask;
import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static dev.tauri.jsg.common.stargate.animation.chevron.StargateChevronsState.A_CHEVRON_ACTIVATE_AFTER_OPEN_DELAY;
import static dev.tauri.jsg.common.stargate.animation.chevron.StargateChevronsState.A_CHEVRON_CLOSE_AFTER_ACTIVATE_DELAY;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public class StargateMilkyWayDialingManager extends StargateClassicDialingManager<StargateMilkyWayBaseBE> {
    public static final long MANUAL_MAX_SPINNING_UPDATE_TIME = 5;
    public static final long MANUAL_ENGAGE_CHEVRON_AFTER_SPIN_STOP_TIME = 80;
    public static final long MANUAL_OPEN_GATE_AFTER_ORIGIN_ENGAGE_TIME = 100;

    protected long manualDialingLastUpdated = -1;

    protected ManualDialingState manualDialingState = ManualDialingState.IDLE;

    public StargateMilkyWayDialingManager(StargateMilkyWayBaseBE stargate) {
        super(stargate);
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
        if (level.isClientSide()) return;
        if (manualDialingState == ManualDialingState.IDLE) return;
        if (manualDialingState == ManualDialingState.WAITING_FOR_OPEN) {
            if ((level.getGameTime() - manualDialingLastUpdated) > MANUAL_OPEN_GATE_AFTER_ORIGIN_ENGAGE_TIME) {
                attemptOpenDialed();
                manualDialingState = ManualDialingState.IDLE;
                stargate.setChanged();
            }
            return;
        }
        if (getSpinHelper().isSpinning() && (level.getGameTime() - manualDialingLastUpdated) > MANUAL_MAX_SPINNING_UPDATE_TIME) {
            getSpinHelper().stopSpinning(true);
            return;
        }
        if (!getSpinHelper().isSpinning() && (level.getGameTime() - manualDialingLastUpdated) > MANUAL_ENGAGE_CHEVRON_AFTER_SPIN_STOP_TIME) {
            manualDialingState = ManualDialingState.IDLE;
            var result = engageCurrentSymbol(false);
            if (!result.ok()) return;
            var symbol = getSpinHelper().getCurrentTopSymbol();
            if (symbol.origin()) {
                manualDialingState = ManualDialingState.WAITING_FOR_OPEN;
                manualDialingLastUpdated = stargate.getTime();
            }
            stargate.setChanged();
        }
    }

    @Override
    public StargateChevronEngageResult engageSymbolDHD(SymbolInterface symbol, boolean noEnergy, boolean ignoreMaxChevrons) {
        if (!getStargateState().idle())
            return StargateChevronEngageResult.BUSY;
        var r = engageSymbolInternal(symbol, false, noEnergy, ignoreMaxChevrons);
        if (!r.ok()) {
            return r;
        }
        StargateComputerEvents.CHEVRON_ENGAGED.apply(StargateComputerEvents.ChevronEvent.Source.DHD, stargate.getPointOfOrigin(), symbol, getNextChevron(symbol, true, ignoreMaxChevrons), getDialedAddressSize()).sendVia(stargate);
        setStargateState(EnumStargateState.DIALING);
        if (r == StargateChevronEngageResult.OK_CONNECTED) {
            getConnection().runOnConnected((conn, sg) -> ((StargateAbstractDialingManager<?>) sg.getDialingManager()).runIncomingWormhole(dialedAddress.addOriginIfMissingAndImmutable().size(), 30));
        }
        int plusTime = new Random().nextInt(5);
        if (isLockChevron(symbol, ignoreMaxChevrons)) {
            if (stargate.getConfig().getValueOrDefault(StargateConfigOptions.MilkyWay.DHD_POO_LOCK)) {
                stargate.getStateManager().getChevronsState().scheduleChevronFinalOpenAndActivate(5 + plusTime, null, true);
                if (addressDialSequence != null)
                    addTask(new ScheduledTask(JSGScheduledTaskTypes.STARGATE_DIAL_NEXT, 15 + plusTime + A_CHEVRON_CLOSE_AFTER_ACTIVATE_DELAY + A_CHEVRON_ACTIVATE_AFTER_OPEN_DELAY));
            } else {
                stargate.getStateManager().getChevronsState().scheduleChevronActivate(10 + plusTime, ChevronEnum.getFinal(), null, true);
                if (addressDialSequence != null)
                    addTask(new ScheduledTask(JSGScheduledTaskTypes.STARGATE_DIAL_NEXT, 15 + plusTime));
            }
        } else {
            stargate.getStateManager().getChevronsState().scheduleChevronActivate(10 + plusTime, getNextChevron(symbol, true, ignoreMaxChevrons), null, true);
            if (addressDialSequence != null)
                addTask(new ScheduledTask(JSGScheduledTaskTypes.STARGATE_DIAL_NEXT, 15 + plusTime));
        }
        stargate.setChanged();
        return r;
    }


    @Override
    protected IncomingAnimation<?> getIncomingAnimation(CompoundTag tag) {
        return new MilkyWayIncomingAnimation(this, tag);
    }

    @Override
    protected IncomingAnimation<?> getIncomingAnimation(int addressSize, int duration) {
        return new MilkyWayIncomingAnimation(stargate.getTime(), this, addressSize, duration);
    }

    @Override
    protected void clearIncomingWormholeOnSelf() {
        getSpinHelper().stopSpinning(true);
        stargate.getStateManager().getChevronsState().scheduleChevronsDimAll(0, true);
    }

    @Override
    public void runIncomingWormhole(int addressSize, int duration) {
        super.runIncomingWormhole(addressSize, duration);
        var dhd = stargate.getLinkedDevice();
        if (stargate.isLinkedAndDHDOperational() && dhd != null) {
            dhd.clearSymbols();
        }
    }

    @Override
    protected void failGate() {
        super.failGate();
        var dhd = stargate.getLinkedDevice();
        if (stargate.isLinkedAndDHDOperational() && dhd != null) {
            dhd.clearSymbols();
        }
    }

    @Override
    protected void onWormholeDisconnected() {
        super.onWormholeDisconnected();
        getSpinHelper().stopSpinning(true);
        var dhd = stargate.getLinkedDevice();
        if (stargate.isLinkedAndDHDOperational() && dhd != null) {
            dhd.clearSymbols();
        }
    }

    @Override
    protected void onGateOpen(boolean initiating) {
        super.onGateOpen(initiating);
        getSpinHelper().stopSpinning(true);
        var dhd = stargate.getLinkedDevice();
        if (stargate.isLinkedAndDHDOperational() && dhd != null) {
            dhd.activateSymbol(stargate.getSymbolType().getBRB());
        }
    }

    @Override
    protected StargateChevronEngageResult engageSymbolInternal(SymbolInterface symbol, boolean isNox, boolean noEnergy, boolean ignoreMaxChevrons) {
        var result = super.engageSymbolInternal(symbol, isNox, noEnergy, ignoreMaxChevrons);
        if (result.ok()) {
            var dhd = stargate.getLinkedDevice();
            if (stargate.isLinkedAndDHDOperational() && dhd != null) {
                dhd.activateSymbol(symbol);
            }
        }
        return result;
    }

    @Override
    public boolean canDialManuallyByHand(Player player, ItemStack heldItem, InteractionHand usedHand) {
        return heldItem.is(JSGItemTags.DIALING_GLOVES);
    }

    @Override
    public StargateManualDialingAttemptResult updateManualDialingByHand(Player player, ItemStack heldItem, InteractionHand usedHand) {
        if (!getStargateState().idle()) return StargateManualDialingAttemptResult.BUSY;
        if (manualDialingState == ManualDialingState.WAITING_FOR_OPEN)
            return StargateManualDialingAttemptResult.BUSY;
        if (getSpinHelper().isSpinning() && manualDialingState == ManualDialingState.IDLE)
            return StargateManualDialingAttemptResult.BUSY;

        // TODO: Make the test value not static
        if (stargate.getEnergyManager().getStorage().getTrueEnergyStored() < 1000)
            return StargateManualDialingAttemptResult.NO_ENERGY;

        manualDialingLastUpdated = stargate.getTime();
        manualDialingState = ManualDialingState.DIALING;
        stargate.setChanged();
        if (getSpinHelper().isSpinning())
            return StargateManualDialingAttemptResult.SPIN_CONTINUES;
        var result = getSpinHelper().rotateFreely(EnumSpinDirection.valueOf(player.isShiftKeyDown()), 0.3f);
        if (!result) return StargateManualDialingAttemptResult.BUSY;
        return StargateManualDialingAttemptResult.SPIN_STARTED;
    }

    public enum ManualDialingState {
        IDLE,
        DIALING,
        WAITING_FOR_OPEN
    }
}
