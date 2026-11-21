package dev.tauri.jsg.common.packet.packets.stargate;

import dev.tauri.jsg.client.renderer.StargateWalkOutClient;
import dev.tauri.jsg.common.stargate.teleportation.walkout.EventHorizonPlane;
import dev.tauri.jsgcore.common.packet.NetworkDirection;
import dev.tauri.jsgcore.common.packet.PacketContext;
import dev.tauri.jsgcore.common.packet.packets.JSGPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

/**
 * Server to nearby players: "this entity has just arrived inside that event horizon" or, with {@code departing},
 * "this player is about to walk into that horizon". The traveler's own game pushes them out (or in) and flashes
 * the screen; everyone's game hides the part of the traveler that is inside the horizon.
 */
public class StargateWalkOutToClient extends JSGPacket {
    public int entityId;
    public EventHorizonPlane plane;
    public Vec3 start;
    public int ticks;
    public float speed;
    public boolean hideFromBehind;
    public boolean departing;

    public StargateWalkOutToClient() {
    }

    public StargateWalkOutToClient(int entityId, EventHorizonPlane plane, Vec3 start, int ticks, float speed, boolean hideFromBehind, boolean departing) {
        this.entityId = entityId;
        this.plane = plane;
        this.start = start;
        this.ticks = ticks;
        this.speed = speed;
        this.hideFromBehind = hideFromBehind;
        this.departing = departing;
    }

    public StargateWalkOutToClient(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeVarInt(entityId);
        plane.toBytes(buf);
        buf.writeVec3(start);
        buf.writeVarInt(ticks);
        buf.writeFloat(speed);
        buf.writeBoolean(hideFromBehind);
        buf.writeBoolean(departing);
    }

    @Override
    public void fromBytes(FriendlyByteBuf buf) {
        entityId = buf.readVarInt();
        plane = EventHorizonPlane.fromBytes(buf);
        start = buf.readVec3();
        ticks = buf.readVarInt();
        speed = buf.readFloat();
        hideFromBehind = buf.readBoolean();
        departing = buf.readBoolean();
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handle(PacketContext ctx) {
        if (ctx.getDirection() != NetworkDirection.PLAY_TO_CLIENT) return;
        ctx.enqueueWork(() -> {
            ctx.setPacketHandled(true);
            StargateWalkOutClient.handlePacket(this);
        });
    }
}
