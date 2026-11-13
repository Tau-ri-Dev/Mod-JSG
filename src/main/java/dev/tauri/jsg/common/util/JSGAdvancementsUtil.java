package dev.tauri.jsg.common.util;

import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.common.advancements.JSGCriterions;
import dev.tauri.jsgcore.common.util.JSGAxisAlignedBB;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

import java.util.List;

public class JSGAdvancementsUtil {
    public enum EnumAdvancementType {
        GATE_FLICKER,
        IRIS_IMPACT
    }

    public static void tryTriggerRangedAdvancement(Stargate<?> tile, EnumAdvancementType advancementType) {
        Level world = tile.getStargateLevel();
        if (world == null) return;
        BlockPos pos = tile.blockPosition();
        tryTriggerRangedAdvancement(world, pos, advancementType);
    }

    public static void tryTriggerRangedAdvancement(Level level, BlockPos pos, EnumAdvancementType advancementType) {

        int radius = 25;

        List<ServerPlayer> players = level.getNearbyEntities(ServerPlayer.class, TargetingConditions.forNonCombat(), null, new JSGAxisAlignedBB(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius)));
        for (ServerPlayer player : players) {
            if (advancementType == EnumAdvancementType.IRIS_IMPACT) {
                JSGCriterions.IRIS_IMPACT.trigger(player);
            }
        }
    }
}
