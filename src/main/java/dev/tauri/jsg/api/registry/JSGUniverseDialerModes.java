package dev.tauri.jsg.api.registry;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.item.linkable.dialer.UniverseDialerMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDManualDialMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDMemoryMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDNearbyMode;
import dev.tauri.jsg.common.item.linkable.dialer.modes.UDStatusMode;
import dev.tauri.jsgcore.common.registry.helper.DeferredRegisterWrapper;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

public class JSGUniverseDialerModes {
    public static final DeferredRegisterWrapper<UniverseDialerMode> REGISTER = DeferredRegisterWrapper.of(DeferredRegister.create(JSGRegistries.UNIVERSE_DIALER_MODES, JSG.MOD_ID));

    public static final RegistryObjectWrapper<UDNearbyMode> NEARBY = REGISTER.register("nearby", UDNearbyMode::new);
    public static final RegistryObjectWrapper<UDMemoryMode> MEMORY = REGISTER.register("memory", UDMemoryMode::new);
    public static final RegistryObjectWrapper<UDStatusMode> STATUS = REGISTER.register("status", UDStatusMode::new);
    public static final RegistryObjectWrapper<UDManualDialMode> MANUAL_DIALING = REGISTER.register("manual_dialing", UDManualDialMode::new);

    public static void init() {
    }

    public static void register(IEventBus eventBus) {
        REGISTER.register(eventBus);
    }
}
