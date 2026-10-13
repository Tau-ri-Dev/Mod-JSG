package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.minecraft.core.registries.BuiltInRegistries;

public class JSGBlockStateProvider extends BlockStateProvider {
    public JSGBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, JSG.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }

    public static ResourceLocation getRL(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
