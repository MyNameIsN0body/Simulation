package com.petproject.simulation;

import com.petproject.simulation.simulation.*;
import com.petproject.simulation.world.WorldMap;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        WorldMap worldMap = new WorldMap(20, 20);
        Simulation simulation = new Simulation(worldMap);
        simulation.initialize();

        Scanner scanner = new Scanner(System.in);

        GameMessenger.displayIntro();
        GameMessenger.displaySelectMode();

        SimulationController controller = new SimulationController(simulation);
        SimulationMode simulationMode = new AutoMode(controller, scanner);
        simulationMode.run();
    }
}