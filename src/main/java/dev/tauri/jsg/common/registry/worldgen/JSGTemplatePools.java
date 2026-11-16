package dev.tauri.jsg.common.registry.worldgen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class JSGTemplatePools {

    //CONTAINERS

    //MINECRAFT VILLAGE CENTERS
    public static final ResourceKey<StructureTemplatePool> DESERT_VILLAGE_PRIEST = createKey("village/desert/priest_villagers");
    public static final ResourceKey<StructureTemplatePool> DESERT_VILLAGE_TOWN_CENTERS = createKey("village/desert/town_centers");
    public static final ResourceKey<StructureTemplatePool> PLAINS_VILLAGE_PRIEST = createKey("village/plains/priest_villagers");
    public static final ResourceKey<StructureTemplatePool> PLAINS_VILLAGE_TOWN_CENTERS = createKey("village/plains/town_centers");
    public static final ResourceKey<StructureTemplatePool> SAVANNA_VILLAGE_PRIEST = createKey("village/savanna/priest_villagers");
    public static final ResourceKey<StructureTemplatePool> SAVANNA_VILLAGE_TOWN_CENTERS = createKey("village/savanna/town_centers");
    public static final ResourceKey<StructureTemplatePool> SNOW_VILLAGE_PRIEST = createKey("village/snow/priest_villagers");
    public static final ResourceKey<StructureTemplatePool> SNOW_VILLAGE_TOWN_CENTERS = createKey("village/snow/town_centers");
    public static final ResourceKey<StructureTemplatePool> TAIGA_VILLAGE_PRIEST = createKey("village/taiga/priest_villagers");
    public static final ResourceKey<StructureTemplatePool> TAIGA_VILLAGE_TOWN_CENTERS = createKey("village/taiga/town_centers");

    public static final ResourceKey<StructureTemplatePool> VILLAGE_MILKYWAY_STARGATE_BURIED = createKey("village/parts/milkyway/buried");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_MILKYWAY_STARGATE_VERTICAL = createKey("village/parts/milkyway/verticals");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_MILKYWAY_STARGATE_VERTICAL_GROUND = createKey("village/parts/milkyway/verticals_in_ground");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_MILKYWAY_STARGATE_DHD_BURIED = createKey("village/parts/milkyway/dhds");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_MILKYWAY_STARGATE_DHD = createKey("village/parts/milkyway/dhds_buried");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_PEGASUS_STARGATE_VERTICAL = createKey("village/parts/pegasus/verticals");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_PEGASUS_STARGATE_VERTICAL_GROUND = createKey("village/parts/pegasus/verticals_in_ground");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_PEGASUS_STARGATE_DHD = createKey("village/parts/pegasus/dhds");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_TOLLAN_STARGATE_VERTICAL = createKey("village/parts/tollan/verticals");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_TOLLAN_STARGATE_VERTICAL_GROUND = createKey("village/parts/tollan/verticals_in_ground");
    public static final ResourceKey<StructureTemplatePool> VILLAGE_TOLLAN_STARGATE_DHD = createKey("village/parts/tollan/dhds");

    //ANCIENT CITIES
    public static final ResourceKey<StructureTemplatePool> ANCIENT_CITY_CENTER = createKey("ancient_city/city_center");

    //STARGATE PARTS

    //ABYDOS
    public static final ResourceKey<StructureTemplatePool> ABYDOS_BOULDER = createKey("abydos/ambient/boulders/naquadah_boulders");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_BOULDER_SLAVES = createKey("abydos/ambient/boulders/villagers");

    public static final ResourceKey<StructureTemplatePool> ABYDOS_CAMP_BASE = createKey("abydos/ambient/camps/base");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CAMP_SIDE = createKey("abydos/ambient/camps/side_of_camp");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_ANIMALS = createKey("abydos/ambient/camps/animals");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_VILLAGERS = createKey("abydos/ambient/camps/villagers");

    public static final ResourceKey<StructureTemplatePool> ABYDOS_DUNGEON_SURFACE = createKey("abydos/dungeon/surface");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_DUNGEON_CONNECTION = createKey("abydos/dungeon/connections");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_DUNGEON_STAIR = createKey("abydos/dungeon/stairs");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_DUNGEON_PART = createKey("abydos/dungeon/parts");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_DUNGEON_BRAZIER = createKey("abydos/dungeon/braziers");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_DUNGEON_TERMINATOR = createKey("abydos/dungeon/terminators");

    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_BACK = createKey("abydos/main_pyramid/backs");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_BRAZIER = createKey("abydos/main_pyramid/brazier");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_CENTRAL_FILL = createKey("abydos/main_pyramid/central_fills");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_DHD = createKey("abydos/main_pyramid/dhds");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_EYE_TREASURE_ROOM = createKey("abydos/main_pyramid/eye_treasure_rooms");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_FOYER = createKey("abydos/main_pyramid/foyers");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_FRONT = createKey("abydos/main_pyramid/fronts");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RINGS_PANELS = createKey("abydos/main_pyramid/gateroom_ring_panels");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RINGS = createKey("abydos/main_pyramid/gateroom_rings");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_GATEROOM = createKey("abydos/main_pyramid/gaterooms");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_LEFT_BACK = createKey("abydos/main_pyramid/left_backs");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_LEFT_FILL = createKey("abydos/main_pyramid/left_fills");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_LEFT_FRONT = createKey("abydos/main_pyramid/left_fronts");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_LEFT_PILLAR = createKey("abydos/main_pyramid/left_pillars");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_LEFT_PILLAR_BACK = createKey("abydos/main_pyramid/left_pillar_backup");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_LEFT = createKey("abydos/main_pyramid/lefts");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RAMP = createKey("abydos/main_pyramid/ramps");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RIGHT_BACK = createKey("abydos/main_pyramid/right_backs");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RIGHT_FRONT = createKey("abydos/main_pyramid/right_fronts");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RIGHT_FRONT_BACK = createKey("abydos/main_pyramid/right_front_backs");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RIGHT = createKey("abydos/main_pyramid/rights");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_RING_COVER = createKey("abydos/main_pyramid/ring_ceiling_covers");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_PYRAMID_STARGATE = createKey("abydos/main_pyramid/stargates");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_MAIN_TOP = createKey("abydos/main_pyramid/tops");

    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SURFACE_START = createKey("abydos/cartouche/cave");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SURFACE_BACK = createKey("abydos/cartouche/cave_back");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SURFACE_LEFT = createKey("abydos/cartouche/cave_left");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SURFACE_RIGHT = createKey("abydos/cartouche/cave_right");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BRAZIER = createKey("abydos/cartouche/braziers");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SECRET_STAIRS = createKey("abydos/cartouche/secret_stairs");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BIG_CARTOUCHE_BACK = createKey("abydos/cartouche/cartouche_back");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BIG_CARTOUCHE_ENTRY = createKey("abydos/cartouche/cartouche_entry");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_MAIN_STAIRS = createKey("abydos/cartouche/main_stairs");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_FIRST_CORRIDOR = createKey("abydos/cartouche/first_corridor");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SECOND_CORRIDOR = createKey("abydos/cartouche/second_corridor");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SMALL_CARTOUCHE = createKey("abydos/cartouche/small_cartouche");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_ADDRESS_EARTH = createKey("abydos/cartouche/cartouche_earth");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_SMALL_ROOMS = createKey("abydos/cartouche/small_treasure_rooms");


    private static ResourceKey<StructureTemplatePool> createKey(String path) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, JSGMapping.rl(JSG.MOD_ID, path));
    }
}
