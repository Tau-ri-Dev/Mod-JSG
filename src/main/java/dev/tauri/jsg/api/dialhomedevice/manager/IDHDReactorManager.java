package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.DHDReactorState;
import dev.tauri.jsg.api.nbt.LegacyNBTSerializable;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.util.FluidTank;

public interface IDHDReactorManager extends LegacyNBTSerializable, ITickable {
    DHDReactorState getState();

    FluidTank getTank();
}
