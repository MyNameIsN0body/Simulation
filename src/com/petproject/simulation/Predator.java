package com.petproject.simulation;

public class Predator extends Creature {

    private static final String PREDATOR_SPRITE = "🐅";

    public Predator(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String getSprite() {
        return PREDATOR_SPRITE;
    }

}
