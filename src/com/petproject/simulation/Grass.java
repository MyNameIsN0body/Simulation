package com.petproject.simulation;

public class Grass extends Entity{

    private static final String GRASS_SPRITE = "🌱";

    public Grass(Coordinates coordinates) {
        super(coordinates);
    }
    @Override
    public String getSprite() {
        return GRASS_SPRITE;
    }
}
