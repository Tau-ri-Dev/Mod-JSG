package dev.tauri.jsg.datagen.tag;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.tags.JSGEntitiesTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class JSGEntityTagGenerator extends EntityTypeTagsProvider {

    public JSGEntityTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, provider, JSG.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        tag(JSGEntitiesTags.STARGATE_UNTRANSPORTABLE_ENTITIES)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.ELDER_GUARDIAN)
                .add(EntityType.WARDEN)
                .add(EntityType.WITHER);
    }




}
