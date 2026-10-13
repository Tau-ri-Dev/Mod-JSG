package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.animation.IButtonsState;
import dev.tauri.jsg.api.nbt.LegacyNBTSerializable;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.blockentity.StateProviderInterface;
import dev.tauri.jsgcore.common.packet.TargetPoint;

public interface IDHDStateManager extends LegacyNBTSerializable, StateProviderInterface, ITickable {
    IButtonsState getButtonsState();

    TargetPoint getTargetPoint();
}
