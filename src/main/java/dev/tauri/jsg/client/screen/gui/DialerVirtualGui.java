package dev.tauri.jsg.client.screen.gui;

import dev.tauri.jsg.common.packet.JSGPacketHandler;
import dev.tauri.jsg.common.packet.packets.linkable.UniverseDialerKeyPressedToServer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.ScreenEvent;
import net.neoforged.neoforge.common.NeoForge;

import javax.annotation.ParametersAreNonnullByDefault;

@OnlyIn(Dist.CLIENT)
public class DialerVirtualGui extends Screen {
    public DialerVirtualGui() {
        super(Component.literal("Remote Dialer"));
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

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        }
        if (pKeyCode == 259) { // backspace
           JSGPacketHandler.sendToServer(new UniverseDialerKeyPressedToServer(' ', true, Screen.hasShiftDown(), Screen.hasAltDown(), Screen.hasControlDown()));
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (net.minecraft.util.StringUtil.isAllowedChatCharacter(pCodePoint)) {
            JSGPacketHandler.sendToServer(new UniverseDialerKeyPressedToServer(pCodePoint, false, Screen.hasShiftDown(), Screen.hasAltDown(), Screen.hasControlDown()));
            return true;
        }
        return false;
    }
}
