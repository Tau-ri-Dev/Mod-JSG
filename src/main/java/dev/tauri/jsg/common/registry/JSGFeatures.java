package dev.tauri.jsg.common.registry;

import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.worldgen.feature.OreSpireFeature;
import dev.tauri.jsg.common.worldgen.feature.config.OreSpireConfig;
import dev.tauri.jsgcore.common.registry.helper.DeferredRegisterWrapper;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.world.level.levelgen.feature.Feature;

public class JSGFeatures {
    private static final DeferredRegisterWrapper<Feature<?>> REGISTER = JSGApi.REGISTRY_HELPER.feature();

    public static final RegistryObjectWrapper<Feature<?>> ORE_SPIRE = register("ore_spire", new OreSpireFeature(OreSpireConfig.CODEC));

    protected static RegistryObjectWrapper<Feature<?>> register(String name, Feature<?> f) {
        return REGISTER.register(name, () -> f);
    }

    public static void init() {
    }
}
