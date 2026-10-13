package dev.tauri.jsg.common.stargate.teleportation.traveler;

import dev.tauri.jsg.api.stargate.Stargate;
import dev.tauri.jsg.api.stargate.traveler.IStargateTraveler;
import dev.tauri.jsg.common.registry.tags.JSGEntitiesTags;
import dev.tauri.jsg.common.stargate.manager.StargateEventHorizonManager;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntityTraveler<E extends Entity> extends AbstractTraveler<E> {
    protected E entity;
    protected final Vec3 originalPos;
    protected final Vec3 originalMotion;
    protected final Vec3 destinationPos;
    protected final Vec3 destinationMotion;
    protected final float destinationYaw;
    protected final List<IStargateTraveler<?>> passengers = new ArrayList<>();
    protected Entity vehicle = null;

    public EntityTraveler(E entity, Vec3 destinationPos, Vec3 originalMotion, Vec3 destinationMotion, float destinationYaw, Stargate<?> sourceGate, Stargate<?> receivingGate, boolean isStatic) {
        super(sourceGate, receivingGate, isStatic);
        this.entity = entity;
        this.destinationMotion = destinationMotion;
        this.destinationPos = destinationPos;
        this.originalPos = entity.position();
        this.originalMotion = originalMotion;
        this.destinationYaw = destinationYaw;
    }

    @Override
    public boolean hasPassengers() {
        return !this.passengers.isEmpty();
    }

    @Override
    public List<IStargateTraveler<?>> getPassengers() {
        return this.passengers;
    }

    @Override
    public void loadPassengers() {
        if (hasPassengers()) return;
        if (!get().isVehicle()) return;
        get().getPassengers().forEach(passengerEntity -> {
            var passenger = getTransmitter().getEventHorizonManager().getTraveler(passengerEntity, this);
            passenger.loadPassengers();
            this.passengers.add(passenger);
        });
    }

    @Override
    public void insertPassengers(List<IStargateTraveler<?>> passengers) {
        this.passengers.clear();
        this.passengers.addAll(passengers);
    }

    @Override
    public void setVehicle(Entity vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public boolean canBeSendToTarget() {
        return !get().getType().is(JSGEntitiesTags.STARGATE_UNTRANSPORTABLE_ENTITIES);
    }

    @Override
    public E get() {
        return entity;
    }

    @Override
    public Vec3 getOriginalMotion() {
        return originalMotion;
    }

    @Override
    public Vec3 getDestinationMotion() {
        return destinationMotion;
    }

    @Override
    public void setMotion(Vec3 newMotion) {
        get().setDeltaMovement(newMotion);
    }

    @Override
    public Vec3 getOriginalPos() {
        return originalPos;
    }

    @Override
    public Vec3 getDestinationPos() {
        return destinationPos;
    }

    @Override
    public void setPos(Vec3 newPos) {
        get().setPos(newPos);
        get().teleportTo(newPos.x, newPos.y, newPos.z);
    }

    @Override
    public void setYaw(float yaw) {
        get().setYRot(yaw);
    }

    @Override
    public void sendChangeDimension(ServerLevel targetLevel, Consumer<IStargateTraveler<?>> newTraveler) {
        loadPassengers();
        var newPassengers = new ArrayList<IStargateTraveler<?>>();
        getPassengers().forEach(passenger -> {
            if (passenger instanceof EntityTraveler<?> entityPassenger)
                entityPassenger.get().stopRiding();
        });
        get().changeDimension(targetLevel, sourceGate.getEventHorizonManager().getTeleporter(this, (traveler) -> {
                    getPassengers().forEach(passenger -> passenger.sendChangeDimension(targetLevel, newPassengers::add));
                    traveler.insertPassengers(newPassengers);
                    traveler.getPassengers().forEach(passenger -> {
                        if (traveler instanceof EntityTraveler<?> entityTraveler)
                            passenger.setVehicle(entityTraveler.get());
                    });
                },
                (traveler) -> {
                }
        ).createTransition(get(), targetLevel));
    }

    @Override
    public void sendSameDimension() {
        loadPassengers();
        getPassengers().forEach(passenger -> {
            if (passenger instanceof EntityTraveler<?> entityPassenger)
                entityPassenger.get().stopRiding();
            passenger.setVehicle(this.get());
        });
        super.sendSameDimension();
        getPassengers().forEach(IStargateTraveler::sendSameDimension);
    }

    @Override
    public void place() {
        super.place();
        if (vehicle != null)
            get().startRiding(vehicle, true);
    }

    @Override
    public float getDestinationYaw() {
        return destinationYaw;
    }

    @Override
    public void killWrongTravel() {
        ((StargateEventHorizonManager) getTransmitter().getEventHorizonManager()).remove(this);
        get().setDeltaMovement(0, 0, 0);
        get().kill();
        passengers.forEach(IStargateTraveler::killWrongTravel);
    }

    @Override
    public void killIris() {
        get().setDeltaMovement(0, 0, 0);
        get().kill();
        passengers.forEach(IStargateTraveler::killIris);
    }

    @Override
    public void killKawoosh() {
        get().setDeltaMovement(0, 0, 0);
        get().kill();
        passengers.forEach(IStargateTraveler::killKawoosh);
    }

    @Override
    public void killUnstable() {
        get().setDeltaMovement(0, 0, 0);
        get().kill();
        passengers.forEach(IStargateTraveler::killUnstable);
    }

    @Override
    public int hashCode() {
        return get().getId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof EntityTraveler<?>)) return false;
        return o.hashCode() == hashCode();
    }
}
