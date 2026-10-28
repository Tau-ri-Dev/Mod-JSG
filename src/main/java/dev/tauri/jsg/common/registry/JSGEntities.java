package dev.tauri.jsg.common.registry;

import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.common.entity.animal.MastadgeEntity;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class JSGEntities {
    public static final RegistryObjectWrapper<EntityType<MastadgeEntity>> MASTADGE = JSGApi.REGISTRY_HELPER.entity().register("mastadge", () -> EntityType.Builder.of(MastadgeEntity::new, MobCategory.CREATURE).sized(3f, 2.7f).setShouldReceiveVelocityUpdates(true).build("mastadge"));

    public static void init() {
    }

    @SubscribeEvent
    public static void onSpawnPlacementsRegister(SpawnPlacementRegisterEvent event) {
        event.register(MASTADGE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MastadgeEntity::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }
}
