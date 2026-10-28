package dev.tauri.jsg.common.block.stargate;

import dev.tauri.jsg.api.stargate.type.StargateType;
import dev.tauri.jsg.api.stargate.type.StargateTypes;
import dev.tauri.jsg.common.registry.JSGBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class StargateUniverseBaseBlock extends StargateClassicBaseBlock {
    @Override
    public @NonNull StargateType<?> getStargateType() {
        return StargateTypes.UNIVERSE.get();
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JSGBlockEntities.STARGATE_UNIVERSE_BASE_BE.get().create(pos, state);
    }
}
