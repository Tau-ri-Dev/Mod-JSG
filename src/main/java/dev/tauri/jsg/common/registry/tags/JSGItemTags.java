package dev.tauri.jsg.common.registry.tags;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class JSGItemTags {
    public static final TagKey<Item> IRIS_BLADES = tag("iris_blades");
    public static final TagKey<Item> STARGATE_CAPACITORS = tag("stargate_capacitors");
    public static final TagKey<Item> DIALING_GLOVES = tag("dialing_gloves");

    private static TagKey<Item> tag(String name) {
        return ItemTags.create(JSGMapping.rl(JSG.MOD_ID, name));
    }
}
