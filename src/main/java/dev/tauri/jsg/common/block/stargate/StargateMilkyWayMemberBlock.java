package dev.tauri.jsg.common.block.stargate;

import dev.tauri.jsg.api.block.stargate.IStargateChevronBlock;
import dev.tauri.jsg.api.block.stargate.IStargateRingBlock;
import dev.tauri.jsg.api.stargate.StargateMemberBE;
import dev.tauri.jsg.api.stargate.type.StargateType;
import dev.tauri.jsg.api.stargate.type.StargateTypes;
import dev.tauri.jsg.common.registry.JSGBlockEntities;
import dev.tauri.jsgcore.common.registry.CoreTabs;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public abstract class StargateMilkyWayMemberBlock extends StargateClassicMemberBlock {

    @Override
    public @NonNull StargateType<?> getStargateType() {
        return StargateTypes.MILKYWAY.get();
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        var be = level.getBlockEntity(pos);
        if (be instanceof StargateMemberBE memberBe) {
            var sg = memberBe.getBaseTile(level);
            if (sg != null) {
                var dm = sg.getDialingManager();
                if (dm.canDialManuallyByHand(player, player.getItemInHand(hand), hand)) {
                    if (!level.isClientSide()) {
                        var result = dm.updateManualDialingByHand(player, player.getItemInHand(hand), hand);
                        //JSG.logger.info("Manual dialing update result: " + result.name());
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.use(state, level, pos, player, hand, hitResult);
    }

    public static class StargateMilkyWayChevronBlock extends StargateMilkyWayMemberBlock implements IStargateChevronBlock {
        @Nullable
        @Override
        @ParametersAreNonnullByDefault
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return JSGBlockEntities.STARGATE_MILKYWAY_CHEVRON_BE.get().create(pos, state);
        }

        @Nullable
        @Override
        public Supplier<RegistryObjectWrapper<CreativeModeTab>> getTab() {
            return CoreTabs.TAB_TRANSPORTATION;
        }
    }

    public static class StargateMilkyWayRingBlock extends StargateMilkyWayMemberBlock implements IStargateRingBlock {
        @Nullable
        @Override
        @ParametersAreNonnullByDefault
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return JSGBlockEntities.STARGATE_MILKYWAY_RING_BE.get().create(pos, state);
        }

        @Nullable
        @Override
        public Supplier<RegistryObjectWrapper<CreativeModeTab>> getTab() {
            return CoreTabs.TAB_TRANSPORTATION;
        }
    }
}
