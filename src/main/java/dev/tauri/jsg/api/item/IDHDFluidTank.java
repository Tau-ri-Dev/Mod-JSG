package dev.tauri.jsg.api.item;

import dev.tauri.jsg.common.capability.DHDFluidHandlerItemStack;
import net.minecraft.world.item.ItemStack;

public interface IDHDFluidTank extends IDHDPartItem {
    DHDFluidHandlerItemStack getFluidTankCapability(ItemStack stack);
}
