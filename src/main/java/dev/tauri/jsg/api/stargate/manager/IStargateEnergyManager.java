package dev.tauri.jsg.api.stargate.manager;

import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.api.stargate.network.StargatePos;
import dev.tauri.jsg.api.stargate.network.address.StargateAddressDynamic;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.power.JSGEnergyStorage;
import dev.tauri.jsgcore.common.power.JSGEnergyStorageWrapper;
import dev.tauri.jsgcore.common.power.general.EnergyRequiredToOperate;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

public interface IStargateEnergyManager<E extends JSGEnergyStorage> extends INBTSerializable<CompoundTag>, ITickable {
    E getStorage();

    JSGEnergyStorageWrapper getStorageForCaps();

    double getSecondsToClose();

    long getTransferredLastTick();

    boolean canOpenWormhole(EnergyRequiredToOperate energyRequiredToDial);

    default EnergyRequiredToOperate getEnergyRequiredToDial(Stargate<?> targetGate, StargateAddressDynamic address) {
        return getEnergyRequiredToDial(targetGate.getStargatePos(), address);
    }

    EnergyRequiredToOperate getEnergyRequiredToDial(@Nullable StargatePos targetGatePos, StargateAddressDynamic address);
}
