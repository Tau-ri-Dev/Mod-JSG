package dev.tauri.jsg.common.blockentity.stargate;

import dev.tauri.jsg.api.config.JSGConfig;
import dev.tauri.jsg.common.registry.JSGBlockEntities;
import dev.tauri.jsg.common.registry.JSGBlocks;
import dev.tauri.jsgcore.common.util.ItemNBT;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;


public class StargateOrlinMemberBE extends StargateAbstractMemberBE {
    public StargateOrlinMemberBE(BlockPos pos, BlockState state) {
        super(JSGBlockEntities.STARGATE_ORLIN_MEMBER_BE.get(), pos, state);
    }

    @Override
    public Block getBaseBlock() {
        return JSGBlocks.STARGATE_ORLIN_BASE_BLOCK.get();
    }

    private int openCount = 0;

    public boolean isBroken() {
        return openCount >= JSGConfig.Stargate.stargateOrlinMaxOpenCount.get();
    }

    public void incrementOpenCount() {
        if (level == null || level.isClientSide()) return;
        openCount++;
        if (isBroken()) {
            level.setBlock(getBlockPos(), level.getBlockState(getBlockPos()).setValue(dev.tauri.jsgcore.common.blockstate.JSGProperties.ORLIN_BROKEN, true), 3);
        }
        setChanged();
    }

    public int getOpenCount() {
        return openCount;
    }

    public void initializeFromItemStack(ItemStack stack) {
        if (ItemNBT.hasTag(stack)) {
            CompoundTag compound = ItemNBT.getTag(stack);
            if (compound != null && compound.contains("openCount")) {
                openCount = compound.getInt("openCount");
            }
        }
    }

    // ---------------------------------------------------------------------------------
    // NBT

    @Override
    public void saveAdditional(CompoundTag compound, net.minecraft.core.HolderLookup.Provider registries) {
        compound.putInt("openCount", openCount);

        super.saveAdditional(compound, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag compound, net.minecraft.core.HolderLookup.Provider registries) {
        openCount = compound.getInt("openCount");

        super.loadAdditional(compound, registries);
    }
}
