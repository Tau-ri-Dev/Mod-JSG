package dev.tauri.jsg.client.renderer.item.dialhomedevice.part;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.api.stargate.StargatePointOfOriginsDefaults;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolPegasusEnum;
import dev.tauri.jsg.client.renderer.item.dialhomedevice.DHDAbstractBEWLR;
import dev.tauri.jsg.client.renderer.item.dialhomedevice.DHDPegasusBEWLR;
import dev.tauri.jsg.common.loader.ElementEnum;
import dev.tauri.jsgcore.common.registry.CoreBiomeOverlays;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DHDPegasusButtonsConsoleBEWLR extends DHDAbstractBEWLR {
    @Override
    public void renderDHD(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay, float partialTick) {
        if (itemDisplayContext == ItemDisplayContext.GROUND) {
            stack.scale(2, 2, 2);
            stack.translate(0, -0.7, 0);
        } else if (itemDisplayContext.firstPerson()) {
            stack.translate(0, 0.2, 0);
        } else if (itemDisplayContext == ItemDisplayContext.GUI) {
            stack.scale(1.8f, 1.8f, 1.8f);
            stack.translate(0, -0.7, 0);
        }
        ElementEnum.PEGASUS_DHD_BUTTON_CONSOLE.bindTexture().render(stack, bufferSource, light, false, 1, true);
        // render symbols
        for (SymbolPegasusEnum symbol : SymbolPegasusEnum.values()) {
            if (symbol.brb()) continue;
            stack.pushPose();
            JSGApi.JSG_LOADERS_HOLDER.texture().getTexture(symbol.brb() ? DHDPegasusBEWLR.BRB_TEX : DHDPegasusBEWLR.SYMBOLS_TEX).bindTexture();
            symbol.getModel(symbol.getSymbolType().getPointOfOriginType(), null, StargatePointOfOriginsDefaults.VARIANT_DHD_LIGHT).render(stack, bufferSource, light, false, 1, true);
            if (symbol.brb())
                ElementEnum.PEGASUS_DHD_BASE.bindTexture(CoreBiomeOverlays.NORMAL);
            symbol.getModel(symbol.getSymbolType().getPointOfOriginType(), null, StargatePointOfOriginsDefaults.VARIANT_DHD).render(stack, bufferSource, light, false, 1, true);
            stack.popPose();
        }
    }

    @Override
    public boolean renderHands(ItemDisplayContext itemDisplayContext) {
        return true;
    }
}
