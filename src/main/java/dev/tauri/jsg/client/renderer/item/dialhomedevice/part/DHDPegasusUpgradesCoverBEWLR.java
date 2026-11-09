package dev.tauri.jsg.client.renderer.item.dialhomedevice.part;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.tauri.jsg.client.renderer.item.dialhomedevice.DHDAbstractBEWLR;
import dev.tauri.jsg.common.loader.ElementEnum;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class DHDPegasusUpgradesCoverBEWLR extends DHDAbstractBEWLR {

    @Override
    public void renderDHD(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay, float partialTick) {
        stack.pushPose();
        if (itemDisplayContext == ItemDisplayContext.FIXED) {
            stack.mulPose(Axis.YP.rotationDegrees(180));
            stack.translate(-0.85, 1.7, 1.35);
            stack.mulPose(Axis.XP.rotationDegrees(-8));
        }
        stack.scale(2, 2, 2);
        if (itemDisplayContext.firstPerson()) {
            stack.translate(0, 0.3, 0);
        }
        if (itemDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            stack.translate(0, -0.3, 0);
        }
        if (itemDisplayContext == ItemDisplayContext.GUI) {
            stack.scale(2, 2, 2);
            stack.mulPose(Axis.YP.rotationDegrees(-30));
            stack.translate(0, -0.1, 0);
        }
        ElementEnum.PEGASUS_DHD_UPGRADE_COVER.bindTexture().render(stack, bufferSource, light);
        stack.popPose();
    }

    @Override
    public boolean renderHands(ItemDisplayContext itemDisplayContext) {
        return true;
    }
}
