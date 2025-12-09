package com.petproject.simulation.render;

public final class ConsoleSprite {
    private ConsoleSprite() {}

    private static final String GRASS = "🌱";
    private static final String ROCK = "🪨";
    private static final String TREE = "🌳";

    private static final String HERBIVORE = "🐑";
    private static final String PREDATOR = "🦖";

    private static final String EMPTY = "⬜";

    public static String getSprite (String entityType) {
        return switch (entityType.toLowerCase()){
            case "grass" -> GRASS;
            case "rock" -> ROCK;
            case "tree" -> TREE;
            case "herbivore" -> HERBIVORE;
            case "predator" -> PREDATOR;
            case "empty" -> EMPTY;
            default -> throw new IllegalStateException("Unexpected value: " + entityType.toLowerCase());
        };
    }
}
