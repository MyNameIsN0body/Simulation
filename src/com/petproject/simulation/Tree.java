package com.petproject.simulation;

public class Tree extends Entity {

    private static final String TREE_SPRITE = "🌳";

    public Tree(Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public String getSprite() {
        return TREE_SPRITE;
    }
}
