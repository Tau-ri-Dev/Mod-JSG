package dev.tauri.jsg.client.renderer.activation;

import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.CompoundTag;

public class DHDActivation extends dev.tauri.jsgcore.client.renderer.Activation<SymbolInterface> {

    public DHDActivation(SymbolInterface textureKey, CompoundTag compoundTag) {
        super(textureKey, compoundTag);
    }

    public DHDActivation(SymbolInterface textureKey, ByteBuf buf) {
        super(textureKey, buf);
    }

    public DHDActivation(SymbolInterface textureKey, long stateChange, boolean dim) {
        super(textureKey, stateChange, dim);
    }

    @Override
    protected float getMaxStage() {
        return 5;
    }

    @Override
    protected float getTickMultiplier() {
        return (textureKey.origin() && !dim) ? 1 : 2;
    }
}
