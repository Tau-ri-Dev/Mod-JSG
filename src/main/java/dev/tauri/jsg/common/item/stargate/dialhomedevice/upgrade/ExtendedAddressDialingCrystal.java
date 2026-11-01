package dev.tauri.jsg.common.item.stargate.dialhomedevice.upgrade;

import dev.tauri.jsg.api.dialhomedevice.StargateDHD;
import dev.tauri.jsg.api.dialhomedevice.upgrade.DHDUpgradeBehaviorImpl;
import dev.tauri.jsg.api.dialhomedevice.upgrade.IDHDUpgrade;
import dev.tauri.jsg.api.dialhomedevice.upgrade.IDHDUpgradeBehavior;
import dev.tauri.jsg.api.dialhomedevice.upgrade.IDHDUpgradeItem;
import dev.tauri.jsg.api.stargate.network.address.StargateAddressDynamic;
import dev.tauri.jsgcore.common.helper.ItemHelper;
import dev.tauri.jsgcore.common.item.JSGItem;
import dev.tauri.jsgcore.common.registry.CoreTabs;
import dev.tauri.jsgcore.common.util.IUpgrade;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

/**
 * Item upgrade which enabled dialing 1 more symbol on DHD
 */
public class ExtendedAddressDialingCrystal extends JSGItem implements IDHDUpgradeItem {
    public ExtendedAddressDialingCrystal() {
        super(new Properties(), List.of(CoreTabs.TAB_UPGRADES));
    }

    @Override
    @ParametersAreNonnullByDefault
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        ItemHelper.applyGenericToolTip(this.getDescriptionId(), components, tooltipFlag);
    }

    @Override
    public IUpgrade getUpgrade() {
        return new Upgrade();
    }

    public static class Upgrade implements IDHDUpgrade {
        @Override
        public IDHDUpgradeBehavior createBehavior(StargateDHD dhd) {
            return new DHDUpgradeBehaviorImpl() {
                @Override
                public int modifyMaxSymbolsInAddress(int symbolsCount, @Nullable StargateAddressDynamic dialedAddress) {
                    return Math.min(symbolsCount + 1, 9);
                }
            };
        }
    }
}
