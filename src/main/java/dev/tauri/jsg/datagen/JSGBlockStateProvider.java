package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.block.stargate.IStargateBlock;
import dev.tauri.jsg.api.block.stargate.IStargateRingBlock;
import dev.tauri.jsg.common.registry.JSGBlocks;
import dev.tauri.jsgcore.JSGCore;
import dev.tauri.jsgcore.common.blockstate.JSGProperties;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class JSGBlockStateProvider extends BlockStateProvider {
    public JSGBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, JSG.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        //SIMPLE BLOCKS
        simpleBlock(JSGBlocks.IRIS_BLOCK.get(), models().getExistingFile(JSGMapping.rl("minecraft", "block/air")));
        itemModels().withExistingParent(JSGBlocks.IRIS_BLOCK.getId().getPath(), "item/generated").texture("layer0", JSGMapping.rl(JSG.MOD_ID, "block/wip"));

        createSandLikeBlocks(JSGBlocks.ABYDOS_SAND, Blocks.SAND);

        //BLOCKS WITH CUSTOM JSON MODEL
        createRotatableJsonModel(JSGBlocks.TOASTER);
        createRotatableJsonModel(JSGBlocks.PRINTER);
        createRedstoneIO(JSGBlocks.SG_REDSTONE_STATE_O_BLOCK);
        createRedstoneIO(JSGBlocks.SG_REDSTONE_DIALER_I_BLOCK);

        //DHD - Handheld models are generated in ItemsProvider because of OBJModels
        models().getBuilder("dhd_generic").texture("particle", JSGMapping.rl(JSGCore.MOD_ID, "block/materials/naquadah_alloy_block"));
        createDHDBlock(JSGBlocks.DHD_MILKYWAY);
        createDHDBlock(JSGBlocks.DHD_PEGASUS);

        //CLASSIC STARGATES - MW, PG etc.
        createClassicStargateBlocks(JSGBlocks.STARGATE_MILKYWAY_BASE_BLOCK, JSGBlocks.STARGATE_MILKYWAY_RING_BLOCK, JSGBlocks.STARGATE_MILKYWAY_CHEVRON_BLOCK);
        createClassicStargateBlocks(JSGBlocks.STARGATE_PEGASUS_BASE_BLOCK, JSGBlocks.STARGATE_PEGASUS_RING_BLOCK, JSGBlocks.STARGATE_PEGASUS_CHEVRON_BLOCK);
        createClassicStargateBlocks(JSGBlocks.STARGATE_UNIVERSE_BASE_BLOCK, JSGBlocks.STARGATE_UNIVERSE_RING_BLOCK, JSGBlocks.STARGATE_UNIVERSE_CHEVRON_BLOCK);
        createClassicStargateBlocks(JSGBlocks.STARGATE_TOLLAN_BASE_BLOCK, JSGBlocks.STARGATE_TOLLAN_RING_BLOCK, JSGBlocks.STARGATE_TOLLAN_CHEVRON_BLOCK);
        createClassicStargateBlocks(JSGBlocks.STARGATE_MOVIE_BASE_BLOCK, JSGBlocks.STARGATE_MOVIE_RING_BLOCK, JSGBlocks.STARGATE_MOVIE_CHEVRON_BLOCK);

        //ORLIN GATE
        createOrlinGateBlock(JSGBlocks.STARGATE_ORLIN_BASE_BLOCK);
        createOrlinGateBlock(JSGBlocks.STARGATE_ORLIN_MEMBER_BLOCK);
    }

    private void createRotatableJsonModel(RegistryObjectWrapper<Block> blockObj) {
        var blockName = blockObj.getId().getPath();
        var blockLoc = JSGMapping.rl(JSG.MOD_ID, "block/" + blockName);
        var blockModel = new ModelFile.UncheckedModelFile(blockLoc);

        var builder = getVariantBuilder(blockObj.get());

        builder.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.NORTH).setModels(ConfiguredModel.builder().modelFile(blockModel).rotationY(0).build());
        builder.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.EAST).setModels(ConfiguredModel.builder().modelFile(blockModel).rotationY(90).build());
        builder.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.SOUTH).setModels(ConfiguredModel.builder().modelFile(blockModel).rotationY(180).build());
        builder.partialState().with(BlockStateProperties.HORIZONTAL_FACING, Direction.WEST).setModels(ConfiguredModel.builder().modelFile(blockModel).rotationY(270).build());

        itemModels().withExistingParent(blockName, blockLoc);
    }

    private void createRedstoneIO(RegistryObjectWrapper<Block> blockObj) {
        var blockName = blockObj.getId().getPath();
        var blockLoc = JSGMapping.rl(JSG.MOD_ID, "block/" + blockName + "_off");
        var blockLitLoc = JSGMapping.rl(JSG.MOD_ID, "block/" + blockName + "_on");
        var blockModel = new ModelFile.UncheckedModelFile(blockLoc);
        var blockLitModel = new ModelFile.UncheckedModelFile(blockLitLoc);

        var builder = getVariantBuilder(blockObj.get());

        for (boolean isLit : new boolean[]{false, true}) {
            ModelFile currentModel = isLit ? blockLitModel : blockModel;

            for (Direction facing : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {

                int rotationY = switch (facing) {
                    case EAST -> 90;
                    case SOUTH -> 180;
                    case WEST -> 270;
                    default -> 0;
                };

                builder.partialState()
                        .with(BlockStateProperties.HORIZONTAL_FACING, facing)
                        .with(BlockStateProperties.LIT, isLit)
                        .setModels(ConfiguredModel.builder().modelFile(currentModel).rotationY(rotationY).build());
            }
        }
        itemModels().withExistingParent(blockName, blockLoc);
    }

    private void createDHDBlock(RegistryObjectWrapper<Block> block) {
        models().withExistingParent(block.getId().getPath(), JSGMapping.rl(JSG.MOD_ID, "block/dhd_generic"));
        simpleBlock(block.get(), models().getExistingFile(JSGMapping.rl(JSG.MOD_ID, "block/dhd_generic")));
    }

    private void createSandLikeBlocks(RegistryObjectWrapper<Block> blockObj, Block mcBlock) {
        var blockName = blockObj.getId().getPath();
        var mcBlockPath = requireNonNull(BuiltInRegistries.BLOCK.getKey(mcBlock)).getPath();
        var blockModel = models().withExistingParent(blockName, "minecraft:block/" + mcBlockPath);

        getVariantBuilder(blockObj.get())
                .partialState()
                .setModels(ConfiguredModel.allYRotations(blockModel, 0, false));
        itemModels().withExistingParent(blockName, JSGMapping.rl(JSG.MOD_ID, "block/" + blockName));
    }

    private void createClassicStargateBlocks(RegistryObjectWrapper<Block> baseBlock, RegistryObjectWrapper<Block> ringBlock, RegistryObjectWrapper<Block> chevronBlock) {
        var type = ((IStargateBlock) baseBlock.get()).getStargateType().getId().getPath();
        var sides = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + type + "_stargate_sides");
        var baseFront = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + type + "_stargate_base_front");
        var ringFront = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + type + "_stargate_ring_front");
        var chevronFront = JSGMapping.rl(JSG.MOD_ID, "block/stargate/" + type + "_stargate_chevron_front");

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
                xRotations.forEach((vertical, xRot) ->
                        List.of(false, true).forEach((renderState) -> builder.partialState()
                                .with(JSGProperties.FACING_HORIZONTAL_PROPERTY, direction)
                                .with(JSGProperties.FACING_VERTICAL_PROPERTY, vertical)
                                .with(JSGProperties.RENDER_BLOCK_PROPERTY, renderState)
                                .setModels(new ConfiguredModel(model, xRot, yRot, false)))));
        itemModels().withExistingParent(blockName, JSGMapping.rl(JSG.MOD_ID, "block/" + blockName));
    }

    private void createOrlinGateBlock(RegistryObjectWrapper<Block> blockObj) {
        var blockName = blockObj.getId().getPath();


        var frontTexture = JSGMapping.rl(JSG.MOD_ID, "block/stargate/orlin_stargate_" + (blockObj.get() instanceof IStargateRingBlock ? "ring" : "base") + "_front");
        var brokenFrontTexture = JSGMapping.rl(JSG.MOD_ID, "block/stargate/orlin_stargate_" + (blockObj.get() instanceof IStargateRingBlock ? "ring" : "base") + "_front_broken");

        var southTexture = JSGMapping.rl(JSG.MOD_ID, "block/stargate/orlin_stargate_south");
        var westTexture = JSGMapping.rl(JSG.MOD_ID, "block/stargate/orlin_stargate_west");
        var eastTexture = JSGMapping.rl(JSG.MOD_ID, "block/stargate/orlin_stargate_east");
        var upTexture = JSGMapping.rl(JSG.MOD_ID, "block/stargate/orlin_stargate_top_bottom");
        var downTexture = JSGMapping.rl(JSG.MOD_ID, "block/stargate/orlin_stargate_top_bottom");

        var normalModel = models().cube(blockName, downTexture, upTexture, frontTexture, southTexture, westTexture, eastTexture)
                .texture("particle", frontTexture);

        var brokenModel = models().cube(blockName + "_broken", downTexture, upTexture, brokenFrontTexture, southTexture, westTexture, eastTexture)
                .texture("particle", brokenFrontTexture);

        itemModels().withExistingParent(blockName, JSGMapping.rl(JSG.MOD_ID, "block/" + blockName));

        var builder = getVariantBuilder(blockObj.get());

        for (boolean isBroken : new boolean[]{false, true}) {
            ModelFile currentModel = isBroken ? brokenModel : normalModel;

            for (boolean renderBlock : new boolean[]{false, true}) {
                for (int vertical : new int[]{0, 1, 2}) {

                    int rotationX = switch (vertical) {
                        case 1 -> 270;
                        case 2 -> 90;
                        default -> 0;
                    };

                    for (Direction facing : BlockStateProperties.HORIZONTAL_FACING.getPossibleValues()) {

                        int rotationY = switch (facing) {
                            case EAST -> 90;
                            case SOUTH -> 180;
                            case WEST -> 270;
                            default -> 0;
                        };

                        builder.partialState()
                                .with(BlockStateProperties.HORIZONTAL_FACING, facing)
                                .with(JSGProperties.FACING_VERTICAL_PROPERTY, vertical)
                                .with(JSGProperties.RENDER_BLOCK_PROPERTY, renderBlock)
                                .with(JSGProperties.ORLIN_BROKEN, isBroken)
                                .setModels(ConfiguredModel.builder()
                                        .modelFile(currentModel)
                                        .rotationX(rotationX)
                                        .rotationY(rotationY)
                                        .build());
                    }
                }
            }
        }
    }
}
