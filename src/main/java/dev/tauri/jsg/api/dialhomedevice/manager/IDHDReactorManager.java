package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.DHDReactorState;
import dev.tauri.jsg.common.capability.DHDFluidHandlerItemStack;
import dev.tauri.jsgcore.common.blockentity.ITickable;

public interface IDHDReactorManager extends ITickable {
    DHDReactorState getState();

    DHDFluidHandlerItemStack getTank();
}
