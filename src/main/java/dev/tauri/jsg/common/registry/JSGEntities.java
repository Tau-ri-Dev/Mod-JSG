package dev.tauri.jsg.common.registry;

import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.entity.animal.MastadgeEntity;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class JSGEntities {
    public static final RegistryObjectWrapper<EntityType<MastadgeEntity>> MASTADGE = JSGApi.REGISTRY_HELPER.entity().register("mastadge", () -> EntityType.Builder.of(MastadgeEntity::new, MobCategory.CREATURE).sized(3f, 2.7f).setShouldReceiveVelocityUpdates(true).build("mastadge"));

    public static void init() {
    }
}
