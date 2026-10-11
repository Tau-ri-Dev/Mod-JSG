package dev.tauri.jsg.common.datafixer;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsgcore.JSGCore;
import dev.tauri.jsgcore.common.registry.CoreItems;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.List;

public class JSGItemsDataFixer {
    public static void fixMappings(List<MissingMappingsEvent.Mapping<Item>> listToFix) {
        listToFix.forEach(mapping -> {

            if (mapping.getKey().equals(JSGMapping.rl(JSG.MOD_ID, "crystal_control_dhd"))) {
                mapping.remap(JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL.get());
                return;
            }
            if (mapping.getKey().equals(JSGMapping.rl(JSG.MOD_ID, "crystal_control_pegasus_dhd"))) {
                mapping.remap(JSGItems.PEGASUS_DHD_MAIN_CRYSTAL.get());
                return;
            }

            if (mapping.getKey().equals(JSGMapping.rl(JSG.MOD_ID, "dhd_brb"))) {
                mapping.remap(JSGItems.MILKYWAY_DHD_ACTIVATION_BUTTON.get());
                return;
            }
            if (mapping.getKey().equals(JSGMapping.rl(JSG.MOD_ID, "dhd_bbb"))) {
                mapping.remap(JSGItems.PEGASUS_DHD_ACTIVATION_BUTTON.get());
                return;
            }

            if (mapping.getKey().equals(JSGMapping.rl(JSG.MOD_ID, "capacitor_block"))) {
                mapping.remap(CoreItems.CRYSTAL_ENERGY_BASIC.get());
                return;
            }
            if (mapping.getKey().equals(JSGMapping.rl(JSG.MOD_ID, "capacitor_block_creative"))) {
                mapping.remap(CoreItems.CRYSTAL_ENERGY_CREATIVE.get());
                return;
            }
            var newKey = JSGMapping.rl(JSGCore.MOD_ID, mapping.getKey().getPath());
            var coreItem = ForgeRegistries.ITEMS.getValue(newKey);
            if (coreItem == null) {
                mapping.warn();
                return;
            }
            mapping.remap(coreItem);
        });
    }
}
