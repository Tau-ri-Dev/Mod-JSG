package dev.tauri.jsg.common.state.stargate;

import dev.tauri.jsg.api.registry.JSGSymbolUsages;
import dev.tauri.jsg.api.stargate.network.address.StargateAddress;
import dev.tauri.jsgcore.common.config.ingame.BEConfig;
import dev.tauri.jsgcore.common.entity.State;
import dev.tauri.jsgcore.common.symbol.SymbolType;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.Map;

public class StargateContainerGuiState extends State {
    public StargateContainerGuiState(BEConfig config) {
        this.config = config;
    }

    public Map<SymbolType<?>, StargateAddress> gateAddressMap = new HashMap<>();
    public Map<SymbolType<?>, StargateAddress> gateAddressMapStatic = new HashMap<>();
    public BEConfig config;

    public StargateContainerGuiState(Map<SymbolType<?>, StargateAddress> gateAddressMap, Map<SymbolType<?>, StargateAddress> gateAddressMapStatic, BEConfig config) {
        this.gateAddressMap = gateAddressMap;
        this.gateAddressMapStatic = gateAddressMapStatic;
        this.config = config;
    }

    @Override
    public void toBytes(ByteBuf buff) {
        var buf = new FriendlyByteBuf(buff);
        for (SymbolType<?> symbolType : SymbolType.values(JSGSymbolUsages.STARGATES.get())) {
            gateAddressMap.get(symbolType).toBytes(buf);
            gateAddressMapStatic.get(symbolType).toBytes(buf);
        }

        config.toBytes(buf);
    }

    @Override
    public void fromBytes(ByteBuf buff) {
        var buf = new FriendlyByteBuf(buff);
        gateAddressMap = new HashMap<>(3);

        for (SymbolType<?> symbolType : SymbolType.values(JSGSymbolUsages.STARGATES.get())) {
            StargateAddress address = new StargateAddress(symbolType);
            address.fromBytes(buf);
            gateAddressMap.put(symbolType, address);

            address = new StargateAddress(symbolType);
            address.fromBytes(buf);
            gateAddressMapStatic.put(symbolType, address);
        }

        config.fromBytes(buf);
    }
}
