package dev.tauri.jsg.common.worldgen.generator;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class StargateDimensionStatusHolder {
    protected final ResourceKey<Level> dimension;
    protected StargateGeneratorDimStatus status = StargateGeneratorDimStatus.QUEUED;

    public StargateDimensionStatusHolder(ResourceKey<Level> dimension) {
        this.dimension = dimension;
    }

    public void update(StargateGeneratorDimStatus status) {
        this.status = status;
    }

    public StargateGeneratorDimStatus status() {
        return status;
    }

    public ResourceKey<Level> dimension() {
        return dimension;
    }
}
