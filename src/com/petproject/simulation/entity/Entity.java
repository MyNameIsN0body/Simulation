package com.petproject.simulation.entity;

public abstract class Entity {
    protected final EntityType type;

    public EntityType getType() {
        return type;
    }

    public Entity(EntityType type) {
        this.type = type;
    }
}
