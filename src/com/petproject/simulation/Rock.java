package com.petproject.simulation;

public class Rock extends Entity{
    public Rock(Coordinates coordinates, Coordinates coordinates1) {
        super(coordinates);
        this.coordinates = coordinates1;
    }

    Coordinates coordinates;
    private static final String ROCK_SPRITE = "\uD83E\uDEA8";//

    public Rock(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String getSprite() {
        return ROCK_SPRITE;
    }
}
