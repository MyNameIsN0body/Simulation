package com.petproject.simulation;

import com.petproject.simulation.simulation.Simulation;

public class Main {

    public static void main(String[] args) {
        Simulation simulation = new Simulation(20, 20);
        simulation.startGame();
    }
}