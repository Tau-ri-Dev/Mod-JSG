package dev.tauri.jsg.common.datafixer;

import dev.tauri.jsgcore.JSGCore;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.List;

public class JSGBlockEntitiesDataFixer {
    public static void fixMappings(List<MissingMappingsEvent.Mapping<BlockEntityType<?>>> listToFix) {
        listToFix.forEach(mapping -> {
            var newKey = JSGMapping.rl(JSGCore.MOD_ID, mapping.getKey().getPath());
            var coreBE = ForgeRegistries.BLOCK_ENTITY_TYPES.getValue(newKey);
            if (coreBE == null) {
                mapping.warn();
                return;
            }
            mapping.remap(coreBE);
        });
    }
}
