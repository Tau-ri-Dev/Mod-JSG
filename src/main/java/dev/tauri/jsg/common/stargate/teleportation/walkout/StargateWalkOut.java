package dev.tauri.jsg.common.stargate.teleportation.walkout;

import dev.tauri.jsg.JSG;
import dev.tauri.jsg.api.config.JSGConfig;
import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.api.stargate.StargateWithIris;
import dev.tauri.jsg.api.stargate.traveler.IStargateTraveler;
import dev.tauri.jsg.common.effect.StargateWormholeHandler;
import dev.tauri.jsg.common.packet.JSGPacketHandler;
import dev.tauri.jsg.common.packet.packets.stargate.StargateWalkOutToClient;
import dev.tauri.jsgcore.common.packet.TargetPoint;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.VehicleEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.WeakHashMap;

/**
 * SERVER SIDE. Travellers walk into the event horizon and step back out of the one at the other end, instead of
 * vanishing just in front of the first gate and appearing a block in front of the second.
 * <ul>
 *     <li>{@link #onSend}: remembers where a traveller went in (which side of the gate's centre line).</li>
 *     <li>{@link #onReceive}: right after {@code place()}, moves the arrival back so it starts inside the
 *     destination horizon on the side it went in, then keeps it moving out until it is clear.</li>
 *     <li>{@link #startWalkIn}: during the travel animation the player stays visible and walks on into the
 *     source horizon; they are hidden once their whole body is through.</li>
 * </ul>
 * Clients get a {@link StargateWalkOutToClient} so they can hide the part of a traveller that is inside the
 * horizon (see {@code StargateWalkOutClient}).
 */
@EventBusSubscriber(modid = JSG.MOD_ID)
public final class StargateWalkOut {
    /** Small gap so "fully inside" never rounds to touching the horizon. */
    private static final double MARGIN = 0.02;
    /** Step used when backing off from a wall behind the gate. */
    private static final double STEP = 0.05;
    /** Players this far away are told about the traveller (covers normal render distance). */
    private static final double NOTIFY_RADIUS = 128.0;
    /** How far through the horizon the front of a departing body must be before it counts as through. */
    private static final double DEPART_DEPTH = 0.05;
    /**
     * Ticks between a departing player being through on the server and hiding them. Other players see them a
     * little behind the server; their own game hides the traveller as soon as it is through on their screen.
     */
    private static final int HIDE_GRACE = 10;

    /** Where a traveller touched the source horizon, kept until they arrive (by UUID: survives dimension changes). */
    private record Entry(Vec3 pos, EventHorizonPlane plane, long time) {
    }

    private record Emerging(EventHorizonPlane plane, double speed, int[] ticksLeft) {
    }

    private record Departing(ServerPlayer player, EventHorizonPlane plane, int[] ticksLeft, int[] grace) {
    }

    private static final Map<UUID, Entry> ENTRIES = new HashMap<>();
    private static final Map<Entity, Emerging> EMERGING = new WeakHashMap<>();
    private static final Map<UUID, Departing> DEPARTING = new HashMap<>();
    private static long serverTicks;

    private StargateWalkOut() {
    }

    private static boolean enabled() {
        return JSGConfig.Stargate.walkOutEnabled.get();
    }

    // --------------------------------------------------
    // GOING IN

    /** Called from {@code StargateEventHorizonManager.send()} just before the traveller is sent. */
    public static void onSend(IStargateTraveler<?> traveler, Stargate<?> sourceGate) {
        if (!enabled() || traveler.isStatic() || !(traveler.get() instanceof Entity entity)) {
            return;
        }
        try {
            EventHorizonPlane plane = EventHorizonPlane.of(sourceGate);
            if (plane != null) {
                ENTRIES.put(entity.getUUID(), new Entry(entity.position(), plane, serverTicks));
            }
        } catch (RuntimeException e) {
            JSG.logger.warn("Walk-out could not record where a traveller went in: ", e);
        }
    }

    /**
     * Called by {@link StargateWormholeHandler#handle} when a player's travel animation starts, instead of making
     * them invisible straight away (they are still about 0.2 blocks short of the horizon then).
     *
     * @return true if the walk-in takes over: the player stays visible and is hidden once through
     */
    public static boolean startWalkIn(ServerPlayer player) {
        if (!enabled() || !JSGConfig.Stargate.walkOutWalkIn.get() || !JSGConfig.Stargate.walkOutPlayers.get()
                || player.isSpectator()) {
            return false;
        }
        Entry entry = ENTRIES.get(player.getUUID());
        if (entry == null) {
            return false;
        }
        EventHorizonPlane plane = entry.plane();
        if (plane.depthThrough(player.getBoundingBox()) >= DEPART_DEPTH) {
            return false; // already through
        }
        int ticks = JSGConfig.Stargate.walkOutMaxTicks.get();
        DEPARTING.put(player.getUUID(), new Departing(player, plane, new int[]{ticks}, new int[]{-1}));
        Vec3 pos = player.position();
        notifyNear(player.serverLevel(), pos, new StargateWalkOutToClient(player.getId(), plane, pos, ticks,
                (float) (double) JSGConfig.Stargate.walkOutExitSpeed.get(), JSGConfig.Stargate.walkOutHideFromBehind.get(), true));
        return true;
    }

    // --------------------------------------------------
    // COMING OUT

    /**
     * Called from {@code StargateEventHorizonManager.receive()} right after {@code place()} and the receive event,
     * before the iris check and before the traveller is added to the recently-through list (which protects it
     * from wrong-side killing while it is still inside the horizon).
     */
    public static void onReceive(IStargateTraveler<?> traveler, Stargate<?> gate, boolean redirected) {
        if (!enabled() || redirected || traveler.isStatic() || !(traveler.get() instanceof Entity entity)) {
            return;
        }
        try {
            receive(traveler, gate, entity);
        } catch (RuntimeException e) {
            // Never break a teleport over a visual effect.
            JSG.logger.warn("Walk-out failed for an arrival; the normal placement was kept: ", e);
        }
    }

    private static void receive(IStargateTraveler<?> traveler, Stargate<?> gate, Entity entity) {
        if (!(entity.level() instanceof ServerLevel level) || !entity.isAlive() || gate.getStargateLevel() != level) {
            return;
        }
        Entry entry = ENTRIES.remove(entity.getUUID());
        // A closed iris kills the traveller straight after this; nothing to walk out of.
        if (gate instanceof StargateWithIris<?> withIris && withIris.getIrisManager().isIrisClosed()) {
            return;
        }
        if (!eligible(entity, traveler)) {
            return;
        }
        EventHorizonPlane plane = EventHorizonPlane.of(gate);
        if (plane == null) {
            return;
        }
        Vec3 placed = entity.position();

        // Come out on the same side you went in. The sideways placement from place() is off by up to a block
        // depending on which way the gates face (the same block-corner rounding as the front/back placement).
        if (entry != null && JSGConfig.Stargate.walkOutKeepSide.get()) {
            AABB box = entity.getBoundingBox();
            Vec3 side = plane.side();
            double halfWidth = (box.getXsize() * Math.abs(side.x) + box.getZsize() * Math.abs(side.z)) / 2.0;
            double limit = Math.max(0.0, plane.halfWidth() - halfWidth - 0.05);
            double wanted = Math.max(-limit, Math.min(limit, entry.pos().subtract(entry.plane().point()).dot(entry.plane().side().reverse())));
            double now = placed.subtract(plane.point()).dot(side);
            entity.setPos(entity.position().add(side.scale(wanted - now)));
        }

        double inside = JSGConfig.Stargate.walkOutStartInside.get();
        Vec3 start = findStart(entity, plane, inside);
        if (start == null && !entity.position().equals(placed) && !level.noCollision(entity, entity.getBoundingBox())) {
            // The corrected side doesn't fit at any depth: keep the original side.
            entity.setPos(placed);
            start = findStart(entity, plane, inside);
        }
        if (start == null) {
            start = entity.position();
            if (plane.depthInside(entity.getBoundingBox()) <= 0) {
                if (!start.equals(placed)) {
                    moveTo(entity, start); // still apply the sideways fix
                }
                return;
            }
        }
        if (!start.equals(placed)) {
            moveTo(entity, start);
        }

        double speed = JSGConfig.Stargate.walkOutExitSpeed.get();
        int ticks = JSGConfig.Stargate.walkOutMaxTicks.get();
        EMERGING.put(entity, new Emerging(plane, speed, new int[]{ticks}));
        notifyNear(level, start, new StargateWalkOutToClient(entity.getId(), plane, start, ticks, (float) speed,
                JSGConfig.Stargate.walkOutHideFromBehind.get(), false));
    }

    private static boolean eligible(Entity entity, IStargateTraveler<?> traveler) {
        // Vehicles and anything riding or carrying something are left to the normal placement.
        if (entity.isPassenger() || entity.isVehicle() || traveler.hasPassengers() || entity instanceof VehicleEntity) {
            return false;
        }
        if (entity instanceof Player) {
            return JSGConfig.Stargate.walkOutPlayers.get();
        }
        if (entity instanceof LivingEntity) {
            return JSGConfig.Stargate.walkOutMobs.get();
        }
        return JSGConfig.Stargate.walkOutOtherEntities.get();
    }

    /**
     * Where to start the traveller: as deep inside the horizon as configured, backing off outwards until the body
     * fits (a wall right behind the gate). Only the distance along the gate's normal changes.
     *
     * @return null to keep the current spot (already right, or nothing else fits)
     */
    @Nullable
    static Vec3 findStart(Entity entity, EventHorizonPlane plane, double inside) {
        AABB box = entity.getBoundingBox();
        double half = plane.halfDepth(box);
        double current = plane.signedDistance(box.getCenter());
        double wanted = half - inside * (2.0 * half + MARGIN);
        // Never go further out than the current spot or "just out of the horizon", whichever is further.
        double limit = Math.max(current, half + MARGIN);
        for (double target = wanted; target <= limit + 1.0E-6; target += STEP) {
            Vec3 shift = plane.normal().scale(target - current);
            if (entity.level().noCollision(entity, box.move(shift))) {
                return shift.lengthSqr() < 1.0E-8 ? null : entity.position().add(shift);
            }
        }
        return null;
    }

    private static void moveTo(Entity entity, Vec3 pos) {
        if (entity instanceof ServerPlayer player) {
            player.connection.teleport(pos.x, pos.y, pos.z, player.getYRot(), player.getXRot());
        } else {
            entity.teleportTo(pos.x, pos.y, pos.z);
        }
        entity.resetFallDistance();
    }

    private static void notifyNear(ServerLevel level, Vec3 pos, StargateWalkOutToClient packet) {
        JSGPacketHandler.sendToClient(packet, new TargetPoint(pos.x, pos.y, pos.z, NOTIFY_RADIUS, level.dimension()));
    }

    /** Raise the outward part of the entity's motion to at least {@code speed}. */
    static void push(Entity entity, Vec3 normal, double speed) {
        Vec3 motion = entity.getDeltaMovement();
        double outward = motion.dot(normal);
        if (outward < speed) {
            entity.setDeltaMovement(motion.add(normal.scale(speed - outward)));
            entity.hurtMarked = true; // send the new motion to watching players
        }
    }

    // --------------------------------------------------
    // TICKING

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        serverTicks++;
        // Forget entries for travellers that were never sent (stepped back out); a player's trip takes ~5 s.
        if (!ENTRIES.isEmpty() && serverTicks % 100 == 0) {
            ENTRIES.values().removeIf(e -> serverTicks - e.time() > 600);
        }
        if (!EMERGING.isEmpty()) {
            Iterator<Map.Entry<Entity, Emerging>> it = EMERGING.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Entity, Emerging> e = it.next();
                Entity entity = e.getKey();
                Emerging em = e.getValue();
                if (entity.isRemoved() || !entity.isAlive() || --em.ticksLeft()[0] < 0 || em.plane().clear(entity.getBoundingBox())) {
                    it.remove();
                } else if (!(entity instanceof Player)) {
                    // Players move themselves; their own game pushes them out.
                    push(entity, em.plane().normal(), em.speed());
                }
            }
        }
        if (!DEPARTING.isEmpty()) {
            Iterator<Map.Entry<UUID, Departing>> it = DEPARTING.entrySet().iterator();
            while (it.hasNext()) {
                Departing d = it.next().getValue();
                ServerPlayer player = d.player();
                if (player.isRemoved() || !StargateWormholeHandler.WORMHOLE_ANIMATION_END_MAP.containsKey(player.getUUID())) {
                    it.remove(); // trip finished or cancelled; release() has made them visible again
                    continue;
                }
                if (d.plane().depthThrough(player.getBoundingBox()) >= DEPART_DEPTH && d.grace()[0] < 0) {
                    d.grace()[0] = HIDE_GRACE;
                }
                if ((d.grace()[0] >= 0 && --d.grace()[0] < 0) || --d.ticksLeft()[0] < 0) {
                    player.setInvisible(true); // as the travel animation normally has it
                    it.remove();
                }
            }
        }
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event) {
        ENTRIES.clear();
        EMERGING.clear();
        DEPARTING.clear();
    }

    /** Is this entity still being helped out of a horizon? */
    public static boolean isEmerging(Entity entity) {
        return EMERGING.containsKey(entity);
    }
}
