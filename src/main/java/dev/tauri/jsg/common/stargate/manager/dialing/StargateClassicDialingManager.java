package dev.tauri.jsg.common.stargate.manager.dialing;

import dev.tauri.jsg.api.registry.JSGScheduledTaskTypes;
import dev.tauri.jsg.api.stargate.EnumStargateState;
import dev.tauri.jsg.api.stargate.iris.EnumIrisMode;
import dev.tauri.jsg.api.stargate.result.StargateChevronEngageResult;
import dev.tauri.jsg.api.stargate.result.StargateOpenResult;
import dev.tauri.jsg.common.blockentity.stargate.StargateClassicBaseBE;
import dev.tauri.jsg.common.stargate.animation.spinning.ClassicSpinHelper;
import dev.tauri.jsgcore.common.entity.ScheduledTaskType;
import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
public abstract class StargateClassicDialingManager<SG extends StargateClassicBaseBE<?>> extends StargateAbstractDialingManager<SG> {
    protected final ClassicSpinHelper spinHelper;
    protected Pair<List<SymbolInterface>, Pair<Boolean, Boolean>> addressBuffer = Pair.of(new ArrayList<>(), Pair.of(false, false));
    protected boolean canDialNextFromBuffer = true;

    public StargateClassicDialingManager(SG stargate) {
        super(stargate);
        this.spinHelper = generateSpinHelper();
    }

    @Override
    public void allowDialNextFromBuffer() {
        canDialNextFromBuffer = true;
        stargate.setChanged();
    }

    @Override
    public void clearDialingBuffer() {
        allowDialNextFromBuffer();
        addressBuffer.first().clear();
        stargate.setChanged();
    }

    public ClassicSpinHelper generateSpinHelper() {
        return new ClassicSpinHelper(stargate, this::onRingStopSpinning);
    }

    public ClassicSpinHelper getSpinHelper() {
        return spinHelper;
    }

    public StargateChevronEngageResult canAddSymbolToBuffer(SymbolInterface symbol, boolean ignoreMaxChevrons) {
        if (isFinalActive && !symbol.brb()) return StargateChevronEngageResult.ADDRESS_FULL;
        if (addressBuffer.first().contains(symbol))
            return StargateChevronEngageResult.ALREADY_ENGAGED;
        if (symbol.brb()) return StargateChevronEngageResult.OK;
        if ((dialedAddress.size() + addressBuffer.first().size() - (addressBuffer.first().contains(stargate.getSymbolType().getBRB()) || symbol.brb() ? 1 : 0)) >= (ignoreMaxChevrons ? 9 : stargate.getMaxChevrons())) {
            return StargateChevronEngageResult.ADDRESS_FULL;
        }
        return StargateChevronEngageResult.OK;
    }

    public StargateChevronEngageResult engageSymbolToBuffer(SymbolInterface symbol, boolean noEnergy, boolean ignoreMaxChevrons) {
        var r = canAddSymbolToBuffer(symbol, ignoreMaxChevrons);
        if (r != StargateChevronEngageResult.OK) return r;
        setStargateState(EnumStargateState.DIALING);
        addressBuffer = Pair.of(addressBuffer.first(), Pair.of(noEnergy, ignoreMaxChevrons));
        addressBuffer.first().add(symbol);
        return StargateChevronEngageResult.OK;
    }

    @Override
    public void tick(Level level) {
        super.tick(level);
        getSpinHelper().tick(level);
        if (level.isClientSide) return;
        if (canDialNextFromBuffer && !addressBuffer.first().isEmpty() && level.getGameTime() % 10 == 0) {
            var symbol = addressBuffer.first().get(0);
            if (symbol == null) {
                addressBuffer.first().remove(0);
                stargate.setChanged();
                return;
            }
            if (symbol.brb()) {
                addressBuffer.first().clear();
                canDialNextFromBuffer = false;
                attemptOpenDialed();
                return;
            }
            if (engageSymbolBySpin(symbol, addressBuffer.second().first(), addressBuffer.second().second())) {
                canDialNextFromBuffer = false;
                addressBuffer.first().remove(0);
                stargate.setChanged();
            } else {
                addressBuffer.first().remove(0);
                stargate.setChanged();
            }
        }
    }

    @Override
    public void onLoad(Level level) {
        super.onLoad(level);
        getSpinHelper().onLoad(level);
    }

    @Override
    protected StargateOpenResult getCanOpenDialed() {
        if (stargate.isGateBurried()) {
            return StargateOpenResult.GATE_BURRIED;
        }
        if (getConnection().callConnected((c, sg) -> sg.isGateBurried(), () -> false))
            return StargateOpenResult.TARGET_GATE_BURRIED;
        return super.getCanOpenDialed();
    }

    @Override
    protected boolean openGate() {
        if (!super.openGate())
            return false;
        stargate.getSoundManager().updateRingRollSound(false);
        stargate.tryHeatUp(8);
        canDialNextFromBuffer = true;
        addressBuffer.first().clear();
        stargate.setChanged();
        return true;
    }

    @Override
    protected void onWormholeDisconnected() {
        super.onWormholeDisconnected();
        stargate.getSoundManager().updateRingRollSound(false);
        stargate.getIrisManager().onGateDisconnected();
        canDialNextFromBuffer = true;
        addressBuffer.first().clear();
        stargate.setChanged();
    }

    @Override
    protected void onIncoming(int addressSize, int duration) {
        super.onIncoming(addressSize, duration);
        stargate.getIrisManager().onIncomingWormhole();
    }

    @Override
    protected void clearIncomingWormholeOnSelf() {
        super.clearIncomingWormholeOnSelf();
        canDialNextFromBuffer = true;
        addressBuffer.first().clear();
        stargate.setChanged();
    }

    @Override
    public void runIncomingWormhole(int addressSize, int duration) {
        super.runIncomingWormhole(addressSize, duration);
        canDialNextFromBuffer = true;
        addressBuffer.first().clear();
        stargate.setChanged();
    }

    @Override
    protected void failGate() {
        super.failGate();
        stargate.getSoundManager().updateRingRollSound(false);
        canDialNextFromBuffer = true;
        addressBuffer.first().clear();
        stargate.setChanged();
    }

    @Override
    protected StargateChevronEngageResult engageSymbolInternal(SymbolInterface symbol, boolean isNox, boolean noEnergy, boolean ignoreMaxChevrons) {
        var result = super.engageSymbolInternal(symbol, isNox, noEnergy, ignoreMaxChevrons);
        canDialNextFromBuffer = true;
        stargate.setChanged();
        return result;
    }

    @Override
    public void executeTask(ScheduledTaskType scheduledTask, CompoundTag customData) {
        if (scheduledTask == JSGScheduledTaskTypes.STARGATE_CLOSE.get()) {
            super.executeTask(scheduledTask, customData);
            var iris = stargate.getIrisManager();
            if (iris.isIrisClosed() && iris.getIrisMode() == EnumIrisMode.AUTO)
                iris.toggleIris();
        } else if (scheduledTask == JSGScheduledTaskTypes.GATE_RING_ROLL.get()) {
            stargate.getSoundManager().updateRingRollSound(true);
        } else
            super.executeTask(scheduledTask, customData);
    }

    @Override
    public CompoundTag serializeNBT() {
        var compound = super.serializeNBT();
        compound.put("spinHelper", spinHelper.serializeNBT());
        compound.putIntArray("addressBufferAddress", addressBuffer.first().stream().map(SymbolInterface::getId).toList());
        compound.putBoolean("addressBufferNoEnergy", addressBuffer.second().first());
        compound.putBoolean("addressBufferIgnoreMaxChevrons", addressBuffer.second().second());
        compound.putBoolean("addressBufferCanDialNext", canDialNextFromBuffer);
        return compound;
    }

    @Override
    public void deserializeNBT(CompoundTag compound) {
        super.deserializeNBT(compound);
        spinHelper.deserializeNBT(compound.getCompound("spinHelper"));
        super.deserializeNBT(compound);
        var list = new ArrayList<SymbolInterface>();
        for (var i : compound.getIntArray("addressBufferAddress")) {
            list.add(stargate.getSymbolType().valueOf(i));
        }
        addressBuffer = Pair.of(list, Pair.of(compound.getBoolean("addressBufferNoEnergy"), compound.getBoolean("addressBufferIgnoreMaxChevrons")));
        canDialNextFromBuffer = compound.getBoolean("addressBufferCanDialNext");
    }
}
