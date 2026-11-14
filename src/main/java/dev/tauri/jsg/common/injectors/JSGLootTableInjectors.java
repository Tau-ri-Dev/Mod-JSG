package dev.tauri.jsg.common.injectors;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.common.loot.LootTableInjector;
import dev.tauri.jsgcore.mapping.JSGMapping;

import java.util.List;

public class JSGLootTableInjectors {
    public static void register() {
        // SCHEMATICS

        var tables = List.of("desert_pyramid", "desert_well", "ocean_ruin_cold", "ocean_ruin_warm");
        tables.forEach(t -> LootTableInjector.Builder
                .forTargets(JSGMapping.rl("minecraft", "archaeology/" + t))
                .clearPoolFirst()
                .add(new LootTableInjector.TableAddition(JSGMapping.rl("minecraft", "archaeology/" + t), JSGMapping.rl(JSG.MOD_ID, "archaeology/" + t)))
                .submit()
        );
    }
}
