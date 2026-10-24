package dev.tauri.jsg.client.screen.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
public class GDOVirtualGui extends Screen {
    public GDOVirtualGui() {
        super(Component.literal("GDO"));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    @ParametersAreNonnullByDefault
    @SuppressWarnings("all")
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        NeoForge.EVENT_BUS.post(new ScreenEvent.BackgroundRendered(this, guiGraphics));
    }
}
