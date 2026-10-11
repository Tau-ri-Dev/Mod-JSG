package dev.tauri.jsg.common.capability;

import dev.tauri.jsgcore.common.definition.material.MaterialType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import org.jetbrains.annotations.NotNull;

public class DHDFluidHandlerItemStack extends FluidHandlerItemStack {
    /**
     * @param container The container itemStack, data is stored on it directly as NBT.
     * @param capacity  The maximum capacity of this fluid tank.
     */
    public DHDFluidHandlerItemStack(@NotNull ItemStack container, int capacity) {
        super(container, capacity);
        if (container.getOrCreateTag().contains("savedCapacity"))
            this.capacity = container.getOrCreateTag().getInt("savedCapacity");
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
        container.getOrCreateTag().putInt("savedCapacity", capacity);
    }

    @Override
    public void setFluid(FluidStack fluid) {
        super.setFluid(fluid);
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public boolean isFluidValid(int tank, @NotNull FluidStack stack) {
        return stack.getFluid() == MaterialType.REFINED_NAQUADAH.getFluid().get();
    }
}
