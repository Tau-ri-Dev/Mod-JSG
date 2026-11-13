package dev.tauri.jsg.common.advancements;

import dev.tauri.jsg.JSG;
import dev.tauri.jsgcore.common.advancement.JSGCriterion;
import dev.tauri.jsgcore.mapping.JSGMapping;

public class JSGCriterions {
    /* OTHER */
    public static final JSGCriterion WORMHOLE_GO = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "wormhole_go"));
    public static final JSGCriterion IRIS_IMPACT = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "iris_impact"));
    public static final JSGCriterion GDO_USED = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "gdo_used"));
    public static final JSGCriterion KAWOOSH_CREMATION = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "kawoosh_cremation"));
    public static final JSGCriterion UNSTABLE_SURVIVE = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "unstable_eh_survive"));

    //ACTIVATE STARGATE
    public static final JSGCriterion ACTIVATE_ORLIN_GATE = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "activate_orlin_gate"));
    public static final JSGCriterion ACTIVATE_MILKYWAY_GATE = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "activate_milkyway_gate"));
    public static final JSGCriterion ACTIVATE_PEGASUS_GATE = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "activate_pegasus_gate"));
    public static final JSGCriterion ACTIVATE_UNIVERSE_GATE = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "activate_universe_gate"));
    public static final JSGCriterion ACTIVATE_TOLLAN_GATE = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "activate_tollan_gate"));
    public static final JSGCriterion ACTIVATE_MOVIE_GATE = new JSGCriterion(JSGMapping.rl(JSG.MOD_ID, "activate_movie_gate"));

    public static void init() {
    }
}
