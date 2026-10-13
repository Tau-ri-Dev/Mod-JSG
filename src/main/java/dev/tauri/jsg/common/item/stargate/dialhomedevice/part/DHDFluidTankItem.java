package dev.tauri.jsg.common.item.stargate.dialhomedevice.part;

import dev.tauri.jsg.api.item.IDHDFluidTank;
import dev.tauri.jsg.common.capability.DHDFluidHandlerItemStack;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

public class DHDFluidTankItem extends DHDAbstractPartItem implements IDHDFluidTank {
    public DHDFluidTankItem(Properties properties, List<Supplier<RegistryObjectWrapper<CreativeModeTab>>> tabs, boolean mandatory, int raycasterId) {
        super(properties, tabs, mandatory, raycasterId);
    }

    @Override
    public DHDFluidHandlerItemStack getTank(ItemStack stack) {
        return (DHDFluidHandlerItemStack) java.util.Objects.requireNonNull(stack.getCapability(net.neoforged.neoforge.capabilities.Capabilities.FluidHandler.ITEM));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);

    }
}
