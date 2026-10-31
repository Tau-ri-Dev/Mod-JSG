package dev.tauri.jsg.client.renderer.item.dialhomedevice.part;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.tauri.jsg.api.JSGApi;
import dev.tauri.jsg.api.stargate.StargatePointOfOriginsDefaults;
import dev.tauri.jsg.api.stargate.network.address.symbol.types.SymbolMilkyWayEnum;
import dev.tauri.jsg.api.stargate.type.StargateTypes;
import dev.tauri.jsg.client.renderer.item.dialhomedevice.DHDAbstractBEWLR;
import dev.tauri.jsg.client.renderer.item.dialhomedevice.DHDMilkyWayBEWLR;
import dev.tauri.jsg.common.loader.ElementEnum;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DHDMilkyWayButtonsConsoleBEWLR extends DHDAbstractBEWLR {
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
        } else if (itemDisplayContext == ItemDisplayContext.FIXED) {
            stack.translate(0.825, 1.65, -1.7);
            stack.mulPose(Axis.YP.rotationDegrees(180));
            stack.mulPose(Axis.XP.rotationDegrees(-60));
        } else if (itemDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            stack.translate(0, -0.5, 0);
        }
        ElementEnum.MILKYWAY_DHD_BUTTON_CONSOLE.bindTexture().render(stack, bufferSource, light, false, 1, true);
        // render symbols
        for (SymbolMilkyWayEnum symbol : SymbolMilkyWayEnum.values()) {
            if (symbol.brb()) continue;
            stack.pushPose();

            if (symbol.origin()) {
                // render plate for PoO
                var plate = JSGApi.JSG_LOADERS_HOLDER.model().getModelResource("milkyway/dhd/buttons/4_base.obj");
                var plateLight = JSGApi.JSG_LOADERS_HOLDER.model().getModelResource("milkyway/dhd/buttons/4.obj");
                ElementEnum.MILKYWAY_DHD_BASE.bindTexture();
                JSGApi.JSG_LOADERS_HOLDER.model().getModel(plate).render(stack, bufferSource, light, overlay, false, 1, true);

                JSGApi.JSG_LOADERS_HOLDER.texture().getTexture(DHDMilkyWayBEWLR.SYMBOLS_TEX).bindTexture();
                JSGApi.JSG_LOADERS_HOLDER.model().getModel(plateLight).render(stack, bufferSource, light, overlay, false, 1, true);
            }

            // render symbol base
            ElementEnum.MILKYWAY_DHD_BASE.bindTexture();
            symbol.getModel(StargateTypes.MILKYWAY.get(), null, StargatePointOfOriginsDefaults.VARIANT_DHD).render(stack, bufferSource, light, false, 1, true);

            // render symbol light emissive
            JSGApi.JSG_LOADERS_HOLDER.texture().getTexture((symbol.brb() ? DHDMilkyWayBEWLR.BRB_TEX : DHDMilkyWayBEWLR.SYMBOLS_TEX)).bindTexture();
            symbol.getModel(StargateTypes.MILKYWAY.get(), null, StargatePointOfOriginsDefaults.VARIANT_DHD_LIGHT).render(stack, bufferSource, light, false, 1, true);
            stack.popPose();
        }
    }

    @Override
    public boolean renderHands(ItemDisplayContext itemDisplayContext) {
        return true;
    }
}
