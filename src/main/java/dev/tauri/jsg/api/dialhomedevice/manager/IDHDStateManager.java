package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.animation.IButtonsState;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.blockentity.StateProviderInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.PacketDistributor;

public interface IDHDStateManager extends INBTSerializable<CompoundTag>, StateProviderInterface, ITickable {
    IButtonsState getButtonsState();

    PacketDistributor.TargetPoint getTargetPoint();
}
