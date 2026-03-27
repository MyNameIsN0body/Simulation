package com.petproject.simulation.simulation;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.render.MapConsoleRenderer;
import com.petproject.simulation.simulation.actions.*;
import com.petproject.simulation.world.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {

    private static volatile boolean running = true;
    private static volatile boolean paused = false;
    private static final Object lock = new Object();

    private final WorldMap worldMap;
    private int currentTurn = 0;

    MapConsoleRenderer mapConsoleRenderer = new MapConsoleRenderer();

    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();


    public Simulation(int length, int width) {

        this.worldMap = new WorldMap(length, width);

        initActions.add(new InitAction(80, 20, 18, 20, 3));

        turnActions.add(new MoveAction());
        turnActions.add(new EatAction());
        turnActions.add(new GrassGrowthAction());
        turnActions.add(new ReproduceAction());
    }

    public void initialize() {
        initActions.getLast().execute(worldMap);
    }


    public void nextTurn() {
        for (Action step : turnActions) {
            step.execute(worldMap);
            currentTurn++;
            gameStats();
            if (shouldStopSimulation()) {
                running = false;
                return;
            }

            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private boolean shouldStopSimulation() {
        int totalCells = worldMap.getLength() * worldMap.getWidth();
        int occupiedCells = worldMap.getAllEntities().size();
        boolean noEmptyCells = occupiedCells >= totalCells;
        boolean noCreatures = true;

        for (Entity entity : worldMap.getAllEntities()) {
            if (entity instanceof Creature) {
                noCreatures = false;
                break;
            }
        }

        if (noCreatures) {
            System.out.println("""
                              \u001B[36mВсе существа умерли.\u001B[0m
                              \u001B[33mСимуляция завершена\u001B[0m
            """);
            return true;
        }

        if (noEmptyCells) {
            System.out.println("""
                              \u001B[36mНа карте не осталось пустых клеток.\u001B[0m
                                     \u001B[33mСимуляция завершена\u001B[0m
            """);
            return true;
        }

        return false;
    }


    private void stepByStepSimulation(Scanner scanner) {
        GameMessenger.showStepByStepModeMenu();
        while (running) {
            System.out.print("\nВведите команду: ");
            String command = scanner.nextLine().toLowerCase().trim();

            switch (command) {
                case "step", "s":
                    step();
                    break;
                case "reproduce", "r":
                    tryReproduce();
                    break;
                case "+grass", "ag":
                    addGrass(10);
                    break;
                case "menu", "m":
                    GameMessenger.showStepByStepModeMenu();
                    break;
                case "exit", "e":
                    running = false;
                    break;
                default:
                    System.out.println(
                            "\u001B[31mНеизвестная команда.\u001B[0m \nИспользуйте: \u001B[32mstep, s, reproduce, r, +grass, ag, menu, m, exit, e\u001B[0m"
                    );
            }
        }
        scanner.close();
        System.out.println("Программа завершена.");
        gameStats();
    }

    public void startGame() {

        GameMessenger.displayIntro();
        GameMessenger.displaySelectMode();

        initialize();
        Scanner scanner = new Scanner(System.in);
        String mode = scanner.nextLine().toLowerCase().trim();

        if (mode.equals("1")) {
            stepByStepSimulation(scanner);
        } else if (mode.equals("2")) {
            paused = true;
            GameMessenger.showAutoModeMenu();
            Thread loopThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    runSimulation();
                }
            });

            loopThread.start();

            startSimulation(scanner);
        }
    }


    public void startSimulation(Scanner scanner) {
        while (running) {
            System.out.println("\nВведите команду: ");
            String command = scanner.nextLine().toLowerCase().trim();
            switch (command) {
                case "pause", "p":
                    pauseSimulation();
                    break;
                case "continue", "c", "run", "r":
                    continueSimulation();
                    break;
                case "menu", "m":
                    GameMessenger.showAutoModeMenu();
                    break;
                case "exit", "e":
                    running = false;
                    synchronized (lock) {
                        lock.notify();
                    }
                    System.out.println("Ожидание завершения потока...");
                    break;
                case "step", "s":
                    step();
                    break;
                default:
                    System.out.println("Неизвестная команда. Используйте: pause, p, run, r, continue, c, step, s, menu, m, exit, e");
            }
        }
        scanner.close();
        System.out.println("Программа завершена.");
        gameStats();
    }

    private void gameStats() {
        mapConsoleRenderer.renderWorld(worldMap);
        GameMessenger.showStatus(worldMap, currentTurn);
    }

    private void step() {
        for (Action action : turnActions) {
            if (action instanceof MoveAction) {
                action.execute(worldMap);
            }
        }
        currentTurn++;
        gameStats();
    }

    private void tryReproduce() {
        for (Action action : turnActions) {
            if (action instanceof ReproduceAction) {
                action.execute(worldMap);
            }
        }
        currentTurn++;
        gameStats();
    }

    private void addGrass(int count) {
        GrassGrowthAction addGrass = new GrassGrowthAction();
        for (int i = 0; i < count; i++) {
            addGrass.execute(worldMap);
        }
        currentTurn++;
        gameStats();
    }

    private void pauseSimulation() {
        paused = true;
        System.out.println("Бесконечный цикл симуляции приостановлен.");
    }

    private void continueSimulation() {
        paused = false;
        synchronized (lock) {
            lock.notify();
        }
        System.out.println("Бесконечный цикл симуляции запущен.");
    }

    public void runSimulation() {
        while (running) {
            synchronized (lock) {
                while (paused && running) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

            if (running) {
                nextTurn();
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}