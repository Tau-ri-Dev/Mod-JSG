package dev.tauri.jsg.client.screen.gui.sggenerator;

import dev.tauri.jsg.common.worldgen.generator.StargateDimensionStatusHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.function.Supplier;

import static net.minecraft.client.GameNarrator.NO_TITLE;

@OnlyIn(Dist.CLIENT)
public class StargateGeneratorScreen extends AbstractStargateGeneratorScreen {
    public StargateGeneratorScreen(Map<ResourceKey<Level>, StargateDimensionStatusHolder> stats, Supplier<Component> message) {
        super(NO_TITLE, () -> "createWorld.stargates_generating", stats, message);
    }
}
