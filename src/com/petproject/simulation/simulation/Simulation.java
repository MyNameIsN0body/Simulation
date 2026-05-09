package com.petproject.simulation.simulation;

import com.petproject.simulation.render.MapConsoleRenderer;
import com.petproject.simulation.actions.*;
import com.petproject.simulation.world.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final WorldMap worldMap;
    private int currentTurn = 0;

    private final MapConsoleRenderer renderer = new MapConsoleRenderer();

    private final List<Action> initActions = new ArrayList<>();
    private final List<Action> turnActions = new ArrayList<>();

    public Simulation(WorldMap worldMap) {
        this.worldMap = worldMap;

        initActions.add(new InitAction(80, 20, 18, 20, 3));

        turnActions.add(new MoveAction());
        turnActions.add(new GrassGrowthAction());
        turnActions.add(new ReproduceAction());
    }

    public void initialize() {
        for (Action action : initActions) {
            action.execute(worldMap);
        }
    }

    public synchronized void nextTurn() {
        for (Action action : turnActions) {
            action.execute(worldMap);
        }

        currentTurn++;
        render();
    }

    private void render() {
        renderer.renderWorld(worldMap);
        GameMessenger.showStatus(calculateGameState());
    }

    public boolean isGameOver() {
        GameStats stats = calculateGameState();
        return stats.getHerbivore() == 0 && stats.getPredators() == 0;
    }

    public GameStats calculateGameState() {
        GameStats stats = new GameStats(currentTurn, 0, 0, 0);

        for (var entity : worldMap.getAllEntities()) {
            stats = entity.updateStats(stats);
        }

        return stats;
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }
}