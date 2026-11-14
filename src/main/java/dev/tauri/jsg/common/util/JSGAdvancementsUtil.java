package dev.tauri.jsg.common.util;

import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.common.advancements.JSGCriterions;
import dev.tauri.jsgcore.common.advancement.JSGCriterion;
import dev.tauri.jsgcore.common.util.JSGAxisAlignedBB;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
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
        if (advancementType == EnumAdvancementType.IRIS_IMPACT)
            tryTriggerRangedAdvancement(level, pos, JSGCriterions.IRIS_IMPACT);
    }

    public static void tryTriggerRangedAdvancement(Stargate<?> sg, JSGCriterion advancement) {
        Level world = sg.getStargateLevel();
        if (world == null) return;
        BlockPos pos = sg.blockPosition();
        tryTriggerRangedAdvancement(world, pos, advancement);
    }

    public static void tryTriggerRangedAdvancement(Level level, BlockPos pos, JSGCriterion advancement) {
        int radius = 25;
        List<ServerPlayer> players = level.getNearbyEntities(ServerPlayer.class, TargetingConditions.forNonCombat(), null, new JSGAxisAlignedBB(pos.offset(-radius, -radius, -radius), pos.offset(radius, radius, radius)));
        for (ServerPlayer player : players) {
            advancement.trigger(player);
        }
    }
}
