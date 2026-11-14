package dev.tauri.jsg.common.registry;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;

public class JSGFeatureFlags {
    public static final FeatureFlag GATE_PRECEDENCE_EXPERIMENT = FeatureFlags.REGISTRY.getFlag(JSGMapping.rl(JSG.MOD_ID, "gate_precedence_experiment"));
}
