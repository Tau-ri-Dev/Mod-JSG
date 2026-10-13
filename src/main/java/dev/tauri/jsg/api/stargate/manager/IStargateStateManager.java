package dev.tauri.jsg.api.stargate.manager;

import dev.tauri.jsg.api.nbt.LegacyNBTSerializable;
import dev.tauri.jsg.api.stargate.animation.AbstractBlackHoleAnimationState;
import dev.tauri.jsg.api.stargate.animation.IChevronsState;
import dev.tauri.jsgcore.common.blockentity.IStateProvider;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.packet.TargetPoint;

public interface IStargateStateManager extends LegacyNBTSerializable, IStateProvider, ITickable {
    IChevronsState getChevronsState();

    AbstractBlackHoleAnimationState getBlackHoleAnimationState();

    TargetPoint getTargetPoint();
}
