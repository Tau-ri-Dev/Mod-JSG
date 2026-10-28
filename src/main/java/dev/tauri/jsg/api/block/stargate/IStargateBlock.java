package dev.tauri.jsg.api.block.stargate;

import dev.tauri.jsg.api.stargate.type.StargateType;

import javax.annotation.Nonnull;

public interface IStargateBlock {
    @Nonnull
    StargateType<?> getStargateType();
}
