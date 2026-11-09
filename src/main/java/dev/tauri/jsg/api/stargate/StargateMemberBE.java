package dev.tauri.jsg.api.stargate;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public interface StargateMemberBE {
    default BlockEntity self() {
        return (BlockEntity) this;
    }

    @Nullable
    Stargate<?> getBaseTile(Level world);
}
