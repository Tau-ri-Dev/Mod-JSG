package dev.tauri.jsg.client.renderer.blockentity.stargate;

import com.mojang.math.Axis;
import dev.tauri.jsg.api.stargate.ChevronEnum;
import dev.tauri.jsg.api.stargate.StargatePointOfOriginsDefaults;
import dev.tauri.jsg.api.stargate.iris.EnumIrisType;
import dev.tauri.jsg.common.loader.ElementEnum;
import dev.tauri.jsg.common.stargate.animation.chevron.StargateChevronsState;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

import javax.annotation.Nonnull;

public class StargateMovieRenderer extends StargateMilkyWayRenderer {
    public StargateMovieRenderer(BlockEntityRendererProvider.Context ignored) {
        super(ignored);
    }

    @Override
    protected void renderGate() {
        stack.pushPose();
        ElementEnum.MOVIE_GATE.bindTexture(rendererState.getBiomeOverlay()).render(stack, source, combinedLight);
        stack.popPose();
        renderRing();
        renderChevrons();
    }

    @Override
    protected void renderKawoosh() {
        stack.scale(1.01f, 1.01f, 1f);
        stack.translate(0, 0, -0.055);
        super.renderKawoosh();
    }

    @Override
    protected void translateIrisBlade() {
        stack.translate(0.1, 0, -0.05);
        rotateAround(stack, Axis.ZP.rotationDegrees(-0.5f), getIrisPivot());
    }

    @Override
    public void renderIris(boolean backOnly) {
        if (rendererState == null) return;
        EnumIrisType irisType = rendererState.irisType;
        stack.pushPose();
        if (irisType == null || irisType == EnumIrisType.SHIELD) {
            stack.scale(1.01f, 1.01f, 1f);
            super.renderIris(backOnly);
            stack.popPose();
            return;
        }
        super.renderIris(backOnly);
        stack.popPose();
    }

    @Override
    protected void renderRing() {
        stack.pushPose();
        tileEntity.getDialingManager().getSpinHelper().correctClientRingStartTime(level.getGameTime(), Minecraft.getInstance().gui.getGuiTicks());
        var angularRotation = (float) tileEntity.getDialingManager().getSpinHelper().apply(Minecraft.getInstance().gui.getGuiTicks() + partialTicks, true);


        stack.translate(RING_LOC.x, RING_LOC.z, RING_LOC.y);
        stack.mulPose(Axis.ZP.rotationDegrees(-angularRotation));
        stack.translate(-RING_LOC.x, -RING_LOC.z, -RING_LOC.y);

        ElementEnum.MOVIE_RING.bindTexture(rendererState.getBiomeOverlay()).render(stack, source, combinedLight);
        tileEntity.getSymbolType().getOrigin().getModel(tileEntity.getStargateType(), tileEntity.getPointOfOrigin(), StargatePointOfOriginsDefaults.VARIANT_GATE).render(stack, source, combinedLight);

        stack.popPose();
    }

    @Override
    protected void renderChevron(ChevronEnum chevron, StargateChevronsState.ChevronState state, float color, boolean onlyLight) {
        if (onlyLight) return;
        stack.pushPose();
        stack.mulPose(Axis.ZP.rotationDegrees(chevron.rotation));

        getTextureLoader().getTexture(getTextureLoader().getTextureResource("movie/chevron" + rendererState.getBiomeOverlay().suffix() + ".png")).bindTexture();

        float chevronOffset = chevron.isFinal() ? 0 : state.getOffset(partialTicks, 2);

        stack.pushPose();

        stack.translate(0, chevronOffset, 0);
        ElementEnum.MOVIE_CHEVRON_LIGHT.render(stack, source, combinedLight);

        stack.translate(0, -2 * chevronOffset, 0);
        if (chevron.isFinal())
            ElementEnum.MOVIE_CHEVRON_MOVING_TOP.render(stack, source, combinedLight);
        else
            ElementEnum.MOVIE_CHEVRON_MOVING.render(stack, source, combinedLight);

        stack.popPose();

        ElementEnum.MOVIE_CHEVRON_FRAME.bindTexture(rendererState.getBiomeOverlay()).render(stack, source, combinedLight);
        ElementEnum.MOVIE_CHEVRON_BACK.render(stack, source, combinedLight);

        stack.popPose();
    }

    @Override
    public boolean shouldRenderBackVortex() {
        return true;
    }

    @Override
    @Nonnull
    public Pair<Integer, Integer> getEventHorizonColor() {
        return Pair.of(0xff415063, 0xffffffff);
    }
}
