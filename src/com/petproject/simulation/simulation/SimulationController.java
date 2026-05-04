package com.petproject.simulation.simulation;

public class SimulationController {

    private final Simulation simulation;

    private volatile boolean running = true;
    private volatile boolean paused = false;

    public SimulationController(Simulation simulation) {
        this.simulation = simulation;
    }

    public void startLoop() {
        Thread loop = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {

                    if (!paused) {
                        simulation.nextTurn();
                    }

                    sleep(1000);
                }
            }
        });

        loop.start();
    }

    public void handleCommand(String command) {
        switch (command) {

            case "pause":
                paused = true;
                System.out.println("⏸ Пауза");
                break;

            case "run":
                paused = false;
                System.out.println("▶ Продолжение");
                break;

            case "step":
                simulation.step();
                break;

            case "exit":
                running = false;
                System.out.println("⛔ Выход...");
                break;

            case "menu":
                GameMessenger.showAutoModeMenu();
                break;

            default:
                System.out.println("Неизвестная команда");
        }
    }

    public boolean isRunning() {
        return running;
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ignored) {}
    }
}