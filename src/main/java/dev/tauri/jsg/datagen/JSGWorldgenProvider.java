package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JSGWorldgenProvider extends DatapackBuiltinEntriesProvider {

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder();
            //.add(Registries.TEMPLATE_POOL, JSGTemplatePools::bootstrap);

    public JSGWorldgenProvider (PackOutput pOutput, CompletableFuture<HolderLookup.Provider> registries) {
        super(pOutput, registries, BUILDER, Set.of(JSG.MOD_ID));
    }
}
