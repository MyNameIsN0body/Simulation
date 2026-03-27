package com.petproject.simulation.entity;

public enum EntitySprite {
    HERBIVORE("🦌"),
    PREDATOR("\u001B[40m🦖\u001B[0m"),
    GRASS("🌱"),
    ROCK("⛰️"),
    TREE("🌲"),
    EMPTY("🔲");

    private final String sprite;

    EntitySprite(String sprite) {
        this.sprite = sprite;
    }

    public String getSprite() {
        return sprite;
    }
}
