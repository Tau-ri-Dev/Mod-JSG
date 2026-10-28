package dev.tauri.jsg.common.block.stargate;

import dev.tauri.jsg.api.block.stargate.IStargateChevronBlock;
import dev.tauri.jsg.api.block.stargate.IStargateRingBlock;
import dev.tauri.jsg.api.stargate.type.StargateType;
import dev.tauri.jsg.api.stargate.type.StargateTypes;
import dev.tauri.jsg.common.registry.JSGBlockEntities;
import dev.tauri.jsgcore.common.registry.CoreTabs;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

public abstract class StargatePegasusMemberBlock extends StargateClassicMemberBlock {

    @Override
    public @NonNull StargateType<?> getStargateType() {
        return StargateTypes.PEGASUS.get();
    }

    public static class StargatePegasusChevronBlock extends StargatePegasusMemberBlock implements IStargateChevronBlock {
        @Nullable
        @Override
        @ParametersAreNonnullByDefault
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return JSGBlockEntities.STARGATE_PEGASUS_CHEVRON_BE.get().create(pos, state);
        }

        @Nullable
        @Override
        public Supplier<RegistryObjectWrapper<CreativeModeTab>> getTab() {
            return CoreTabs.TAB_TRANSPORTATION;
        }
    }

    public static class StargatePegasusRingBlock extends StargatePegasusMemberBlock implements IStargateRingBlock {
        @Nullable
        @Override
        @ParametersAreNonnullByDefault
        public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
            return JSGBlockEntities.STARGATE_PEGASUS_RING_BE.get().create(pos, state);
        }

        @Nullable
        @Override
        public Supplier<RegistryObjectWrapper<CreativeModeTab>> getTab() {
            return CoreTabs.TAB_TRANSPORTATION;
        }
    }
}
