package com.petproject.simulation;

public class Herbivore extends Creature {

    private static final String HERBIVORE_SPRITE = "🐑";

    public Herbivore(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String getSprite() {
        return HERBIVORE_SPRITE;
    }

}
