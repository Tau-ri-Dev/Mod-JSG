package dev.tauri.jsg.common.item.stargate.dialhomedevice.part;

import dev.tauri.jsg.api.item.IDHDFluidTank;
import dev.tauri.jsg.client.renderer.item.dialhomedevice.part.DHDFluidTankBEWLR;
import dev.tauri.jsg.common.capability.DHDFluidHandlerItemStack;
import dev.tauri.jsgcore.client.renderer.AbstractItemBEWLR;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DHDFluidTankItem extends DHDAbstractPartItem implements IDHDFluidTank {
    public DHDFluidTankItem(Properties properties, List<Supplier<RegistryObjectWrapper<CreativeModeTab>>> tabs, boolean mandatory, int raycasterId) {
        super(properties, tabs, mandatory, raycasterId);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AbstractItemBEWLR.create(DHDFluidTankBEWLR::new));
    }

    @Override
    public DHDFluidHandlerItemStack getFluidTankCapability(ItemStack stack) {
        return (DHDFluidHandlerItemStack) Objects.requireNonNull(stack.getCapability(Capabilities.FluidHandler.ITEM));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, components, tooltipFlag);
    }
}
