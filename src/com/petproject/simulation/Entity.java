package com.petproject.simulation;

public abstract class Entity {
    Coordinates coordinates;
    String sprite;

    public Entity(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
    public abstract String getSprite();

}
