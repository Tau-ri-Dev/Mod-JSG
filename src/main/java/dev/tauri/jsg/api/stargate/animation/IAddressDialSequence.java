package dev.tauri.jsg.api.stargate.animation;

import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import dev.tauri.jsgcore.common.symbol.address.IAddress;
import net.minecraft.nbt.CompoundTag;
import dev.tauri.jsg.api.nbt.LegacyNBTSerializable;

public interface IAddressDialSequence extends LegacyNBTSerializable {
    interface DialNextConsumer {
        void accept(SymbolInterface symbol, boolean noEnergy, boolean ignoreMaxChevrons, EnumDialingType dialingType);
    }

    IAddress getOriginalAddress();
}
