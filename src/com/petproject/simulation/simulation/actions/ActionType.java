package com.petproject.simulation.simulation.actions;

public enum ActionType {
    LOVE,
    DEATH,
    HERBIVORE_EATS,
    PREDATOR_EATS,
    RESPAWN_GRASS;

    private String sprite;

    static {
        LOVE.sprite = "💞";
        DEATH.sprite = "🪦";
        HERBIVORE_EATS.sprite = "➕";
        PREDATOR_EATS.sprite = "🩸";
        RESPAWN_GRASS.sprite = "💫";
    }

    public String getSprite() {
        return sprite;
    }
}
