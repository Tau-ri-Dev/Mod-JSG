package dev.tauri.jsg.common.block.stargate;

import dev.tauri.jsg.api.stargate.type.StargateType;
import dev.tauri.jsg.api.stargate.type.StargateTypes;
import dev.tauri.jsg.common.registry.JSGBlockEntities;
import dev.tauri.jsgcore.JSGCore;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

public class StargateMovieBaseBlock extends StargateClassicBaseBlock {
    @Override
    public @NonNull StargateType<?> getStargateType() {
        return StargateTypes.MOVIE.get();
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return JSGBlockEntities.STARGATE_MOVIE_BASE_BE.get().create(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack itemStack, Item.TooltipContext context, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, context, components, tooltipFlag);
        components.add(JSGCore.getInProgress());
    }
}
