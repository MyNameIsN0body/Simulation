package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;

public class HerbivoreHunter extends BaseHunter {
    private static final int ENERGY_GAIN = 10;

    @Override
    protected Class<? extends Entity> getTargetClass() {
        return Grass.class;
    }

    @Override
    protected int getEnergyGain() {
        return ENERGY_GAIN;
    }

    @Override
    protected boolean canEatTarget(Entity target) {
        return target != null && getTargetClass().isInstance(target);
    }

    @Override
    protected void onEatTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
        moveTo(creature, targetCoordinate, worldMap);
    }

}