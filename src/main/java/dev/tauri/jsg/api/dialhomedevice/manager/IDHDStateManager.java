package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.animation.IButtonsState;
import dev.tauri.jsg.api.item.IDHDPartItem;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.blockentity.StateProviderInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.ParametersAreNonnullByDefault;

public interface IDHDStateManager extends INBTSerializable<CompoundTag>, StateProviderInterface, ITickable {
    IButtonsState getButtonsState();

    PacketDistributor.TargetPoint getTargetPoint();

    @ParametersAreNonnullByDefault
    ItemStack getAssembledPart(IDHDPartItem part);

    @ParametersAreNonnullByDefault
    ItemStack disassemblePart(IDHDPartItem part, boolean simulate);

    @ParametersAreNonnullByDefault
    boolean isDHDPartAssembled(IDHDPartItem part);

    @ParametersAreNonnullByDefault
    void assemblePart(IDHDPartItem part, ItemStack stack);
}
