package dev.tauri.jsg.datagen.custom;

import com.mojang.serialization.JsonOps;
import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.registry.JSGRegistries;
import dev.tauri.jsg.api.registry.JSGSymbolTypes;
import dev.tauri.jsg.api.stargate.network.address.ReservedStargateAddress;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolMilkyWayEnum;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolPegasusEnum;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolUniverseEnum;
import dev.tauri.jsg.common.registry.JSGDimensions;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class JSGReservedAddressesProvider implements DataProvider {
    protected final PackOutput.PathProvider pathProvider;

    public JSGReservedAddressesProvider(PackOutput pOutput) {
        this.pathProvider = pOutput.createPathProvider(PackOutput.Target.DATA_PACK, CommonHooks.prefixNamespace(JSGRegistries.RESERVED_ADDRESSES.location()));
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        List<CompletableFuture<?>> list = new ArrayList<>();

        list.add(DataProvider.saveStable(pOutput, ReservedStargateAddress.CODEC.encodeStart(JsonOps.INSTANCE, ReservedStargateAddress.builder()
                .setAddress(JSGSymbolTypes.MILKYWAY.get(),
                        SymbolMilkyWayEnum.AURIGA,
                        SymbolMilkyWayEnum.CETUS,
                        SymbolMilkyWayEnum.CENTAURUS,
                        SymbolMilkyWayEnum.CANCER,
                        SymbolMilkyWayEnum.SCUTUM,
                        SymbolMilkyWayEnum.ERIDANUS,
                        SymbolMilkyWayEnum.CANIS_MINOR,
                        SymbolMilkyWayEnum.SERPENS_CAPUT
                )
                .setAddress(JSGSymbolTypes.PEGASUS.get(),
                        SymbolPegasusEnum.BYDO,
                        SymbolPegasusEnum.ZEO,
                        SymbolPegasusEnum.ONCE_EL,
                        SymbolPegasusEnum.DAWNRE,
                        SymbolPegasusEnum.RAMNON,
                        SymbolPegasusEnum.GILLTIN,
                        SymbolPegasusEnum.ELENAMI,
                        SymbolPegasusEnum.RECKTIC
                )
                .setAddress(JSGSymbolTypes.UNIVERSE.get(),
                        SymbolUniverseEnum.G10,
                        SymbolUniverseEnum.G15,
                        SymbolUniverseEnum.G20,
                        SymbolUniverseEnum.G26,
                        SymbolUniverseEnum.G28,
                        SymbolUniverseEnum.G13,
                        SymbolUniverseEnum.G18,
                        SymbolUniverseEnum.G6
                )
                .build(Level.OVERWORLD)).getOrThrow(), pathProvider.json(JSGMapping.rl(JSG.MOD_ID, "overworld"))));

        list.add(DataProvider.saveStable(pOutput, ReservedStargateAddress.CODEC.encodeStart(JsonOps.INSTANCE, ReservedStargateAddress.builder()
                .setAddress(JSGSymbolTypes.MILKYWAY.get(),
                        SymbolMilkyWayEnum.TAURUS,
                        SymbolMilkyWayEnum.SERPENS_CAPUT,
                        SymbolMilkyWayEnum.CAPRICORNUS,
                        SymbolMilkyWayEnum.MONOCEROS,
                        SymbolMilkyWayEnum.SAGITTARIUS,
                        SymbolMilkyWayEnum.ORION,
                        SymbolMilkyWayEnum.CANIS_MINOR,
                        SymbolMilkyWayEnum.AURIGA
                )
                .setAddress(JSGSymbolTypes.PEGASUS.get(),
                        SymbolPegasusEnum.ACJESIS,
                        SymbolPegasusEnum.BASELAI,
                        SymbolPegasusEnum.DANAMI,
                        SymbolPegasusEnum.ECRUMIG,
                        SymbolPegasusEnum.AAXEL,
                        SymbolPegasusEnum.GILLTIN,
                        SymbolPegasusEnum.HACEMILL,
                        SymbolPegasusEnum.RECKTIC
                )
                .setAddress(JSGSymbolTypes.UNIVERSE.get(),
                        SymbolUniverseEnum.G5,
                        SymbolUniverseEnum.G6,
                        SymbolUniverseEnum.G18,
                        SymbolUniverseEnum.G20,
                        SymbolUniverseEnum.G1,
                        SymbolUniverseEnum.G7,
                        SymbolUniverseEnum.G22,
                        SymbolUniverseEnum.G31
                )
                .build(JSGDimensions.ABYDOS)).getOrThrow(), pathProvider.json(JSGMapping.rl(JSG.MOD_ID, "abydos"))));

        list.add(DataProvider.saveStable(pOutput, ReservedStargateAddress.CODEC.encodeStart(JsonOps.INSTANCE, ReservedStargateAddress.builder()
                .setAddress(JSGSymbolTypes.MILKYWAY.get(),
                        SymbolMilkyWayEnum.LYNX,
                        SymbolMilkyWayEnum.VIRGO,
                        SymbolMilkyWayEnum.CANIS_MINOR,
                        SymbolMilkyWayEnum.ERIDANUS,
                        SymbolMilkyWayEnum.CENTAURUS,
                        SymbolMilkyWayEnum.EQUULEUS,
                        SymbolMilkyWayEnum.SEXTANS,
                        SymbolMilkyWayEnum.MICROSCOPIUM
                )
                .setAddress(JSGSymbolTypes.PEGASUS.get(),
                        SymbolPegasusEnum.BASELAI,
                        SymbolPegasusEnum.ACJESIS,
                        SymbolPegasusEnum.DANAMI,
                        SymbolPegasusEnum.ECRUMIG,
                        SymbolPegasusEnum.AAXEL,
                        SymbolPegasusEnum.GILLTIN,
                        SymbolPegasusEnum.HACEMILL,
                        SymbolPegasusEnum.RECKTIC
                )
                .setAddress(JSGSymbolTypes.UNIVERSE.get(),
                        SymbolUniverseEnum.G6,
                        SymbolUniverseEnum.G5,
                        SymbolUniverseEnum.G18,
                        SymbolUniverseEnum.G20,
                        SymbolUniverseEnum.G1,
                        SymbolUniverseEnum.G7,
                        SymbolUniverseEnum.G22,
                        SymbolUniverseEnum.G31
                )
                .build(Level.NETHER)).getOrThrow(), pathProvider.json(JSGMapping.rl(JSG.MOD_ID, "nether"))));

        list.add(DataProvider.saveStable(pOutput, ReservedStargateAddress.CODEC.encodeStart(JsonOps.INSTANCE, ReservedStargateAddress.builder()
                .setAddress(JSGSymbolTypes.MILKYWAY.get(),
                        SymbolMilkyWayEnum.MICROSCOPIUM,
                        SymbolMilkyWayEnum.PISCES,
                        SymbolMilkyWayEnum.PISCIS_AUSTRINUS,
                        SymbolMilkyWayEnum.SCUTUM,
                        SymbolMilkyWayEnum.ORION,
                        SymbolMilkyWayEnum.SERPENS_CAPUT,
                        SymbolMilkyWayEnum.GEMINI,
                        SymbolMilkyWayEnum.NORMA
                )
                .setAddress(JSGSymbolTypes.PEGASUS.get(),
                        SymbolPegasusEnum.OLAVII,
                        SymbolPegasusEnum.BASELAI,
                        SymbolPegasusEnum.CA_PO,
                        SymbolPegasusEnum.ZAMILLOZ,
                        SymbolPegasusEnum.TAHNAN,
                        SymbolPegasusEnum.ILLUME,
                        SymbolPegasusEnum.ZEO,
                        SymbolPegasusEnum.POCORE
                )
                .setAddress(JSGSymbolTypes.UNIVERSE.get(),
                        SymbolUniverseEnum.G3,
                        SymbolUniverseEnum.G30,
                        SymbolUniverseEnum.G11,
                        SymbolUniverseEnum.G14,
                        SymbolUniverseEnum.G31,
                        SymbolUniverseEnum.G9,
                        SymbolUniverseEnum.G1,
                        SymbolUniverseEnum.G22
                )
                .build(Level.END)).getOrThrow(), pathProvider.json(JSGMapping.rl(JSG.MOD_ID, "end"))));

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    @Override
    public String getName() {
        return "Reserved Addresses";
    }
}
