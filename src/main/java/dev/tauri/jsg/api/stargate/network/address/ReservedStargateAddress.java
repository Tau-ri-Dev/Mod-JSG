package dev.tauri.jsg.api.stargate.network.address;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.tauri.jsg.api.registry.JSGRegistries;
import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.common.stargate.network.StargateNetwork;
import dev.tauri.jsgcore.common.registry.JSGCoreRegistries;
import dev.tauri.jsgcore.common.symbol.SymbolInterface;
import dev.tauri.jsgcore.common.symbol.SymbolType;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public record ReservedStargateAddress(ResourceKey<Level> dimension, Map<SymbolType<?>, StargateAddress> addresses) {
    public static final Codec<ReservedStargateAddress> CODEC = RecordCodecBuilder.create(reservedAddress -> reservedAddress.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(ReservedStargateAddress::dimension),
            Codec.unboundedMap(ResourceKey.codec(JSGCoreRegistries.SYMBOL_TYPE), Codec.STRING.listOf())
                    .fieldOf("addresses")
                    .forGetter(addr -> {
                        var map = new HashMap<ResourceKey<SymbolType<?>>, List<String>>();
                        addr.addresses().forEach((s, a) ->
                                map.put(ResourceKey.create(JSGCoreRegistries.SYMBOL_TYPE, s.getId()), a.getNameList()));
                        return map;
                    })
    ).apply(reservedAddress, (dim, map) -> {
        var newMap = new HashMap<SymbolType<?>, StargateAddress>();
        map.forEach((s, a) -> {
            var symbolType = JSGCoreRegistries.R_SYMBOL_TYPE.get().get(s.location());
            if (symbolType == null) return;
            var address = new StargateAddress(symbolType);
            address.address.addAll(a.stream()
                    .map(symbolType::fromEnglishName)
                    .toList()
            );
            newMap.put(
                    symbolType,
                    address
            );
        });
        return new ReservedStargateAddress(dim, newMap);
    }));

    @ParametersAreNonnullByDefault
    public static Optional<ReservedStargateAddress> getStargate(RegistryAccess access, ResourceKey<Level> dim, @Nullable List<ReservedStargateAddress> blackList) {
        return JSGRegistries.R_RESERVED_ADDRESSES.apply(access)
                .stream()
                .filter(ra -> ra.dimension().equals(dim))
                .filter(ra -> blackList == null || !blackList.contains(ra))
                .findAny();
    }

    public static Optional<ReservedStargateAddress> getStargate(@Nonnull ServerLevel dim, @Nullable List<ReservedStargateAddress> blackList) {
        return getStargate(dim.registryAccess(), dim.dimension(), blackList);
    }

    public void setAddresses(Stargate<?> baseTile) {
        for (var addressEntry : addresses.entrySet()) {
            baseTile.setGateAddress(addressEntry.getKey(), addressEntry.getValue());
        }
    }

    public boolean isGenerated() {
        for (var addressEntry : addresses.entrySet()) {
            if (StargateNetwork.INSTANCE.getStargate(addressEntry.getValue()) != null) return true;
        }
        return false;
    }

    public static AddressesBuilder builder() {
        return new AddressesBuilder();
    }

    public static class AddressesBuilder {
        Map<SymbolType<?>, StargateAddress> addressMap = new HashMap<>();

        public AddressesBuilder setAddress(SymbolType<?> type, SymbolInterface... symbols) {
            if (symbols.length < 8) return this;
            var address = new StargateAddress(type, symbols[0], symbols[1], symbols[2], symbols[3], symbols[4], symbols[5], symbols[6], symbols[7]);
            addressMap.put(type, address);
            return this;
        }

        public ReservedStargateAddress build(ResourceKey<Level> dimension) {
            return new ReservedStargateAddress(dimension, addressMap);
        }
    }
}
