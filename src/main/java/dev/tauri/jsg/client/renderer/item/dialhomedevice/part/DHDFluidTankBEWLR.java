package dev.tauri.jsg.client.renderer.item.dialhomedevice.part;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.tauri.jsg.client.renderer.item.dialhomedevice.DHDAbstractBEWLR;
import dev.tauri.jsg.common.loader.ElementEnum;
import dev.tauri.jsgcore.client.renderer.BlockRenderer;
import dev.tauri.jsgcore.client.texture.ITexture;
import dev.tauri.jsgcore.common.definition.material.MaterialType;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.fluids.FluidStack;

import java.awt.*;

public class DHDFluidTankBEWLR extends DHDAbstractBEWLR {
    @Override
    public void renderDHD(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack stack, MultiBufferSource bufferSource, int light, int overlay, float partialTick) {
        if (itemDisplayContext == ItemDisplayContext.GROUND) {
            stack.scale(5f, 5f, 5f);
            stack.translate(0, -0.1, 0);
        } else if (itemDisplayContext.firstPerson()) {
            stack.scale(5f, 5f, 5f);
            stack.translate(0, 0.17, -0.05);
            stack.mulPose(Axis.YP.rotationDegrees(180));
        } else if (itemDisplayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || itemDisplayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND) {
            stack.scale(5f, 5f, 5f);
            stack.translate(0, 0, -0.05);
            stack.mulPose(Axis.YP.rotationDegrees(180));
        } else if (itemDisplayContext == ItemDisplayContext.GUI) {
            stack.scale(9f, 9f, 9f);
            stack.translate(0, -0.03, 0);
            stack.mulPose(Axis.YP.rotationDegrees(180));
        } else if (itemDisplayContext == ItemDisplayContext.FIXED) {
            stack.translate(1, 0, -1);
            stack.scale(5f, 5f, 5f);
            stack.translate(-0.03, 0.3, 0);
        }
        ElementEnum.MILKYWAY_DHD_FLUID_TANK_BASE.bindTexture()
                .render(stack, bufferSource, light, false, 1, true);
        RenderSystem.enableBlend();
        TextureAtlasSprite sprite = BlockRenderer.getFluidTexture(new FluidStack(MaterialType.REFINED_NAQUADAH.getFluid().get(), 1000), BlockRenderer.FluidTextureType.STILL);
        if (sprite != null) {
            stack.pushPose();
            float height = itemStack.getCapability(ForgeCapabilities.FLUID_HANDLER_ITEM)
                    .map(c -> (float) ((double) c.getFluidInTank(0).getAmount() / (double) Math.max(1, c.getTankCapacity(0))))
                    .orElse(0f);
            stack.scale(1, height, 1);
            ITexture.bindTextureWithMc(sprite.atlasLocation());
            var extension = IClientFluidTypeExtensions.of(MaterialType.REFINED_NAQUADAH.getFluid().type.get());
            var color = new Color(extension.getTintColor(), true);
            ElementEnum.MILKYWAY_DHD_FLUID_TANK_FLUID
                    .render(stack, bufferSource, light, overlay, false, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f, true, sprite);
            stack.popPose();
        }
        ElementEnum.MILKYWAY_DHD_FLUID_TANK_GLASS
                .bindTexture()
                .render(stack, bufferSource, light, overlay, false, 1, true);
        RenderSystem.disableBlend();
    }

    @Override
    public boolean renderHands(ItemDisplayContext itemDisplayContext) {
        return true;
    }
}
