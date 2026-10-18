package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsgcore.client.model.JSGOBJModelLoaderBuilder;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class JSGItemModelProvider extends ItemModelProvider {
    public JSGItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, JSG.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        itemOBJModel(JSGItems.DHD_NAQUADAH_TANK,
                ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                ItemDisplayContext.FIRST_PERSON_RIGHT_HAND,
                ItemDisplayContext.THIRD_PERSON_LEFT_HAND,
                ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,
                ItemDisplayContext.GROUND,
                ItemDisplayContext.GUI,
                ItemDisplayContext.FIXED,
                ItemDisplayContext.HEAD
        );
    }

    private void blockOBJModel(RegistryObjectWrapper<Block> block, ItemDisplayContext... renderTypes) {
        itemOBJModel(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block.get())), renderTypes);
    }

    private void itemOBJModel(RegistryObjectWrapper<Item> item, ItemDisplayContext... renderTypes) {
        itemOBJModel(Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item.get())), renderTypes);
    }

    private void itemOBJModel(ResourceLocation item, ItemDisplayContext... renderTypes) {
        getBuilder(item.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", JSGMapping.rl(JSGApi.MOD_ID, "block/wip"))
                .customLoader((parent, existingFileHelper) -> new JSGOBJModelLoaderBuilder<>(parent, existingFileHelper).renderTypes(renderTypes));
    }
}
