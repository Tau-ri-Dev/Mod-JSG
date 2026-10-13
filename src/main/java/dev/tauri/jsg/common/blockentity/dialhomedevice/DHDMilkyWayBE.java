package dev.tauri.jsg.common.blockentity.dialhomedevice;

import dev.tauri.jsg.api.item.IDHDFluidTank;
import dev.tauri.jsg.api.item.IDHDPartItem;
import dev.tauri.jsg.api.registry.JSGSymbolTypes;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolMilkyWayEnum;
import dev.tauri.jsg.common.dialhomedevice.manager.state.DHDAbstractStateManager;
import dev.tauri.jsg.common.dialhomedevice.manager.state.DHDMilkyWayStateManager;
import dev.tauri.jsg.common.registry.JSGBlockEntities;
import dev.tauri.jsg.common.registry.JSGItems;
import dev.tauri.jsg.common.registry.JSGSoundEvents;
import dev.tauri.jsg.common.registry.tags.JSGBlockTags;
import dev.tauri.jsgcore.common.sound.ISoundEvent;
import dev.tauri.jsgcore.common.symbol.SymbolType;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.LinkedList;

public class DHDMilkyWayBE extends DHDAbstractBE {
    public DHDMilkyWayBE(BlockPos pos, BlockState state) {
        super(JSGBlockEntities.DHD_MILKYWAY.get(), pos, state);
    }

    @Override
    protected DHDAbstractStateManager<?, ?> createStateManager() {
        return new DHDMilkyWayStateManager(this);
    }

    @Override
    public SymbolType<SymbolMilkyWayEnum> getSymbolType() {
        return JSGSymbolTypes.MILKYWAY.get();
    }

    @Override
    public TagKey<Block> getLinkableBlocks() {
        return JSGBlockTags.DHD_MILKYWAY_LINKABLE_BLOCKS;
    }

    @Override
    public IDHDFluidTank getFluidTankItemPart() {
        return JSGItems.DHD_NAQUADAH_TANK.get();
    }

    @Override
    public ISoundEvent getButtonPressSound() {
        return JSGSoundEvents.DHD_MILKYWAY_PRESS;
    }

    @Override
    public ISoundEvent getBRBPressSound() {
        return JSGSoundEvents.DHD_MILKYWAY_PRESS_BRB;
    }

    @Override
    public Item getControlCrystal() {
        return JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL.get();
    }

    @Override
    public LinkedList<IDHDPartItem> getAllParts() {
        return Util.make(new LinkedList<>(), list -> {
            list.addLast(JSGItems.MILKYWAY_DHD_CONTROL_CRYSTALS.get());
            list.addLast(JSGItems.MILKYWAY_DHD_BUTTONS_CONSOLE.get());
            list.addLast(JSGItems.MILKYWAY_DHD_MAIN_CRYSTAL.get());
            list.addLast(JSGItems.MILKYWAY_DHD_ACTIVATION_BUTTON.get());
            list.addLast(JSGItems.DHD_NAQUADAH_TANK.get());
            list.addLast(JSGItems.MILKYWAY_DHD_UPGRADES_COVER.get());
        });
    }
}
