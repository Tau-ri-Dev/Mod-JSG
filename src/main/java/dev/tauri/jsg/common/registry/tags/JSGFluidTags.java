package dev.tauri.jsg.common.registry.tags;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class JSGFluidTags {
    public static TagKey<Fluid> DHD_FUEL = tag("dhd_fuel");

    private static TagKey<Fluid> tag(String name) {
        return FluidTags.create(JSGMapping.rl(JSG.MOD_ID, name));
    }
}
