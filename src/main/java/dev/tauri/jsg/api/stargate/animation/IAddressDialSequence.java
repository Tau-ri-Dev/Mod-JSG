package dev.tauri.jsg.api.stargate.animation;

import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import dev.tauri.jsgcore.common.symbol.address.IAddress;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IAddressDialSequence extends INBTSerializable<CompoundTag> {
    interface DialNextConsumer {
        void accept(SymbolInterface symbol, boolean noEnergy, boolean ignoreMaxChevrons, EnumDialingType dialingType);
    }

    IAddress getOriginalAddress();
}
