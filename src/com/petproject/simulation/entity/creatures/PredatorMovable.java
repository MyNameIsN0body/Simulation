package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;


public class PredatorMovable extends BaseMovable {
    private static final int ENERGY_FROM_HERBIVORE = 5;
    private static final int ENERGY_FOR_STEP = 2;

    @Override
    protected  Class<? extends Entity> getTargetClass() {
        return Herbivore.class;
    }

    @Override
    protected void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        creature.setEnergy(creature.getEnergy() + ENERGY_FROM_HERBIVORE);
    }

    @Override
    protected void onNoTargetFound(Creature creature, WorldMap worldMap) {
        moveRandomly(creature, worldMap);
        creature.setEnergy(creature.getEnergy() - ENERGY_FOR_STEP);
    }
}
