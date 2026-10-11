package dev.tauri.jsg.api.dialhomedevice.animation;

import dev.tauri.jsgcore.common.blockentity.ITickable;
import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

import java.util.List;

public interface IButtonsState extends INBTSerializable<CompoundTag>, ITickable {
    IButtonState get(SymbolInterface symbol);

    List<SymbolInterface> getActivatedButtons();

    interface IButtonState {
        void activate();

        void deactivate();

        boolean isActive();
    }
}
