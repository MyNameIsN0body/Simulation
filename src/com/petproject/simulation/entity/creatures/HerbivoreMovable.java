package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;

public class HerbivoreMovable extends BaseMovable {
    private static final int ENERGY_FROM_GRASS = 3;

    @Override
    protected Class<? extends Entity> getTargetClass() {
        return Grass.class;
    }

    @Override
    protected void onEmptyCell(Creature creature, Coordinates coordinate, WorldMap worldMap) {
        relocate(creature, coordinate, worldMap);
    }

    @Override
    protected void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
        relocate(creature, targetCoordinate, worldMap);

        creature.setEnergy(creature.getEnergy() + ENERGY_FROM_GRASS);
    }

    @Override
    protected void onBlocked(Creature creature, WorldMap worldMap) {
        moveRandomly(creature, worldMap);
    }

    private void relocate(Creature creature, Coordinates to, WorldMap worldMap) {
        Coordinates from = worldMap.getEntityCoordinate(creature).orElseThrow();

        worldMap.removeEntity(from);
        worldMap.putEntity(to, creature);
    }
}

