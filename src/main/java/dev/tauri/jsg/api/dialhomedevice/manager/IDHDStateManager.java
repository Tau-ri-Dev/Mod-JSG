package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.animation.IButtonsState;
import dev.tauri.jsg.api.item.IDHDPartItem;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.blockentity.StateProviderInterface;
import dev.tauri.jsgcore.common.nbt.LegacyNBTSerializable;
import dev.tauri.jsgcore.common.packet.TargetPoint;
import net.minecraft.world.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

public interface IDHDStateManager extends LegacyNBTSerializable, StateProviderInterface, ITickable {
    IButtonsState getButtonsState();

    TargetPoint getTargetPoint();

    @ParametersAreNonnullByDefault
    ItemStack getAssembledPart(IDHDPartItem part);

    @ParametersAreNonnullByDefault
    ItemStack disassemblePart(IDHDPartItem part, boolean simulate);

    @ParametersAreNonnullByDefault
    boolean isDHDPartAssembled(IDHDPartItem part);

    @ParametersAreNonnullByDefault
    void assemblePart(IDHDPartItem part, ItemStack stack);
}
