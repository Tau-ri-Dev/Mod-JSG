package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.common.blockstate.JSGProperties;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockModelBuilder;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Map;

public class JSGBlockStateProvider extends BlockStateProvider {
    public JSGBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, JSG.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
    }


    private void createClassicStargateBlocks(RegistryObjectWrapper<Block> baseBlock, RegistryObjectWrapper<Block> ringBlock, RegistryObjectWrapper<Block> chevronBlock, String gateType) {
        var sides = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + gateType + "_sides");
        var baseFront = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + gateType + "_base_front");
        var ringFront = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + gateType + "_ring_front");
        var chevronFront = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + gateType + "_chevron_front");

        buildClassicStargatePart(baseBlock, baseFront, sides);
        buildClassicStargatePart(ringBlock, ringFront, sides);
        buildClassicStargatePart(chevronBlock, chevronFront, sides);

    }

    private void buildClassicStargatePart(RegistryObjectWrapper<Block> blockObj, ResourceLocation frontTexture, ResourceLocation sidesTexture) {
        var block = blockObj.get();
        var blockName = blockObj.getId().getPath();

        BlockModelBuilder model = models().withExistingParent(blockName, "block/cube")
                .texture("particle", sidesTexture)
                .texture("north", frontTexture)
                .texture("south", sidesTexture)
                .texture("west", sidesTexture)
                .texture("east", sidesTexture)
                .texture("up", sidesTexture)
                .texture("down", sidesTexture);

        VariantBlockStateBuilder builder = getVariantBuilder(block);

        Map<Direction, Integer> yRotations = Map.of(
                Direction.NORTH, 0,
                Direction.EAST, 90,
                Direction.SOUTH, 180,
                Direction.WEST, 270
        );

        Map<Integer, Integer> xRotations = Map.of(
                0, 0,
                2, 90,
                1, 270
        );

        yRotations.forEach((direction, yRot) ->
                xRotations.forEach((vertical, xRot) -> {
                    List.of(false, true).forEach((renderState) -> {
                        builder.partialState()
                                .with(JSGProperties.FACING_HORIZONTAL_PROPERTY, direction)
                                .with(JSGProperties.FACING_VERTICAL_PROPERTY, vertical)
                                .with(JSGProperties.RENDER_BLOCK_PROPERTY, renderState)
                                .setModels(new ConfiguredModel(model, xRot, yRot, false));
                    });
                }));
        itemModels().withExistingParent(blockName, JSGMapping.rl(JSG.MOD_ID, "block/" + blockName));
    }

    public static ResourceLocation getRL(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
