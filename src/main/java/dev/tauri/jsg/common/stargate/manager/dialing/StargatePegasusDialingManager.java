package dev.tauri.jsg.common.stargate.manager.dialing;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.integration.StargateComputerEvents;
import dev.tauri.jsg.api.registry.JSGScheduledTaskTypes;
import dev.tauri.jsg.api.stargate.ChevronEnum;
import dev.tauri.jsg.api.stargate.EnumStargateState;
import dev.tauri.jsg.api.stargate.animation.EnumDialingType;
import dev.tauri.jsg.api.stargate.exception.StargateException;
import dev.tauri.jsg.api.stargate.result.StargateChevronEngageResult;
import dev.tauri.jsg.common.advancements.JSGCriterions;
import dev.tauri.jsg.common.blockentity.stargate.StargatePegasusBaseBE;
import dev.tauri.jsg.common.stargate.StargateAddressDialSequence;
import dev.tauri.jsg.common.stargate.animation.chevron.StargatePegasusChevronsState;
import dev.tauri.jsg.common.stargate.animation.incoming.IncomingAnimation;
import dev.tauri.jsg.common.stargate.animation.incoming.PegasusIncomingAnimation;
import dev.tauri.jsg.common.stargate.animation.spinning.ClassicSpinHelper;
import dev.tauri.jsg.common.stargate.animation.spinning.PegasusSpinHelper;
import dev.tauri.jsgcore.common.advancement.JSGCriterion;
import dev.tauri.jsgcore.common.entity.ScheduledTask;
import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

import static dev.tauri.jsg.common.stargate.animation.chevron.StargateChevronsState.A_CHEVRON_CLOSE_AFTER_ACTIVATE_DELAY;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public class StargatePegasusDialingManager extends StargateClassicDialingManager<StargatePegasusBaseBE> {
    public StargatePegasusDialingManager(StargatePegasusBaseBE stargate) {
        super(stargate);
    }

    @Override
    public Optional<JSGCriterion> getActivationAdvancementCriterion() {
        return Optional.of(JSGCriterions.ACTIVATE_PEGASUS_GATE);
    }

    @Override
    public ClassicSpinHelper generateSpinHelper() {
        return new PegasusSpinHelper(stargate, this::onRingStopSpinning);
    }

    @Override
    protected StargateAddressDialSequence.DialNextConsumer getAddressDialSequenceSymbolConsumer() {
        return (symbol, noEnergy, ignoreMaxChevrons, dialingType) -> {
            if (symbol == null) {
                if (dialingType == EnumDialingType.FAST)
                    engageSymbolDHD(stargate.getSymbolType().getBRB(), noEnergy, ignoreMaxChevrons);
                else
                    attemptOpenDialed();
                this.addressDialSequence = null;
                this.stargate.setChanged();
                return;
            }
            if (dialingType == EnumDialingType.NORMAL) {
                engageSymbolBySpin(symbol, noEnergy, ignoreMaxChevrons);
                return;
            }
            engageSymbolDHD(symbol, noEnergy, ignoreMaxChevrons);
        };
    }

    @Override
    protected void onRingStopSpinning(CompoundTag stopSpinData) {
        if (stargate.getStargateLevel() == null || stargate.getStargateLevel().isClientSide()) return;
        if (getStargateState().dialingComputer())
            setStargateState(EnumStargateState.IDLE);
        if (stopSpinData.contains("symbol")) {
            var symbol = stargate.getSymbolType().valueOf(stopSpinData.getInt("symbol"));
            stargate.getStateManager().getChevronsState().scheduleChevronActivate(0, getNextChevron(symbol, false, stopSpinData.getBoolean("ignoreMaxChevrons")), symbol, getStargateState().dialingComputer(), false, true, stopSpinData.getBoolean("noEnergy"), stopSpinData.getBoolean("ignoreMaxChevrons"));
        }
    }

    @Override
    public @Nullable StargateChevronEngageResult onChevronActivates(CompoundTag customData) {
        if (!customData.contains("symbol"))
            return null;
        var symbol = stargate.getSymbolType().valueOf(customData.getInt("symbol"));
        if (symbol == null)
            return StargateChevronEngageResult.BLOCKED_BY_EVENT;
        var result = engageSymbolInternal(symbol, customData.getBoolean("noxDialing"), customData.getBoolean("noEnergy"), customData.getBoolean("ignoreMaxChevrons"));
        if (!result.ok())
            return result;
        StargateComputerEvents.CHEVRON_ENGAGED.apply(StargateComputerEvents.ChevronEvent.Source.BY_SPIN, stargate.getPointOfOrigin(), symbol, getNextChevron(symbol, true, customData.getBoolean("ignoreMaxChevrons")), getDialedAddressSize()).sendVia(stargate);
        if (customData.getBoolean("checkConnection") && customData.getBoolean("isFinal") && !getConnection().getStatus().waiting())
            return StargateChevronEngageResult.FAILED_FAIL_GATE;
        if (addressDialSequence != null && !getStargateState().dialing())
            addTask(new ScheduledTask(JSGScheduledTaskTypes.STARGATE_DIAL_NEXT, A_CHEVRON_CLOSE_AFTER_ACTIVATE_DELAY + 10));
        ((StargatePegasusChevronsState) stargate.getStateManager().getChevronsState())
                .activateSlot(StargatePegasusChevronsState.slotFromChevron(ChevronEnum.valueOf(customData.getInt("chevron"))), dialedAddress.getLast());
        return StargateChevronEngageResult.OK;
    }

    @Override
    public StargateChevronEngageResult engageSymbolDHD(SymbolInterface symbol, boolean noEnergy, boolean ignoreMaxChevrons) {
        var result = engageSymbolToBuffer(symbol, noEnergy, ignoreMaxChevrons);
        if (result != StargateChevronEngageResult.OK) return result;
        var dhd = stargate.getLinkedDevice();
        if (stargate.isLinkedAndDHDOperational() && dhd != null) {
            dhd.activateSymbol(symbol);
        }
        if (addressDialSequence != null)
            addTask(new ScheduledTask(JSGScheduledTaskTypes.STARGATE_DIAL_NEXT, 10));
        return StargateChevronEngageResult.OK;
    }

    @Override
    public boolean engageSymbolBySpin(SymbolInterface symbol, boolean noEnergy, boolean ignoreMaxChevrons) {
        if (!getStargateState().idle() && !getStargateState().dialingDHD()) return false;
        if (isFinalActive) return false;
        var spinHelper = (PegasusSpinHelper) getSpinHelper();
        if (spinHelper.isSpinning()) return false;
        var nextChevron = getNextChevron(symbol, false, ignoreMaxChevrons);
        var currentChevron = dialedAddress.size() > 0 ? getNextChevron(dialedAddress.getLast(), true, ignoreMaxChevrons) : ChevronEnum.getFinal();
        var r = spinHelper.moveToAndEngage(symbol, nextChevron, currentChevron, noEnergy, ignoreMaxChevrons, !stargate.getSoundManager().isRingRollPlaying(), addressBuffer.first().size() < (addressBuffer.first().contains(stargate.getSymbolType().getBRB()) ? 3 : 2) || nextChevron.isFinal());
        if (r.first()) {
            if (!getStargateState().dialingDHD())
                setStargateState(EnumStargateState.DIALING_COMPUTER);
            if (isLockChevron(symbol, true, ignoreMaxChevrons)) {
                getConnection().runOnConnected((conn, sg) -> ((StargateAbstractDialingManager<?>) sg.getDialingManager()).runIncomingWormhole(dialedAddress.addOriginIfMissingAndImmutable().size(), r.second().intValue()));
            }
        }
        return r.first();
    }

    @Override
    public StargateChevronEngageResult engageCurrentSymbol(boolean ignoreMaxChevrons, boolean tryOpenAfter) {
        JSG.logger.error("", new StargateException("Tried to engage current top symbol on pegasus gate. This will not work...", stargate));
        return StargateChevronEngageResult.BLOCKED_BY_EVENT;
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
        getSpinHelper().stopSpinning(true);
        var dhd = stargate.getLinkedDevice();
        if (stargate.isLinkedAndDHDOperational() && dhd != null) {
            dhd.clearSymbols();
        }
    }

    @Override
    protected void onWormholeDisconnected() {
        super.onWormholeDisconnected();
        getSpinHelper().stopSpinning(true);
        stargate.getStateManager().getChevronsState().scheduleChevronsDimAll(0, true);
        stargate.setChanged();
        var dhd = stargate.getLinkedDevice();
        if (stargate.isLinkedAndDHDOperational() && dhd != null) {
            dhd.clearSymbols();
        }
    }

    @Override
    protected void onGateOpen(boolean initiating) {
        super.onGateOpen(initiating);
        getSpinHelper().stopSpinning(true);
        stargate.setChanged();
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
    protected IncomingAnimation<?> getIncomingAnimation(CompoundTag tag) {
        return new PegasusIncomingAnimation(this, tag);
    }

    @Override
    protected IncomingAnimation<?> getIncomingAnimation(int addressSize, int duration) {
        return new PegasusIncomingAnimation(stargate.getTime(), this, addressSize, duration);
    }
}
