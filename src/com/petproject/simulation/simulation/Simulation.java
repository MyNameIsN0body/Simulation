package com.petproject.simulation.simulation;

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

    private final List<Actions> initActions = new ArrayList<>();
    private final List<Actions> turnActions = new ArrayList<>();

    public Simulation(int length, int width) {
        this.worldMap = new WorldMap(length, width);
        // Создаем команды инициализации
        initActions.add(new InitAction(80, 20, 18, 20, 3));
        // Создаем команды для каждого хода
        turnActions.add(new MoveAction());
        turnActions.add(new EatAction());
        turnActions.add(new GrassGrowthAction());
        turnActions.add(new ReproduceAction());

    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void initialize() {
        initActions.getLast().execute(worldMap);
    }

    public void nextTurn() {
        for (Actions step : turnActions) {
            step.execute(worldMap);
//            System.out.println("\n" + step.toString() + "\n" );
            currentTurn++;
            gameStats();
            try {
                Thread.sleep(700);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
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
                    System.out.println("\u001B[31mНеизвестная команда.\u001B[0m \nИспользуйте: \u001B[32mstep, s, reproduce, r, +grass, ag, menu, m, exit, e\u001B[0m");
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
                    System.out.println("Неизвестная команда. Используйте: pause, continue, exit, e");
            }
        }
        scanner.close();
        System.out.println("Программа завершена.");
        gameStats();
    }

    private void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }

    private void gameStats() {
        clearConsole();
        mapConsoleRenderer.renderWorld(worldMap);
        GameMessenger.showStatus(worldMap, currentTurn);
    }

    private void step() {
        for (Actions actions: turnActions) {
            if (actions instanceof MoveAction) {
                actions.execute(worldMap);
            }
        }
        currentTurn++;
        gameStats();
    }

    private void tryReproduce() {
        for (Actions actions: turnActions) {
            if (actions instanceof ReproduceAction) {
                actions.execute(worldMap);
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
        System.out.println("Ход номер: " + currentTurn);
    }

    private void continueSimulation() {
        paused = false;
        synchronized (lock) {
            lock.notify();
        }
        System.out.println("Бесконечный цикл симуляции возобновлен.");
    }

    public void runSimulation() {
            pauseSimulation();
            GameMessenger.showAutoModeMenu();
        while (running) {
            // Проверяем паузу с синхронизацией
            synchronized (lock) {
                while (paused && running) {
                    try {
                        lock.wait();  // ждём, пока не вызовут notify()
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }

            // Если не остановлено — выполняем итерацию
            if (running) {
                nextTurn();
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
        System.out.println("Цикл остановлен.");
    }
}