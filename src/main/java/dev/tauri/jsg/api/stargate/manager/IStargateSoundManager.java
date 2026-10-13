package dev.tauri.jsg.api.stargate.manager;

import dev.tauri.jsg.api.nbt.LegacyNBTSerializable;
import dev.tauri.jsgcore.common.blockentity.ITickable;

public interface IStargateSoundManager extends LegacyNBTSerializable, ITickable {
    void updateRingRollSound(boolean play);

    void updateShieldHummingSound(boolean play);

    void updateWormholeSound(boolean play);

    boolean isRingRollPlaying();
}
