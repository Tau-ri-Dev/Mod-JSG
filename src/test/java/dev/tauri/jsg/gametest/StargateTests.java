package dev.tauri.jsg.gametest;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.registry.JSGRegistries;
import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.api.stargate.type.StargateTypes;
import dev.tauri.jsg.common.registry.JSGBlocks;
import dev.tauri.jsgcore.common.multistructure.merging.IMergeHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@GameTestHolder(JSG.MOD_ID)
@PrefixGameTestTemplate(false)
public class StargateTests {
    @GameTest(template = JSGGameTests.EMPTY_TEMPLATE, timeoutTicks = 1000)
    public static void testMergeHelper(GameTestHelper helper) {
        final var basePos = new BlockPos(8, 2, 8);
        final var player = helper.makeMockSurvivalPlayer();
        player.setYBodyRot(0);
        player.setXRot(0);
        final var pInv = player.getInventory();

        final var sequence = helper.startSequence();

        final var level = helper.getLevel();

        sequence.thenExecute(() -> {
            helper.setBlock(basePos.below(), Blocks.STONE.defaultBlockState());
            helper.assertBlock(basePos, (block) -> {
                JSG.logger.info(block.getDescriptionId());
                return block == Blocks.AIR;
            }, "Block at future gate base is not air");
        });

        // check each sg type
        for (var sgType : JSGRegistries.R_STARGATE_TYPE.get().getValues()) {
            sequence.thenExecute(() -> JSG.logger.info("Testing {} stargate...", sgType.name));

            final AtomicReference<Block> ring = new AtomicReference<>();
            final AtomicReference<Block> chevron = new AtomicReference<>();
            final AtomicReference<List<IMergeHelper.MemberAutoBuildBlock>> missingRings = new AtomicReference<>();
            final AtomicReference<List<IMergeHelper.MemberAutoBuildBlock>> missingChevrons = new AtomicReference<>();

            final AtomicReference<dev.tauri.jsg.common.multistructure.mergehelper.StargateAbstractMergeHelper> mergeHelperReference = new AtomicReference<>();

            sequence.thenExecuteAfter(5, () -> {
                // place base
                JSG.logger.info(" > Placing base block...");
                var bases = new ItemStack(sgType.getBaseBlock(), 32);
                pInv.setItem(pInv.selected, bases);
                helper.placeAt(player, bases, basePos.below(), Direction.UP);
                helper.assertBlock(basePos, block -> {
                    var result = block == sgType.getBaseBlock();
                    if (!result)
                        JSG.logger.info(" > Found: {}", block.getDescriptionId());
                    return result;
                }, "Stargate base block not placed");
                if (bases.getCount() != 31)
                    throw new GameTestAssertException("Bases stack doesn't have count = 31. Found " + bases.getCount());
                JSG.logger.info(" > Base block OK");
            }).thenExecuteAfter(5, () -> {
                JSG.logger.info(" > Testing BE presence...");
                // check BE and mergeHelper
                var be = helper.getBlockEntity(basePos);
                if (!(be instanceof Stargate<?> sg))
                    throw new GameTestAssertException("BlockEntity of stargate base block is not instance of Stargate<?>");
                JSG.logger.info(" > BE OK");
                JSG.logger.info(" > Testing Merge Helper status...");
                var mergeHelper = sg.getMergeHelper();
                if (mergeHelper == null)
                    throw new GameTestAssertException("mergeHelper is null");
                if (!(mergeHelper instanceof dev.tauri.jsg.common.multistructure.mergehelper.StargateAbstractMergeHelper abstractMergeHelper))
                    throw new GameTestAssertException("mergeHelper is not instance of abstractMergeHelper");
                JSG.logger.info(" - Merge Helper present");

                if (sg.isMerged())
                    throw new GameTestAssertException("Stargate is merged without any member block");
                JSG.logger.info(" - Merge status OK");

                mergeHelperReference.set(abstractMergeHelper);

                // get all missing blocks from the gate
                missingRings.set(mergeHelper.getAbsentBlockPositions(level, false));
                missingChevrons.set(mergeHelper.getAbsentBlockPositions(level, true));

                // get ring block
                ring.set(abstractMergeHelper.getRingBlock());
                if (ring.get() == null)
                    throw new GameTestAssertException("Ring block from merge helper is null");
                JSG.logger.info(" - Ring block OK");

                // classic gates
                if (mergeHelper instanceof dev.tauri.jsg.common.multistructure.mergehelper.StargateClassicMergeHelper classicMergeHelper) {
                    JSG.logger.info(" > Merge helper is classic one - getting chevron blocks...");
                    // get chevron block
                    chevron.set(classicMergeHelper.getChevronBlock());
                    if (chevron.get() == null)
                        throw new GameTestAssertException("Chevron block from classic merge helper is null");
                    JSG.logger.info(" - Chevron block OK");
                }
                // -----------

                if (missingRings.get().isEmpty())
                    throw new GameTestAssertException("Stargate doesn't have ring blocks");
                if (chevron.get() != null) {
                    if (missingChevrons.get().isEmpty())
                        throw new GameTestAssertException("Stargate doesn't have chevron blocks when it should have");
                    if (missingChevrons.get().size() != 9)
                        throw new GameTestAssertException("Stargate with chevrons should have exactly 9 of them. Found " + missingChevrons.get().size());
                }
                JSG.logger.info(" > Merge Helper status OK");
            });

            // testing wrong ring/chevron type
            sequence.thenExecuteAfter(2, () -> {
                JSG.logger.info(" > Attempt to use wrong ring block...");
                final var wrongRingBlock = (sgType == StargateTypes.MILKYWAY.get() ? JSGBlocks.STARGATE_PEGASUS_RING_BLOCK : JSGBlocks.STARGATE_MILKYWAY_RING_BLOCK).get();
                final var wrongRingStack = new ItemStack(wrongRingBlock, 5);
                // set the rings stack to player's main hand
                pInv.setItem(pInv.selected, wrongRingStack);
                helper.useBlock(basePos, player);

                if (mergeHelperReference.get().getAbsentBlockPositions(level, false).size() < missingRings.get().size())
                    throw new GameTestAssertException("Wrong ring placed");

                if (wrongRingStack.getCount() < 5) {
                    if (helper.getBlockState(basePos.relative(Direction.NORTH)).is(wrongRingBlock))
                        helper.setBlock(basePos.relative(Direction.NORTH), Blocks.AIR);
                    else
                        throw new GameTestAssertException("Wrong rings stack decremented and used to build stargate");
                }
                JSG.logger.info(" > Attempt failed - OK");
            });
            sequence.thenExecuteAfter(2, () -> {
                JSG.logger.info(" > Attempt to use wrong chevron block...");
                final var wrongChevronBlock = (sgType == StargateTypes.MILKYWAY.get() ? JSGBlocks.STARGATE_PEGASUS_CHEVRON_BLOCK : JSGBlocks.STARGATE_MILKYWAY_CHEVRON_BLOCK).get();
                final var wrongChevronStack = new ItemStack(wrongChevronBlock, 5);
                // set the chevrons stack to player's main hand
                pInv.setItem(pInv.selected, wrongChevronStack);
                helper.useBlock(basePos, player);

                if (mergeHelperReference.get().getAbsentBlockPositions(level, true).size() < missingChevrons.get().size())
                    throw new GameTestAssertException("Wrong chevron placed");

                if (wrongChevronStack.getCount() < 5) {
                    if (helper.getBlockState(basePos.relative(Direction.NORTH)).is(wrongChevronBlock))
                        helper.setBlock(basePos.relative(Direction.NORTH), Blocks.AIR);
                    else
                        throw new GameTestAssertException("Wrong chevrons stack decremented and used to build stargate");
                }
                JSG.logger.info(" > Attempt failed - OK");
            });

            // testing placing rings via autobuilder
            final var ringsTotal = new AtomicInteger();
            final var ringsStack = new AtomicReference<>(ItemStack.EMPTY);
            final var lastRingsStackCount = new AtomicInteger();
            sequence.thenExecute(() -> {
                JSG.logger.info(" > Testing Auto Builder - ring blocks...");
                ringsTotal.set(missingRings.get().size());
                ringsStack.set(new ItemStack(ring.get(), ringsTotal.get()));
                lastRingsStackCount.set(ringsTotal.get());
            });
            for (int i = 0; i < (sgType == StargateTypes.ORLIN.get() ? 7 : 8); i++) {
                sequence.thenExecuteAfter(5, () -> {
                    // set the target stack to player's main hand
                    pInv.setItem(pInv.selected, ringsStack.get());
                    helper.useBlock(basePos, player);

                    if (ringsStack.get().getCount() != (lastRingsStackCount.get() - 1))
                        throw new GameTestAssertException("Rings stack not decremented after ring is placed");

                    lastRingsStackCount.set(ringsStack.get().getCount());

                    if (mergeHelperReference.get().getAbsentBlockPositions(level, false).size() != ringsStack.get().getCount())
                        throw new GameTestAssertException("Rings stack decremented but ring block not placed");
                    JSG.logger.info(" - Placed ring block");
                });
            }
            sequence.thenExecute(() -> {
                if (!mergeHelperReference.get().getAbsentBlockPositions(level, false).isEmpty())
                    throw new GameTestAssertException("After all ring blocks have been placed, there is still some missing!");
                JSG.logger.info(" > Testing Auto Builder - ring blocks OK");
            });

            // testing placing chevrons via autobuilder
            final var chevronsTotal = new AtomicInteger();
            final var chevronsStack = new AtomicReference<>(ItemStack.EMPTY);
            final var lastChevronsStackCount = new AtomicInteger();
            sequence.thenExecute(() -> {
                JSG.logger.info(" > Testing Auto Builder - chevron blocks...");
                chevronsTotal.set(missingChevrons.get().size());
                if (chevron.get() != null)
                    chevronsStack.set(new ItemStack(chevron.get(), chevronsTotal.get()));
                lastChevronsStackCount.set(chevronsStack.get().getCount());
            });
            for (int i = 0; i < (sgType == StargateTypes.ORLIN.get() ? 0 : 9); i++) {
                sequence.thenExecuteAfter(5, () -> {
                    // set the target stack to player's main hand
                    pInv.setItem(pInv.selected, chevronsStack.get());
                    helper.useBlock(basePos, player);

                    if (chevronsStack.get().getCount() != (lastChevronsStackCount.get() - 1))
                        throw new GameTestAssertException("Chevrons stack not decremented after chevron is placed");

                    lastChevronsStackCount.set(chevronsStack.get().getCount());

                    if (mergeHelperReference.get().getAbsentBlockPositions(level, true).size() != chevronsStack.get().getCount())
                        throw new GameTestAssertException("Chevrons stack decremented but chevron block not placed");
                    JSG.logger.info(" - Placed chevron block");
                });
            }
            sequence.thenExecute(() -> {
                if (!mergeHelperReference.get().getAbsentBlockPositions(level, true).isEmpty())
                    throw new GameTestAssertException("After all chevron blocks have been placed, there is still some missing!");
                JSG.logger.info(" > Testing Auto Builder - chevron blocks OK");
            });

            // check if merged
            sequence.thenExecuteAfter(5, () -> {
                JSG.logger.info(" > Checking merge status after building...");
                // check BE and mergeHelper
                var be = helper.getBlockEntity(basePos);
                if (!(be instanceof Stargate<?> sg))
                    throw new GameTestAssertException("BlockEntity of stargate base block is not instance of Stargate<?> - after placing members");

                if (!sg.isMerged())
                    throw new GameTestAssertException("Stargate is NOT merged after placing member blocks");
                JSG.logger.info(" > Stargate is merged! OK");
            });

            // --- CLEANUP ---

            sequence.thenExecuteAfter(2, () -> {
                JSG.logger.info(" > Cleanup base block...");
                var memberBlocks = mergeHelperReference.get().getBlocks(true);

                var absoluteBasePos = helper.absolutePos(basePos);
                helper.getBlockState(basePos).onDestroyedByPlayer(level, absoluteBasePos, player, false, level.getFluidState(absoluteBasePos));

                helper.assertBlock(basePos, block -> block == Blocks.AIR, "Stargate base is still there");
                JSG.logger.info(" > Cleanup base block OK");

                JSG.logger.info(" > Cleanup member blocks...");
                for (var absoluteMemberPos : memberBlocks.keySet()) {
                    var currentMemberState = level.getBlockState(absoluteMemberPos);
                    var result = currentMemberState.getBlock() instanceof dev.tauri.jsg.common.block.stargate.StargateAbstractMemberBlock;
                    if (!result) {
                        JSG.logger.info(" - Found {}", currentMemberState.getBlock().getDescriptionId());
                        throw new GameTestAssertException("Not e member block");
                    }
                    level.setBlock(absoluteMemberPos, Blocks.AIR.defaultBlockState(), 3);
                }
                JSG.logger.info(" > Cleanup member blocks OK");
            });
        }

        sequence.thenExecute(() -> JSG.logger.info("Testing done!")).thenSucceed();
    }
}
