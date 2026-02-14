package com.petproject.simulation.entity;

public enum EntityType {
    HERBIVORE,
    PREDATOR,
    GRASS,
    ROCK,
    TREE,
    EMPTY;
    private String sprite;

static {
    HERBIVORE.sprite = "🦌";//🐏"; //🐑";
    PREDATOR.sprite = "\u001B[40m🦖\u001B[0m";
    GRASS.sprite = "🌱";
    ROCK.sprite = "⛰️";
    TREE.sprite = "🌲";//🌳";
    EMPTY.sprite = "🔲";
}

    public String getSprite() {
        return sprite;
    }
}
