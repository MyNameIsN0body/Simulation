package com.petproject.simulation.entity;

public abstract class Entity {
//    private  Coordinates coordinates;
    protected final EntityType type;

    public EntityType getType() {
        return type;
    }

    public Entity(EntityType type) {
//        this.coordinates = coordinates;
        this.type = type;
    }

//    public Coordinates getCoordinates() {
//        return coordinates;
//    }
//
//    public void setCoordinates(Coordinates coordinates) {
//        this.coordinates = coordinates;
//    }

}
