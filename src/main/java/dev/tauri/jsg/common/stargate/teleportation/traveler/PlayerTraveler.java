package dev.tauri.jsg.common.stargate.teleportation.traveler;

import dev.tauri.jsg.api.config.JSGConfig;
import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.api.stargate.traveler.IStargateTraveler;
import dev.tauri.jsg.common.effect.StargateWormholeHandler;
import dev.tauri.jsg.common.effect.StargateWormholeType;
import dev.tauri.jsg.common.packet.JSGPacketHandler;
import dev.tauri.jsg.common.packet.packets.stargate.StargateMotionAndRotationToClient;
import dev.tauri.jsg.common.packet.packets.stargate.StargatePlayerMotionRequestToClient;
import dev.tauri.jsg.common.stargate.teleportation.StargateTeleporter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;

import java.util.ArrayList;
import java.util.function.Consumer;

public class PlayerTraveler extends LivingTraveler<ServerPlayer> {
    public PlayerTraveler(ServerPlayer entity, Vec3 destinationPos, Vec3 originalMotion, Vec3 destinationMotion, float destinationYaw, Stargate<?> sourceGate, Stargate<?> receivingGate, boolean isStatic) {
        super(entity, destinationPos, originalMotion, destinationMotion, destinationYaw, sourceGate, receivingGate, isStatic);
    }

    @Override
    public boolean canBeKilledByIris() {
        return !JSGConfig.Stargate.allowCreative.get() || !get().isCreative();
    }

    @Override
    public void setMotion(Vec3 newMotion) {
        super.setMotion(newMotion);
        entity.hurtMarked = true;
        JSGPacketHandler.sendTo(new StargateMotionAndRotationToClient(newMotion, entity.getXRot(), getDestinationYaw()), get());
    }

    @Override
    public boolean canBeSendToTarget() {
        if (sourceGate.getStargatePos() == null || receivingGate.getStargatePos() == null || sourceGate.getStargatePos().dimension == receivingGate.getStargatePos().dimension)
            return true;
        return CommonHooks.onTravelToDimension(entity, receivingGate.getStargatePos().dimension);
    }

    @Override
    public void sendChangeDimension(ServerLevel targetLevel, Consumer<IStargateTraveler<?>> newTraveler) {
        if (isStatic()) {
            get().changeDimension(new StargateTeleporter(this, (traveler) -> {
            }, (traveler) -> {
                setMotion(getDestinationMotion());
                newTraveler.accept(traveler);
            })
                    .createTransition(get(), targetLevel));
            return;
        }

        loadPassengers();
        var newPassengers = new ArrayList<IStargateTraveler<?>>();
        getPassengers().forEach(passenger -> {
            if (passenger instanceof EntityTraveler<?> entityPassenger)
                entityPassenger.get().stopRiding();
        });
        StargateWormholeHandler.handle(get(), (afterPlace) -> get().changeDimension(new StargateTeleporter(this,
                (traveler) -> {
                    getPassengers().forEach(passenger -> passenger.sendChangeDimension(targetLevel, newPassengers::add));
                    traveler.insertPassengers(newPassengers);
                    traveler.getPassengers().forEach(passenger -> {
                        if (traveler instanceof EntityTraveler<?> entityTraveler)
                            passenger.setVehicle(entityTraveler.get());
                    });
                },
                (traveler) -> {
                    afterPlace.accept((ServerPlayer) traveler.get());
                    setMotion(getDestinationMotion());
                    newTraveler.accept(traveler);
                    if (vehicle != null && newTraveler instanceof PlayerTraveler playerTraveler)
                        playerTraveler.get().startRiding(vehicle, true);
                }
        ).createTransition(get(), targetLevel)), StargateWormholeType.fromTileEntity(receivingGate), 5);
    }

    @Override
    public void sendSameDimension() {
        if (isStatic()) {
            receivingGate.getEventHorizonManager().receive(this);
            return;
        }
        loadPassengers();
        getPassengers().forEach(passenger -> {
            if (passenger instanceof EntityTraveler<?> entityPassenger)
                entityPassenger.get().stopRiding();
            passenger.setVehicle(this.get());
        });
        StargateWormholeHandler.handle(get(), (afterPlace) -> {
            receivingGate.getEventHorizonManager().receive(this);
            afterPlace.accept(get());
        }, StargateWormholeType.fromTileEntity(receivingGate), 3);
        getPassengers().forEach(IStargateTraveler::sendSameDimension);
    }

    @Override
    public void place() {
        super.place();
    }

    @Override
    public void setPos(Vec3 newPos) {
        get().teleportTo(newPos.x, newPos.y, newPos.z);
    }

    @Override
    public boolean isPlayer() {
        return true;
    }

    @Override
    public void send() {
        JSGPacketHandler.sendTo(new StargatePlayerMotionRequestToClient(getTransmitter().blockPosition(), false), get());
    }

    @Override
    public void killWrongTravel() {
        JSGPacketHandler.sendTo(new StargatePlayerMotionRequestToClient(getTransmitter().blockPosition(), true), get());
    }

    public void confirmSend() {
        super.send();
    }

    @Override
    public boolean isSameDimension() {
        if (isStatic())
            return getReceiver().getStargatePos().dimension == get().level().dimension();
        return super.isSameDimension();
    }

    public void confirmKillWrongTravel() {
        super.killWrongTravel();
    }

    @Override
    public void killIris() {
        get().setInvulnerable(false);
        get().setInvisible(false);
        get().noPhysics = false;
        get().getAbilities().invulnerable = false;
        get().getAbilities().mayBuild = true;
        get().gameMode.getGameModeForPlayer().updatePlayerAbilities(get().getAbilities());
        get().onUpdateAbilities();
        super.killIris();
    }

    @Override
    public int hashCode() {
        return get().getUUID().hashCode();
    }
}
