package com.petproject.simulation.entity;

public abstract class Entity {
    protected final EntitySprite type;

    public EntitySprite getType() {
        return type;
    }

    public Entity(EntitySprite type) {
        this.type = type;
    }
}
