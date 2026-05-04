package com.petproject.simulation.simulation;

import java.util.Scanner;

public class AutoMode implements SimulationMode {

    private final SimulationController controller;
    private final Scanner scanner;

    public AutoMode(SimulationController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    @Override
    public void run() {

        controller.startLoop();
        GameMessenger.showAutoModeMenu();

        while (controller.isRunning()) {
            String command = scanner.nextLine();
            controller.handleCommand(command);
        }

        System.out.println("Симуляция завершена.");
    }
}