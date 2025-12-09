package com.petproject.simulation.entity;

public abstract class Entity {
    private  Coordinates coordinates;

    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getTypeName() {
        return this.getClass().getSimpleName().toLowerCase();
    }


}
