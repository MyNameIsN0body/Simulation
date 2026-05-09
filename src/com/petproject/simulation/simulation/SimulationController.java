package com.petproject.simulation.simulation;

import java.util.Scanner;

public class SimulationController {

    private final Simulation simulation;
    private final Scanner scanner;

    private volatile boolean running = true;
    private volatile boolean paused = true;

    public SimulationController(Simulation simulation, Scanner scanner) {
        this.simulation = simulation;
        this.scanner = scanner;
    }

    public void run() {

        startSimulationLoop();

        GameMessenger.showAutoModeMenu();

        while (running) {

            String command = scanner.nextLine().trim();

            handleCommand(command);
        }

        System.out.println("Симуляция завершена.");
    }

    private void startSimulationLoop() {

        Thread simulationThread = new Thread(() -> {

            while (running) {

                if (!paused) {
                    makeTurn();
                }

                sleep(1000);
            }
        });

        simulationThread.setDaemon(true);
        simulationThread.start();
    }

    private void handleCommand(String command) {

        switch (command) {

            case "run":
            case "r":
                paused = false;
               GameMessenger.showInfoStart();
                break;

            case "pause":
            case "p":
                paused = true;
                GameMessenger.showInfoPause();
                break;

            case "step":
            case "s":

                if (paused) {
                    GameMessenger.showInfoStep();
                    makeTurn();
                } else {
                    System.out.println("Сначала поставьте на паузу");
                }

                break;

            case "menu":
            case "m":
                GameMessenger.showAutoModeMenu();
                break;

            case "exit":
            case "e":
                running = false;
                GameMessenger.showInfoExit();
                break;

            default:
                System.out.println("Неизвестная команда");
        }
    }

    private void sleep(int ms) {

        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {
        }
    }

    private void makeTurn() {
        simulation.nextTurn();
        if (simulation.isGameOver()) {
            running = false;
            GameMessenger.showInfoGameOver();
            System.exit(0);
        }
    }
}