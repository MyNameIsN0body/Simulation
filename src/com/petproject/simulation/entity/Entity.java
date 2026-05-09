package com.petproject.simulation.entity;

import com.petproject.simulation.simulation.GameStats;
import com.petproject.simulation.world.WorldMap;

public abstract class Entity {

    public boolean canBeEnteredBy(Entity entity) {
        return false;
    }

    public void makeMove(WorldMap worldMap) {}

    public GameStats updateStats(GameStats stats) {
        return stats;
    }

    public boolean existsIn(WorldMap worldMap) {
        return worldMap.getEntityCoordinate(this).isPresent();
    }
}
