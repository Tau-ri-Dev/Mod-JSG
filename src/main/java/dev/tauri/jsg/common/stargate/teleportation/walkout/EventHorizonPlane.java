package dev.tauri.jsg.common.stargate.teleportation.walkout;

import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.common.stargate.manager.StargateEventHorizonManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

/**
 * The flat surface of an event horizon: a point on it and the direction travellers come out (the front of the gate),
 * plus the size of the opening (half its width, its bottom and top heights).
 * Used by the server (placing and pushing arrivals) and the client (hiding travellers, pushing the local player).
 */
public record EventHorizonPlane(Vec3 point, Vec3 normal, double halfWidth, double bottom, double top) {

    /** Travellers are helped this far clear of the horizon, so they don't stop with their back in the puddle. */
    public static final double CLEARANCE = 0.3;

    /**
     * The normal is the gate's local +Z in world space, the same transform JSG uses to send arrivals' motion
     * out of the gate. (The arrival <i>position</i> from {@code place()} is not a reliable guide: it is worked
     * out from a block corner, so depending on facing, arrivals land up to half a block behind the horizon or
     * 1.5 blocks in front of it.)
     *
     * @return null for gates the walk-out leaves alone (gates lying in the floor or ceiling, other managers)
     */
    @Nullable
    public static EventHorizonPlane of(Stargate<?> gate) {
        if (!(gate.getEventHorizonManager() instanceof StargateEventHorizonManager manager)) {
            return null;
        }
        // getLocalTeleportBox() undoes the block offset, so add it back to get world coordinates.
        AABB box = manager.getLocalTeleportBox().offset(gate.blockPosition());
        Vec3 normal = gate.rotated(new Vec3(0, 0, 1)).subtract(gate.rotated(Vec3.ZERO));
        if (normal.lengthSqr() < 1.0E-6) {
            return null;
        }
        normal = normal.normalize();
        if (Math.abs(normal.y) > 0.5) {
            return null;
        }
        Vec3 side = normal.cross(new Vec3(0, 1, 0)).normalize();
        double halfWidth = (box.getXsize() * Math.abs(side.x) + box.getZsize() * Math.abs(side.z)) / 2.0;
        // Reach a little below the teleport box so feet on the gate's floor are covered.
        return new EventHorizonPlane(box.getCenter(), normal, halfWidth, box.minY - 0.5, box.maxY);
    }

    /** Horizontal direction across the opening. */
    public Vec3 side() {
        return normal.cross(new Vec3(0, 1, 0)).normalize();
    }

    /** Distance in front of the horizon (negative = behind it, inside the gate). */
    public double signedDistance(Vec3 pos) {
        return pos.subtract(point).dot(normal);
    }

    /** Half the box's thickness measured along the normal. */
    public double halfDepth(AABB box) {
        return (box.getXsize() * Math.abs(normal.x) + box.getYsize() * Math.abs(normal.y) + box.getZsize() * Math.abs(normal.z)) / 2.0;
    }

    /** How far the back of the box is still behind the horizon. 0 or less: fully out. */
    public double depthInside(AABB box) {
        return halfDepth(box) - signedDistance(box.getCenter());
    }

    /** How far the front of the box has gone through the horizon (positive once it is through). */
    public double depthThrough(AABB box) {
        return -(signedDistance(box.getCenter()) + halfDepth(box));
    }

    /** True once the traveller has stepped far enough out that the walk-out is done. */
    public boolean clear(AABB box) {
        return depthInside(box) <= -CLEARANCE;
    }

    public void write(FriendlyByteBuf buf) {
        writeVec(buf, point);
        writeVec(buf, normal);
        buf.writeDouble(halfWidth);
        buf.writeDouble(bottom);
        buf.writeDouble(top);
    }

    public static EventHorizonPlane read(FriendlyByteBuf buf) {
        return new EventHorizonPlane(readVec(buf), readVec(buf), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void writeVec(FriendlyByteBuf buf, Vec3 v) {
        buf.writeDouble(v.x);
        buf.writeDouble(v.y);
        buf.writeDouble(v.z);
    }

    public static Vec3 readVec(FriendlyByteBuf buf) {
        return new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
}
