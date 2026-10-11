package dev.tauri.jsg.common.registry;

import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.client.screen.inventory.StargateContainerGui;
import dev.tauri.jsg.common.container.StargateContainer;
import dev.tauri.jsgcore.common.registry.helper.RegistryHelper;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.world.inventory.MenuType;

public class JSGMenuTypes {

    public static final RegistryObjectWrapper<MenuType<StargateContainer>> STARGATE_MENU_TYPE = JSGApi.REGISTRY_HELPER.menu().register("stargate_container", RegistryHelper.menu(StargateContainer::new));

    public static void init() {
        JSGApi.REGISTRY_HELPER.guiRegister(() -> {
            RegistryHelper.bindScreenToMenu(STARGATE_MENU_TYPE.get(), StargateContainerGui::new);
        });
    }
}
