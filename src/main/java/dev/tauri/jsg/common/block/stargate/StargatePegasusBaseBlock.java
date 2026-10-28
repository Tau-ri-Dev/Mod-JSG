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

public class StargatePegasusBaseBlock extends StargateClassicBaseBlock {
    @Override
    public @NonNull StargateType<?> getStargateType() {
        return StargateTypes.PEGASUS.get();
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JSGBlockEntities.STARGATE_PEGASUS_BASE_BE.get().create(pos, state);
    }
}
