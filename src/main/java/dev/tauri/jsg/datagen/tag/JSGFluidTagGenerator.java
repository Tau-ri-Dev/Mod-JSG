package dev.tauri.jsg.datagen.tag;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.common.registry.tags.JSGFluidTags;
import dev.tauri.jsgcore.common.definition.material.MaterialType;
import dev.tauri.jsgcore.datagen.tag.CoreFluidTagGenerator;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

public class JSGFluidTagGenerator extends CoreFluidTagGenerator {
    public JSGFluidTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pProvider, JSG.MOD_ID, existingFileHelper);
    }

    @Override
    @ParametersAreNonnullByDefault
    protected void addTags(HolderLookup.Provider provider) {
        tag(JSGFluidTags.DHD_FUEL).add(MaterialType.REFINED_NAQUADAH.getFluid().get());
    }
}
