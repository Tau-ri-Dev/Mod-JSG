package dev.tauri.jsg.client.screen.provider;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.tauri.jsg.client.screen.gui.AbydosWarningGui;
import dev.tauri.jsg.client.screen.gui.sggenerator.StargateGeneratorScreen;
import dev.tauri.jsg.common.worldgen.generator.StargateDimensionStatusHolder;
import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.function.Supplier;

public class SGGeneratorGuiProvider {
    public static void showProgress(Map<ResourceKey<Level>, StargateDimensionStatusHolder> stats, Supplier<Component> message) {
        RenderSystem.recordRenderCall(() -> Minecraft.getInstance().forceSetScreen(new StargateGeneratorScreen(stats, message)));
    }

    public static void showAbydosWarning(BooleanConsumer callback) {
        RenderSystem.recordRenderCall(() -> Minecraft.getInstance().forceSetScreen(new AbydosWarningGui(callback)));
    }
}
