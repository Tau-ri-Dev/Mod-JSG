package dev.tauri.jsg.api.power;

import dev.tauri.jsg.api.config.JSGConfig;
import dev.tauri.jsgcore.common.power.general.EnergyRequiredToOperate;
import dev.tauri.jsgcore.common.power.general.LargeEnergyStorage;
import dev.tauri.jsgcore.common.power.general.SmallEnergyStorage;

public class PowerUtils {
    public static LargeEnergyStorage getLarge(Runnable onChanged) {
        return new LargeEnergyStorage(JSGConfig.Stargate.stargateEnergyStorage.get(), Long.MAX_VALUE, Long.MAX_VALUE) {
            @Override
            public void onEnergyChanged() {
                onChanged.run();
            }
        };
    }

    public static SmallEnergyStorage getSmall(Runnable onChanged) {
        return getSmall(JSGConfig.Stargate.stargateEnergyStorage.get(), onChanged);
    }

    public static SmallEnergyStorage getSmall(long capacity, Runnable onChanged) {
        return new SmallEnergyStorage(capacity, Long.MAX_VALUE, Long.MAX_VALUE) {
            @Override
            public void onEnergyChanged() {
                onChanged.run();
            }
        };
    }

    public static EnergyRequiredToOperate stargateConsumption() {
        return new EnergyRequiredToOperate(JSGConfig.Stargate.openingBlockToEnergyRatio.get(), JSGConfig.Stargate.keepAliveBlockToEnergyRatioPerTick.get());
    }
}
