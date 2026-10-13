package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsgcore.client.model.JSGOBJModelLoaderBuilder;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Objects;

public class JSGItemModelProvider extends ItemModelProvider {
    public JSGItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, JSG.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
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
