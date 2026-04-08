package com.petproject.simulation.simulation;

public class GameStats {
    private final int turn;
    private final int predators;
    private final int herbivore;
    private final int grass;

    public int getPredators() {
        return predators;
    }

    public int getHerbivore() {
        return herbivore;
    }

    public int getGrass() {
        return grass;
    }
    public int getTurn() {
        return turn;
    }

    public GameStats(int turn, int predators, int herbivore, int grass) {
        this.turn = turn;
        this.predators = predators;
        this.herbivore = herbivore;
        this.grass = grass;
    }
}
