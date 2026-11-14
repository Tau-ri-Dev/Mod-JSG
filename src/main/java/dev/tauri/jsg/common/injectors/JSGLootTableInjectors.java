package dev.tauri.jsg.common.injectors;

public class JSGLootTableInjectors {
    public static void register() {
        // SCHEMATICS
        /*
        // TODO: Fix this!!
        var tables = List.of("desert_pyramid", "desert_wall", "ocean_ruin_cold", "ocean_ruin_warm");
        tables.forEach(t -> LootTableInjector.Builder
                .forTargets(JSGMapping.rl("minecraft", "archaeology/" + t))
                .add(new LootTableInjector.TableAddition(JSGMapping.rl("minecraft", "archaeology/" + t), JSGMapping.rl(JSG.MOD_ID, "loot_tables/archaeology/" + t)))
                .submit()
        );*/
    }
}
