package dev.tauri.jsg.api.dialhomedevice.manager;

import dev.tauri.jsg.api.dialhomedevice.DHDReactorState;
import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.util.FluidTank;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IDHDReactorManager extends INBTSerializable<CompoundTag>, ITickable {
    DHDReactorState getState();

    FluidTank getTank();
}
