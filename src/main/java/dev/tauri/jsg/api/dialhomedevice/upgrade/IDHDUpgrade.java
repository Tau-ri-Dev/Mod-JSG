package dev.tauri.jsg.api.dialhomedevice.upgrade;

import dev.tauri.jsg.api.dialhomedevice.StargateDHD;
import dev.tauri.jsgcore.common.util.IUpgrade;

public interface IDHDUpgrade extends IUpgrade {
    IDHDUpgradeBehavior createBehavior(StargateDHD dhd);
}
