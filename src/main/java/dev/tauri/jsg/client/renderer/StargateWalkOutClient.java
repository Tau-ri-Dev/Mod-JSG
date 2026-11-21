package dev.tauri.jsg.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.config.JSGConfig;
import dev.tauri.jsg.common.packet.packets.stargate.StargateWalkOutToClient;
import dev.tauri.jsg.common.stargate.teleportation.walkout.EventHorizonPlane;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.RenderLivingEvent;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4fStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CLIENT SIDE half of the walk-out (see {@code StargateWalkOut}): hides the part of a traveller that is inside
 * an event horizon, pushes the local player out of (or into) the horizon, and flashes the screen on arrival
 * when the travel animation is off.
 */
@EventBusSubscriber(modid = JSG.MOD_ID, value = Dist.CLIENT)
public final class StargateWalkOutClient {
    /**
     * {@code arrived}: Minecraft slides a teleported entity to its new spot over a few ticks on other players'
     * screens, so it would be seen gliding across to the gate. It is kept hidden until it is actually there.
     */
    private record Active(int id, boolean departing, EventHorizonPlane plane, Vec3 start, long expires, double speed,
                          boolean hide, boolean[] arrived, boolean[] gone) {
    }

    private static final Map<Integer, Active> ACTIVE = new HashMap<>();
    /** A departing traveller is hidden once its trailing side is this far through the horizon. */
    private static final double GONE_DEPTH = 0.02;
    /** Walking in stops once the front of the body is this far through the horizon. */
    private static final double DEPART_DEPTH = 0.1;
    /** Within this distance (squared: 3 blocks) of the arrival spot, the traveller counts as at the gate. */
    private static final double ARRIVED_SQR = 9.0;
    /** The cut-off surface sits this far behind the horizon, clear of its ripples (under 0.01 blocks). */
    private static final double CUT_OFF_DEPTH = 0.03;
    private static final int FLASH_TICKS = 12;

    private static long ticks;
    @Nullable
    private static Active self;
    private static long flashStart = -1;

    private StargateWalkOutClient() {
    }

    public static void onPacket(StargateWalkOutToClient p) {
        Minecraft mc = Minecraft.getInstance();
        // A few spare ticks: the entity's position update can reach us a moment after this packet.
        // A departure starts where the player already is, so there is no slide to hide.
        Active active = new Active(p.entityId, p.departing, p.plane, p.start, ticks + p.ticks + 10, p.speed,
                p.hideFromBehind, new boolean[]{p.departing}, new boolean[]{false});
        ACTIVE.put(p.entityId, active);
        Entity known = mc.level == null ? null : mc.level.getEntity(p.entityId);
        if (!p.departing && known != null && known.position().distanceToSqr(p.start) > ARRIVED_SQR) {
            snapToStart(active, known);
        }
        LocalPlayer player = mc.player;
        if (player != null && player.getId() == p.entityId) {
            self = active;
            // The travel animation ends in its own white flash; don't stack a second one on it.
            boolean animation = StargateWormholeEffect.isPlaying || StargateWormholeEffect.isPlayingWhite;
            if (!p.departing && JSGConfig.Stargate.walkOutArrivalFlash.get() && !animation) {
                flashStart = ticks;
            }
        }
    }

    /** Skip Minecraft's slide: put the traveller straight onto the arrival spot. */
    private static void snapToStart(Active a, Entity entity) {
        if (entity == Minecraft.getInstance().player) {
            return;
        }
        Vec3 s = a.start;
        entity.moveTo(s.x, s.y, s.z, entity.getYRot(), entity.getXRot());
        entity.lerpTo(s.x, s.y, s.z, entity.getYRot(), entity.getXRot(), 0);
    }

    /** Should this traveller be left out of this frame? */
    static boolean shouldHide(Entity entity, Vec3 cam) {
        Active a = ACTIVE.get(entity.getId());
        if (a == null) {
            return false;
        }
        if (a.departing) {
            // Gone as soon as the whole body is through on this player's screen, whatever the server says.
            if (!a.gone[0]) {
                AABB box = entity.getBoundingBox();
                if (a.plane.signedDistance(box.getCenter()) + a.plane.halfDepth(box) <= -GONE_DEPTH) {
                    a.gone[0] = true;
                }
            }
            if (a.gone[0]) {
                return true;
            }
        }
        if (!a.arrived[0]) {
            if (entity.position().distanceToSqr(a.start) > ARRIVED_SQR) {
                snapToStart(a, entity);
                return true; // still sliding over from where it was before the trip
            }
            a.arrived[0] = true;
        }
        if (!a.hide) {
            return false;
        }
        // From the front, the cut-off surface (renderCutOff) hides whatever is still inside the horizon.
        if (a.plane.signedDistance(cam) >= 0) {
            return false;
        }
        // From behind the gate: hidden while any part of it is still inside.
        return a.plane.depthInside(entity.getBoundingBox()) > 0;
    }

    @SubscribeEvent
    public static void onRenderLiving(RenderLivingEvent.Pre<?, ?> event) {
        if (!ACTIVE.isEmpty() && shouldHide(event.getEntity(), Minecraft.getInstance().gameRenderer.getMainCamera().getPosition())) {
            event.setCanceled(true);
        }
    }

    /**
     * The horizon is drawn before players (players use a translucent pass that is drawn last), so it does not
     * cover a player standing inside it. Before any entity is drawn, write an invisible depth-only rectangle just
     * behind each horizon someone is coming out of: anything behind it then fails the depth test, as if the
     * horizon were a solid wall, for every kind of entity.
     */
    @SubscribeEvent
    public static void onRenderStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_CUTOUT_BLOCKS || ACTIVE.isEmpty()) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        boolean show = JSGConfig.Debug.walkOutShowCutOff.get();
        Vec3 cam = event.getCamera().getPosition();
        List<EventHorizonPlane> planes = new ArrayList<>();
        for (Map.Entry<Integer, Active> e : ACTIVE.entrySet()) {
            EventHorizonPlane plane = e.getValue().plane;
            Entity entity = mc.level.getEntity(e.getKey());
            if (!show && entity != null && plane.depthInside(entity.getBoundingBox()) <= 0) {
                continue; // already out
            }
            if (plane.signedDistance(cam) <= CUT_OFF_DEPTH) {
                continue; // seen from behind: onRenderLiving hides them instead
            }
            if (!planes.contains(plane)) {
                planes.add(plane);
            }
        }
        if (planes.isEmpty()) {
            return;
        }

        Matrix4fStack modelView = RenderSystem.getModelViewStack();
        modelView.pushMatrix();
        modelView.identity();
        modelView.mul(event.getModelViewMatrix());
        RenderSystem.applyModelViewMatrix();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (show) {
            // Debug: see-through red, to check where it is.
            RenderSystem.setShaderColor(1f, 0.1f, 0.1f, 0.35f);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
        } else {
            RenderSystem.colorMask(false, false, false, false);
            RenderSystem.disableBlend();
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(GL11.GL_LEQUAL);
        RenderSystem.disableCull();

        var buffer = Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION);
        for (EventHorizonPlane p : planes) {
            Vec3 c = p.point().subtract(p.normal().scale(CUT_OFF_DEPTH)).subtract(cam);
            Vec3 s = p.side().scale(p.halfWidth());
            float y0 = (float) (p.bottom() - cam.y);
            float y1 = (float) (p.top() - cam.y);
            buffer.addVertex((float) (c.x - s.x), y0, (float) (c.z - s.z));
            buffer.addVertex((float) (c.x + s.x), y0, (float) (c.z + s.z));
            buffer.addVertex((float) (c.x + s.x), y1, (float) (c.z + s.z));
            buffer.addVertex((float) (c.x - s.x), y1, (float) (c.z - s.z));
        }
        BufferUploader.drawWithShader(buffer.buildOrThrow());

        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        modelView.popMatrix();
        RenderSystem.applyModelViewMatrix();
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        ticks++;
        ACTIVE.values().removeIf(a -> a.expires < ticks);

        LocalPlayer player = Minecraft.getInstance().player;
        if (self == null || player == null) {
            return;
        }
        AABB box = player.getBoundingBox();
        boolean done = self.departing
                ? self.plane.signedDistance(box.getCenter()) + self.plane.halfDepth(box) <= -DEPART_DEPTH
                : self.plane.clear(box);
        if (self.expires < ticks || player.isSpectator() || done) {
            self = null;
            return;
        }
        // Out of the horizon on arrival, into it on departure.
        Vec3 normal = self.departing ? self.plane.normal().reverse() : self.plane.normal();
        Vec3 motion = player.getDeltaMovement();
        double outward = motion.dot(normal);
        if (outward < self.speed) {
            player.setDeltaMovement(motion.add(normal.scale(self.speed - outward)));
        }
    }

    @SubscribeEvent
    public static void onLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ACTIVE.clear();
        self = null;
        flashStart = -1;
    }

    /** Registered as a GUI layer in {@code ClientModListener}. */
    public static void renderFlash(GuiGraphics graphics, DeltaTracker delta) {
        if (flashStart < 0) {
            return;
        }
        float t = (ticks - flashStart + delta.getGameTimeDeltaPartialTick(false)) / FLASH_TICKS;
        if (t >= 1f) {
            flashStart = -1;
            return;
        }
        float alpha = 0.9f * (1f - t) * (1f - t);
        int a = Mth.clamp((int) (alpha * 255f), 0, 255);
        // Pale blue-white, like stepping out of the puddle.
        graphics.fill(0, 0, graphics.guiWidth(), graphics.guiHeight(), (a << 24) | 0xE4EEFF);
    }
}
