package dev.tauri.jsg.api.stargate.animation;

import dev.tauri.jsgcore.common.nbt.LegacyNBTSerializable;
import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import dev.tauri.jsgcore.common.symbol.address.IAddress;

public interface IAddressDialSequence extends LegacyNBTSerializable {
    interface DialNextConsumer {
        void accept(SymbolInterface symbol, boolean noEnergy, boolean ignoreMaxChevrons, EnumDialingType dialingType);
    }

    IAddress getOriginalAddress();

    EnumDialingType getDialingType();

    boolean ignoreMaxChevrons();

    boolean noEnergy();
}
