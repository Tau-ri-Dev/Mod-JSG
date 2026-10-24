package dev.tauri.jsg.api.registry;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.api.stargate.network.address.ReservedStargateAddress;
import dev.tauri.jsg.api.stargate.type.StargateType;
import dev.tauri.jsg.common.item.linkable.dialer.UniverseDialerMode;
import dev.tauri.jsg.common.stargate.rig.RIGWave;
import dev.tauri.jsgcore.mapping.JSGMapping;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DataPackRegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = JSG.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class JSGRegistries {
    private static final List<DeferredRegister<?>> REGISTERS = new ArrayList<>();

    public static final ResourceKey<Registry<StargateType<?>>> STARGATE_TYPE = ResourceKey.createRegistryKey(JSGMapping.rl(JSGApi.MOD_ID, "stargate_type"));
    public static final ResourceKey<Registry<RIGWave>> RIG_WAVES = ResourceKey.createRegistryKey(JSGMapping.rl(JSGApi.MOD_ID, "rig_waves"));
    public static final ResourceKey<Registry<UniverseDialerMode>> UNIVERSE_DIALER_MODES = ResourceKey.createRegistryKey(JSGMapping.rl(JSGApi.MOD_ID, "universe_dialer_modes"));
    public static final ResourceKey<Registry<ReservedStargateAddress>> RESERVED_ADDRESSES = ResourceKey.createRegistryKey(JSGMapping.rl(JSGApi.MOD_ID, "reserved_addresses"));

    public static final Supplier<IForgeRegistry<StargateType<?>>> R_STARGATE_TYPE = create(STARGATE_TYPE);
    public static final Function<RegistryAccess, Registry<RIGWave>> R_RIG_WAVES = (access) -> access.registryOrThrow(RIG_WAVES);
    public static final Supplier<IForgeRegistry<UniverseDialerMode>> R_UNIVERSE_DIALER_MODES = create(UNIVERSE_DIALER_MODES, (owner, stage) -> UniverseDialerMode.calculateModesSequence());
    public static final Function<RegistryAccess, Registry<ReservedStargateAddress>> R_RESERVED_ADDRESSES = (access) -> access.registryOrThrow(RESERVED_ADDRESSES);

    private static <T> Supplier<IForgeRegistry<T>> create(ResourceKey<Registry<T>> id, IForgeRegistry.BakeCallback<T> onBake) {
        return create(id, () -> new RegistryBuilder<T>().setName(id.location()).onBake(onBake));
    }

    private static <T> Supplier<IForgeRegistry<T>> create(ResourceKey<Registry<T>> id) {
        return create(id, () -> RegistryBuilder.of(id.location()));
    }

    private static <T> Supplier<IForgeRegistry<T>> create(ResourceKey<Registry<T>> id, Supplier<RegistryBuilder<T>> builder) {
        DeferredRegister<T> dr = DeferredRegister.create(id, id.location().getNamespace());
        REGISTERS.add(dr);
        return dr.makeRegistry(builder);
    }

    public static void init() {

    }

    public static void register(IEventBus bus) {
        REGISTERS.forEach(dr -> dr.register(bus));
    }

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(RIG_WAVES, RIGWave.CODEC);
        event.dataPackRegistry(RESERVED_ADDRESSES, ReservedStargateAddress.CODEC);
    }
}
