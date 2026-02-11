package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;

public class HerbivoreHunting extends BaseHunting {
    private static final int ENERGY_GAIN = 10;

    @Override
    protected EntityType getTargetType() {
        return EntityType.GRASS;
    }

    @Override
    protected int getEnergyGain() {
        return ENERGY_GAIN;
    }

    @Override
    protected boolean canEatTarget(Entity target) {
        return target != null && target.getType() == EntityType.GRASS;
    }

    @Override
    protected void onEatTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
        MoveService.moveCreature(creature, targetCoordinate, worldMap);
    }
    protected void die(Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
   }

}