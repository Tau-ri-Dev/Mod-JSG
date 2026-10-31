package dev.tauri.jsg.common.item.stargate;

import dev.tauri.jsgcore.common.item.JSGItem;
import dev.tauri.jsgcore.common.registry.CoreTabs;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;

import java.util.List;

public class DialingGlovesItem extends JSGItem {
    public DialingGlovesItem() {
        super(new Properties(), List.of(() -> CoreTabs.TAB_TOOLS));
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, LevelReader level, BlockPos pos, Player player) {
        return true;
    }
}
