package dev.tauri.jsg.api.registry;

import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsgcore.common.registry.helper.RegistryObjectWrapper;
import dev.tauri.jsgcore.common.symbol.SymbolUsage;

public class JSGSymbolUsages {
    public static final RegistryObjectWrapper<SymbolUsage> STARGATES = JSGApi.REGISTRY_HELPER.symbolUsage().register("stargates", () -> new SymbolUsage("stargates", JSGNotebookPageTypes.STARGATE_ADDRESS::get));

    public static void init() {
    }
}
