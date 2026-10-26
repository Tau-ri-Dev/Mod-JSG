package dev.tauri.jsg.datagen;

import com.mojang.datafixers.util.Pair;
import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.registry.JSGBlocks;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsgcore.client.model.JSGOBJModelLoaderBuilder;
import dev.tauri.jsgcore.common.item.JSGMusicDiscItem;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import static java.util.Objects.requireNonNull;

public class JSGItemModelProvider extends ItemModelProvider {
    public JSGItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, JSG.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        //All INVENTORY OBJ MODELS
        List.of(JSGItems.DHD_NAQUADAH_TANK, JSGItems.MILKYWAY_DHD_BUTTONS_CONSOLE, JSGItems.PEGASUS_DHD_BUTTONS_CONSOLE,
                JSGItems.MILKYWAY_DHD_UPGRADES_COVER, JSGItems.PEGASUS_DHD_UPGRADES_COVER, JSGItems.GDO,
                JSGItems.ADMIN_CONTROLLER, JSGItems.UNIVERSE_DIALER
        ).forEach(regObj -> {
            ItemDisplayContext[] allContext = ItemDisplayContext.values();

            itemOBJModel(regObj, allContext);
        });

        List.of(JSGBlocks.DHD_MILKYWAY, JSGBlocks.DHD_PEGASUS
        ).forEach(regObj -> {
            ItemDisplayContext[] allContext = ItemDisplayContext.values();

            blockOBJModel(regObj, allContext);
        });

        //ADVANCEMENTS
        List.of(JSGItems.ICON_WORMHOLE, JSGItems.ICON_KAWOOSH_DEATH
        ).forEach( regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/advancement/" + path));
        });

        //UPGRADE CRYSTALS
        List.of(JSGItems.CRYSTAL_GLYPH_MILKYWAY, JSGItems.CRYSTAL_GLYPH_PEGASUS, JSGItems.CRYSTAL_GLYPH_UNIVERSE,
                JSGItems.CRYSTAL_ADDRESS_EXTENDED, JSGItems.CRYSTAL_ADDRESS_STATIC, JSGItems.CRYSTAL_ADDRESS_DIALING_EXTENDED,
                JSGItems.UPGRADE_IRIS, JSGItems.UPGRADE_IRIS_TRINIUM, JSGItems.UPGRADE_IRIS_CREATIVE,
                JSGItems.UPGRADE_SHIELD
                        ).forEach( regObj -> {
                            var path = regObj.getId().getPath();
                            withExistingParent(path, "item/generated")
                                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/upgrade/" + path));
        });

        //CRAFTING ELEMENTS
        List.of(JSGItems.FRAGMENT_MILKYWAY, JSGItems.FRAGMENT_PEGASUS, JSGItems.FRAGMENT_UNIVERSE,
                JSGItems.FRAGMENT_TOLLAN, JSGItems.SHIELD_EMITTER, JSGItems.IRIS_BLADE,
                JSGItems.IRIS_BLADE_TRINIUM, JSGItems.QUAD_IRIS_BLADE, JSGItems.QUAD_IRIS_BLADE_TRINIUM
        ).forEach( regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/crafting_element/" + path));
        });

        //SCHEMATICS
        List.of(JSGItems.SCHEMATIC_MILKYWAY, JSGItems.SCHEMATIC_PEGASUS, JSGItems.SCHEMATIC_UNIVERSE,
                JSGItems.SCHEMATIC_TOLLAN
        ).forEach( regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/schematic/" + path));
        });

        //DHD ELEMENTS
        List.of(JSGItems.HOLDER_CRYSTAL, JSGItems.HOLDER_CRYSTAL_PEGASUS, JSGItems.MILKYWAY_DHD_CONTROL_CRYSTALS,
                JSGItems.PEGASUS_DHD_CONTROL_CRYSTALS, JSGItems.MILKYWAY_DHD_ACTIVATION_BUTTON, JSGItems.PEGASUS_DHD_ACTIVATION_BUTTON,
                JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL, JSGItems.PEGASUS_DHD_MAIN_CRYSTAL
        ).forEach( regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/dhd_element/" + path));
        });

        //FOOD
        List.of( JSGItems.FOOD_CHOCOLATE_BAR, JSGItems.JIBBARAN_ENHANCER
        ).forEach( regObj -> {
            var path = regObj.getId().getPath();
            withExistingParent(path, "item/generated")
                    .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/food/" + path));
        });

        //SPAWN EGGS
        List.of( JSGItems.EGG_MASTADGE
        ).forEach( regObj -> {
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
        ForgeRegistries.ITEMS.getValues().stream()
                .filter(item -> item instanceof JSGMusicDiscItem)
                .forEach(item -> {
                    String path = requireNonNull(ForgeRegistries.ITEMS.getKey(item)).getPath();

                    withExistingParent(path, "item/generated")
                            .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/record/" + path));
                });

        //COMPATIBILITY
        List.of("iris_blade_golden_cast", "iris_blade_sand_cast",  "iris_blade_red_sand_cast",
                "stone_iris_blade", "incomplete_chocolate_bar"
        ).forEach( name -> withExistingParent(name, "item/generated")
                .texture("layer0", JSGMapping.rl(JSG.MOD_ID, "item/compat/" + name)));



    }

    private void blockOBJModel(RegistryObjectWrapper<? extends Block> block, ItemDisplayContext... renderTypes) {
        itemOBJModel(requireNonNull(ForgeRegistries.BLOCKS.getKey(block.get())), renderTypes);
    }

    private void itemOBJModel(RegistryObjectWrapper<? extends Item> item, ItemDisplayContext... renderTypes) {
        itemOBJModel(requireNonNull(ForgeRegistries.ITEMS.getKey(item.get())), renderTypes);
    }

    private void itemOBJModel(ResourceLocation item, ItemDisplayContext... renderTypes) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", JSGMapping.rl(JSGApi.MOD_ID, "block/wip"))
                .customLoader((parent, existingFileHelper) -> new JSGOBJModelLoaderBuilder<>(parent, existingFileHelper).renderTypes(renderTypes))
                .visibility("override_transformations", true);
    }
}