package dev.tauri.jsg.datagen;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.JSGDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class JSGDamageTypeProvider extends DatapackBuiltinEntriesProvider {

    public JSGDamageTypeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(JSG.MOD_ID));
    }

    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.DAMAGE_TYPE, context -> {
                context.register(JSGDamageTypes.IRIS, new DamageType("iris", DamageScaling.NEVER, 0.1F));
                context.register(JSGDamageTypes.KAWOOSH, new DamageType("kawoosh", DamageScaling.NEVER, 0.1F));
                context.register(JSGDamageTypes.UNSTABLE_EH, new DamageType("unstable_eh", DamageScaling.NEVER, 0.1F));
                context.register(JSGDamageTypes.WRONG_SIDE, new DamageType("wrong_side", DamageScaling.NEVER, 0.1F));
            });
}
