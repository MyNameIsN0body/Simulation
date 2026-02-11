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
            System.out.println("\n" + step.toString() + "\n" );
            currentTurn++;
            mapConsoleRenderer.renderWorld(worldMap);
            GameMessenger.showStatus(worldMap, currentTurn);
        }
    }


    private void stepByStepSimulation(Scanner scanner) {
        GameMessenger.showStepByStepModeMenu();
        while (running) {
            System.out.print("\nВведите команду: ");
            System.out.println("ход номер: " + currentTurn);
            String command = scanner.nextLine().toLowerCase().trim();
            switch (command) {
                case "exit", "e":
                    running = false;
                    break;
                case "step", "st":
                    nextTurn();
                    currentTurn++;
                    break;
                case "+grass", "ag":
                    for (int i = 0; i < 10; i++) {
                        addGrass();
                    }
                    break;
                case "reproduce", "r":
                    reproduceHerbivore();
                    nextTurn();
                    currentTurn++;
                    GameMessenger.showStatus(worldMap, currentTurn);
                    break;
                default:
                    System.out.println("Неизвестная команда. Используйте: exit, e, step, st, +grass, ag, reproduce, r");
            }
            mapConsoleRenderer.renderWorld(worldMap);
            GameMessenger.showStatus(worldMap, currentTurn);
        }
        scanner.close();
        System.out.println("Программа завершена.");

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
        mapConsoleRenderer.renderWorld(worldMap);
        while (running) {
            System.out.print("\nВведите команду: ");
            String command = scanner.nextLine().toLowerCase().trim();
            switch (command) {
                case "pause", "p":
                    pauseSimulation();
                    break;
                case "continue", "c":
                    continueSimulation();
                    break;
                case "exit", "e":
                    running = false;
                    synchronized (lock) {
                        lock.notify();
                    }
                    System.out.println("Ожидание завершения потока...");
                    break;
                case "step", "st":
                    step();
                    break;
                default:
                    System.out.println("Неизвестная команда. Используйте: pause, continue, exit, e");
            }
        }
        scanner.close();
        System.out.println("Программа завершена.");
    }

    private void step() {
        nextTurn();
        currentTurn++;
        mapConsoleRenderer.renderWorld(worldMap);
        GameMessenger.showStatus(worldMap, currentTurn);
    }

    private void reproduceHerbivore() {
        ReproduceAction reproduceAction = new ReproduceAction();
        reproduceAction.execute(worldMap);
    }

    private void addGrass() {
        GrassGrowthAction addGrass = new GrassGrowthAction();
        addGrass.execute(worldMap);
    }

    private void pauseSimulation() { //- приостановить бесконечный цикл симуляции и рендеринга
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
//                currentTurn++;
//                mapConsoleRenderer.renderWorld(worldMap);
                try {
                    Thread.sleep(700);
                } catch (InterruptedException e) {
                    break;
                }
            }
            GameMessenger.showStatus(worldMap, currentTurn);
        }
        System.out.println("Цикл остановлен.");
    }
}