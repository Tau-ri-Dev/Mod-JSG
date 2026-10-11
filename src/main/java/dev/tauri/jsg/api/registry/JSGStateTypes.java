package dev.tauri.jsg.api.registry;

import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsgcore.common.entity.StateType;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;

public class JSGStateTypes {
    public static final RegistryObjectWrapper<StateType> SPIN_STATE = JSGApi.REGISTRY_HELPER.state().register("spin_state", () -> new StateType("spin_state"));
    public static final RegistryObjectWrapper<StateType> FLASH_STATE = JSGApi.REGISTRY_HELPER.state().register("flash_state", () -> new StateType("flash_state"));
    public static final RegistryObjectWrapper<StateType> IRIS_ANIMATION = JSGApi.REGISTRY_HELPER.state().register("iris_animation", () -> new StateType("iris_animation"));
    public static final RegistryObjectWrapper<StateType> STARGATE_VAPORIZE_BLOCK_PARTICLES = JSGApi.REGISTRY_HELPER.state().register("gate_vapor_block_particles", () -> new StateType("gate_vapor_block_particles"));
    public static final RegistryObjectWrapper<StateType> BLACK_HOLE_ANIMATION_UPDATE = JSGApi.REGISTRY_HELPER.state().register("black_hole_update", () -> new StateType("black_hole_update"));
    public static final RegistryObjectWrapper<StateType> CHEVRONS_STATE = JSGApi.REGISTRY_HELPER.state().register("chevrons_state", () -> new StateType("chevrons_state"));
    public static final RegistryObjectWrapper<StateType> BUTTONS_STATE = JSGApi.REGISTRY_HELPER.state().register("buttons_state", () -> new StateType("buttons_state"));
    public static final RegistryObjectWrapper<StateType> ORLIN_PARTICLE_STATE = JSGApi.REGISTRY_HELPER.state().register("orlin_particle_state", () -> new StateType("orlin_particle_state"));

    public static void init() {
    }
}
