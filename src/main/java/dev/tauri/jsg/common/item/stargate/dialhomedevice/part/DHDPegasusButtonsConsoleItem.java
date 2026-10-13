package dev.tauri.jsg.common.item.stargate.dialhomedevice.part;

import dev.tauri.jsg.client.renderer.item.dialhomedevice.part.DHDPegasusButtonsConsoleBEWLR;
import dev.tauri.jsgcore.client.renderer.AbstractItemBEWLR;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DHDPegasusButtonsConsoleItem extends DHDAbstractPartItem {
    public DHDPegasusButtonsConsoleItem(Properties properties, List<Supplier<RegistryObjectWrapper<CreativeModeTab>>> tabs, boolean mandatory, int raycasterId) {
        super(properties, tabs, mandatory, raycasterId);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(AbstractItemBEWLR.create(DHDPegasusButtonsConsoleBEWLR::new));
    }
}
