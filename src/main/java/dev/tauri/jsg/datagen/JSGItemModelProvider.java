package dev.tauri.jsg.datagen;

import com.mojang.datafixers.util.Pair;
import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.registry.JSGBlocks;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsgcore.common.item.JSGMusicDiscItem;
import dev.tauri.jsgcore.datagen.CoreItemModelProvider;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;

public class JSGItemModelProvider extends CoreItemModelProvider {
    public JSGItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, JSG.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //2D INVENTORY MODELS + BLOCKBENCH MODELS IN OTHER PERSPECTIVES
        differentInventoryItem(JSGItems.DIALING_GLOVES, true);

        //All INVENTORY OBJ MODELS
        List.of(JSGItems.DHD_NAQUADAH_TANK, JSGItems.MILKYWAY_DHD_BUTTONS_CONSOLE, JSGItems.PEGASUS_DHD_BUTTONS_CONSOLE,
                JSGItems.MILKYWAY_DHD_UPGRADES_COVER, JSGItems.PEGASUS_DHD_UPGRADES_COVER, JSGItems.GDO,
                JSGItems.ADMIN_CONTROLLER, JSGItems.UNIVERSE_DIALER
        ).forEach(regObj -> {
            var allContext = ItemDisplayContext.values();

            itemOBJModel(regObj, allContext);
        });

        List.of(JSGBlocks.DHD_MILKYWAY, JSGBlocks.DHD_PEGASUS
        ).forEach(regObj -> {
            var allContext = ItemDisplayContext.values();

            blockOBJModel(regObj, allContext);
        });

        //ADVANCEMENTS
        List.of(JSGItems.ICON_WORMHOLE, JSGItems.ICON_KAWOOSH_DEATH, JSGItems.ICON_RA_REALM,
                JSGItems.ICON_PRIEST_TRADE
        ).forEach(regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/advancement/" + path));
        });

        //UPGRADE CRYSTALS
        List.of(JSGItems.CRYSTAL_GLYPH_MILKYWAY, JSGItems.CRYSTAL_GLYPH_PEGASUS, JSGItems.CRYSTAL_GLYPH_UNIVERSE,
                JSGItems.CRYSTAL_ADDRESS_EXTENDED, JSGItems.CRYSTAL_ADDRESS_STATIC, JSGItems.CRYSTAL_ADDRESS_DIALING_EXTENDED,
                JSGItems.UPGRADE_IRIS, JSGItems.UPGRADE_IRIS_TRINIUM, JSGItems.UPGRADE_IRIS_CREATIVE,
                JSGItems.UPGRADE_SHIELD
        ).forEach(regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/upgrade/" + path));
        });

        //CRAFTING ELEMENTS
        List.of(JSGItems.FRAGMENT_MILKYWAY, JSGItems.FRAGMENT_PEGASUS, JSGItems.FRAGMENT_UNIVERSE,
                JSGItems.FRAGMENT_TOLLAN, JSGItems.SHIELD_EMITTER, JSGItems.IRIS_BLADE,
                JSGItems.IRIS_BLADE_TRINIUM, JSGItems.QUAD_IRIS_BLADE, JSGItems.QUAD_IRIS_BLADE_TRINIUM
        ).forEach(regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/crafting_element/" + path));
        });

        //SCHEMATICS
        List.of(JSGItems.SCHEMATIC_MILKYWAY, JSGItems.SCHEMATIC_PEGASUS, JSGItems.SCHEMATIC_UNIVERSE,
                JSGItems.SCHEMATIC_TOLLAN
        ).forEach(regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/schematic/" + path));
        });

        //DHD ELEMENTS
        List.of(JSGItems.HOLDER_CRYSTAL, JSGItems.HOLDER_CRYSTAL_PEGASUS, JSGItems.MILKYWAY_DHD_CONTROL_CRYSTALS,
                JSGItems.PEGASUS_DHD_CONTROL_CRYSTALS, JSGItems.MILKYWAY_DHD_ACTIVATION_BUTTON, JSGItems.PEGASUS_DHD_ACTIVATION_BUTTON,
                JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL, JSGItems.PEGASUS_DHD_MAIN_CRYSTAL
        ).forEach(regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/dhd_element/" + path));
        });

        //FOOD
        List.of(JSGItems.FOOD_CHOCOLATE_BAR, JSGItems.JIBBARAN_ENHANCER
        ).forEach(regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/food/" + path));
        });

        //SPAWN EGGS
        List.of(JSGItems.EGG_MASTADGE
        ).forEach(regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, JSGMapping.rl("minecraft", "item/template_spawn_egg"));
        });

        //CARTRIDGES
        List.of(
                Pair.of("yellow_cartridge", "yellow"),
                Pair.of("magenta_cartridge", "magenta"),
                Pair.of("cyan_cartridge", "cyan"),
                Pair.of("black_cartridge", "black")
        ).forEach(cartridge -> {
            String itemName = cartridge.getFirst();
            String colorName = cartridge.getSecond();

            withExistingParent(itemName, JSGMapping.rl(JSG.MOD_ID, "item/cartridge"))
                    .texture("3", JSGMapping.rl("minecraft", "block/" + colorName + "_wool"))
                    .texture("4", JSGMapping.rl("minecraft", "block/" + colorName + "_concrete"));
        });

        //MUSIC DISCS
        JSGApi.REGISTRY_HELPER.item().getEntries().stream()
                .filter(item -> item.get() instanceof JSGMusicDiscItem)
                .forEach(item -> {
                    String path = item.getId().getPath();

                    withExistingParent(path, "item/generated")
                            .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/record/" + path));
                });

        //COMPATIBILITY
        List.of("iris_blade_golden_cast", "iris_blade_sand_cast", "iris_blade_red_sand_cast",
                "stone_iris_blade", "incomplete_chocolate_bar"
        ).forEach(name -> withExistingParent(name, "item/generated")
                .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/compat/" + name)));
    }
}