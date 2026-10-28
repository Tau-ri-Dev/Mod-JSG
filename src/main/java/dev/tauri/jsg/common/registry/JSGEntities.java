package dev.tauri.jsg.common.registry;

import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.entity.animal.MastadgeEntity;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobSpawnEvent;

@EventBusSubscriber
public class JSGEntities {
    public static final RegistryObjectWrapper<EntityType<MastadgeEntity>> MASTADGE = JSGApi.REGISTRY_HELPER.entity().register("mastadge", () -> EntityType.Builder.of(MastadgeEntity::new, MobCategory.CREATURE).sized(3f, 2.7f).setShouldReceiveVelocityUpdates(true).build("mastadge"));

    public static void init() {
    }

    @SubscribeEvent
    public static void onSpawnPlacementsRegister(MobSpawnEvent.SpawnPlacementCheck event) {
        // TODO: Investigate how this works
        //event.register(MASTADGE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MastadgeEntity::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }
}
