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

    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_START = createKey("abydos/city/starting_pieces");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_VILLAGERS = createKey("abydos/city/villagers");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_ANIMALS = createKey("abydos/city/animals");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_BRAZIERS = createKey("abydos/city/braziers");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_HOUSE_SMALL = createKey("abydos/city/buildings/8x4");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_HOUSE_MEDIUM = createKey("abydos/city/buildings/7x7");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_HOUSE_BIG = createKey("abydos/city/buildings/13x13");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_BACK_EAST_LONG = createKey("abydos/city/streets/back_east_long");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_BACK_EAST_SHORT = createKey("abydos/city/streets/back_east_short");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_BACK_WEST_LONG = createKey("abydos/city/streets/back_west_long");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_BACK_WEST_SHORT = createKey("abydos/city/streets/back_west_short");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_BACK_WEST_TWINS = createKey("abydos/city/streets/back_west_twins_tower");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_CENTER = createKey("abydos/city/streets/center");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_EAST_FRONT = createKey("abydos/city/streets/east_front");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_EAST_TO_SHORT = createKey("abydos/city/streets/east_to_short");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_FRONT_WEST = createKey("abydos/city/streets/front_west");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_GATE_TO_CENTER = createKey("abydos/city/streets/side_gate_to_center");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_MIDDLE_EAST = createKey("abydos/city/streets/side_middle_east");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_MIDDLE_EAST_SHORT = createKey("abydos/city/streets/side_middle_east_short");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_MIDDLE_EAST_U = createKey("abydos/city/streets/side_middle_east_u");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_MIDDLE_WEST = createKey("abydos/city/streets/side_middle_west");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_SIDE_EAST_NORTH = createKey("abydos/city/streets/side_east_north");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_SIDE_EAST_SOUTH = createKey("abydos/city/streets/side_east_south");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_SIDE_WEST = createKey("abydos/city/streets/side_west");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_STREET_TO_BACK = createKey("abydos/city/streets/to_back");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_BACK_EAST_ROW = createKey("abydos/city/walls/back_east_row");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_BACK_WEST_ROW = createKey("abydos/city/walls/back_west_row");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_EAST_NORTH_CORNER = createKey("abydos/city/walls/east_corner_to_north");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_EAST_WEST_CORNER = createKey("abydos/city/walls/east_corner_to_west");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_FINAL_BACK = createKey("abydos/city/walls/final_back");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_FRONT_EAST_ROW = createKey("abydos/city/walls/front_east_row");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_FRONT_WEST_ROW = createKey("abydos/city/walls/front_west_row");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_EAST_INNER_CORNER = createKey("abydos/city/walls/inner_corner_east");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_WEST_INNER_CORNER = createKey("abydos/city/walls/inner_corner_west");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_SIDE_EAST_NORTH = createKey("abydos/city/walls/side_east_north");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_SIDE_EAST_SOUTH = createKey("abydos/city/walls/side_east_south");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_WEST_EAST_CORNER = createKey("abydos/city/walls/west_corner_to_east");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_WEST_NORTH_CORNER = createKey("abydos/city/walls/west_corner_to_norht");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CITY_WALL_WEST_SIDE = createKey("abydos/city/walls/west_side");

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
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_1 = createKey("abydos/cartouche/cartouches/back_1");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_2 = createKey("abydos/cartouche/cartouches/back_2");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_3 = createKey("abydos/cartouche/cartouches/back_3");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_4 = createKey("abydos/cartouche/cartouches/back_4");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_5 = createKey("abydos/cartouche/cartouches/back_5");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_6 = createKey("abydos/cartouche/cartouches/back_6");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_7 = createKey("abydos/cartouche/cartouches/back_7");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_8 = createKey("abydos/cartouche/cartouches/back_8");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_9 = createKey("abydos/cartouche/cartouches/back_9");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_10 = createKey("abydos/cartouche/cartouches/back_10");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_BACK_POS_11 = createKey("abydos/cartouche/cartouches/back_11");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_1 = createKey("abydos/cartouche/cartouches/left_1");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_2 = createKey("abydos/cartouche/cartouches/left_2");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_3 = createKey("abydos/cartouche/cartouches/left_3");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_4 = createKey("abydos/cartouche/cartouches/left_4");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_5 = createKey("abydos/cartouche/cartouches/left_5");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_6 = createKey("abydos/cartouche/cartouches/left_6");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_7 = createKey("abydos/cartouche/cartouches/left_7");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_8 = createKey("abydos/cartouche/cartouches/left_8");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_9 = createKey("abydos/cartouche/cartouches/left_9");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_10 = createKey("abydos/cartouche/cartouches/left_10");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_11 = createKey("abydos/cartouche/cartouches/left_11");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_12 = createKey("abydos/cartouche/cartouches/left_12");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_13 = createKey("abydos/cartouche/cartouches/left_13");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_14 = createKey("abydos/cartouche/cartouches/left_14");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_15 = createKey("abydos/cartouche/cartouches/left_15");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_16 = createKey("abydos/cartouche/cartouches/left_16");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_17 = createKey("abydos/cartouche/cartouches/left_17");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_18 = createKey("abydos/cartouche/cartouches/left_18");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_19 = createKey("abydos/cartouche/cartouches/left_19");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_20 = createKey("abydos/cartouche/cartouches/left_20");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_21 = createKey("abydos/cartouche/cartouches/left_21");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_22 = createKey("abydos/cartouche/cartouches/left_22");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_23 = createKey("abydos/cartouche/cartouches/left_23");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_24 = createKey("abydos/cartouche/cartouches/left_24");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_25 = createKey("abydos/cartouche/cartouches/left_25");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_26 = createKey("abydos/cartouche/cartouches/left_26");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_27 = createKey("abydos/cartouche/cartouches/left_27");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_28 = createKey("abydos/cartouche/cartouches/left_28");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_29 = createKey("abydos/cartouche/cartouches/left_29");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_30 = createKey("abydos/cartouche/cartouches/left_30");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_31 = createKey("abydos/cartouche/cartouches/left_31");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_32 = createKey("abydos/cartouche/cartouches/left_32");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_33 = createKey("abydos/cartouche/cartouches/left_33");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_34 = createKey("abydos/cartouche/cartouches/left_34");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_35 = createKey("abydos/cartouche/cartouches/left_35");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_36 = createKey("abydos/cartouche/cartouches/left_36");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_37 = createKey("abydos/cartouche/cartouches/left_37");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_38 = createKey("abydos/cartouche/cartouches/left_38");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_39 = createKey("abydos/cartouche/cartouches/left_39");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_LEFT_POS_40 = createKey("abydos/cartouche/cartouches/left_40");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_1 = createKey("abydos/cartouche/cartouches/right_1");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_2 = createKey("abydos/cartouche/cartouches/right_2");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_3 = createKey("abydos/cartouche/cartouches/right_3");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_4 = createKey("abydos/cartouche/cartouches/right_4");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_5 = createKey("abydos/cartouche/cartouches/right_5");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_6 = createKey("abydos/cartouche/cartouches/right_6");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_7 = createKey("abydos/cartouche/cartouches/right_7");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_8 = createKey("abydos/cartouche/cartouches/right_8");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_9 = createKey("abydos/cartouche/cartouches/right_9");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_10 = createKey("abydos/cartouche/cartouches/right_10");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_11 = createKey("abydos/cartouche/cartouches/right_11");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_12 = createKey("abydos/cartouche/cartouches/right_12");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_13 = createKey("abydos/cartouche/cartouches/right_13");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_14 = createKey("abydos/cartouche/cartouches/right_14");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_15 = createKey("abydos/cartouche/cartouches/right_15");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_16 = createKey("abydos/cartouche/cartouches/right_16");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_17 = createKey("abydos/cartouche/cartouches/right_17");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_18 = createKey("abydos/cartouche/cartouches/right_18");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_19 = createKey("abydos/cartouche/cartouches/right_19");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_20 = createKey("abydos/cartouche/cartouches/right_20");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_21 = createKey("abydos/cartouche/cartouches/right_21");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_22 = createKey("abydos/cartouche/cartouches/right_22");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_23 = createKey("abydos/cartouche/cartouches/right_23");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_24 = createKey("abydos/cartouche/cartouches/right_24");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_25 = createKey("abydos/cartouche/cartouches/right_25");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_26 = createKey("abydos/cartouche/cartouches/right_26");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_27 = createKey("abydos/cartouche/cartouches/right_27");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_28 = createKey("abydos/cartouche/cartouches/right_28");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_29 = createKey("abydos/cartouche/cartouches/right_29");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_30 = createKey("abydos/cartouche/cartouches/right_30");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_31 = createKey("abydos/cartouche/cartouches/right_31");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_32 = createKey("abydos/cartouche/cartouches/right_32");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_33 = createKey("abydos/cartouche/cartouches/right_33");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_34 = createKey("abydos/cartouche/cartouches/right_34");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_35 = createKey("abydos/cartouche/cartouches/right_35");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_36 = createKey("abydos/cartouche/cartouches/right_36");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_37 = createKey("abydos/cartouche/cartouches/right_37");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_38 = createKey("abydos/cartouche/cartouches/right_38");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_39 = createKey("abydos/cartouche/cartouches/right_39");
    public static final ResourceKey<StructureTemplatePool> ABYDOS_CARTOUCHE_RIGHT_POS_40 = createKey("abydos/cartouche/cartouches/right_40");





    private static ResourceKey<StructureTemplatePool> createKey(String path) {
        return ResourceKey.create(Registries.TEMPLATE_POOL, JSGMapping.rl(JSG.MOD_ID, path));
    }
}
