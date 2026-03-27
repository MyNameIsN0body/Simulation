package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntitySprite;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;


public class PredatorMove extends BaseMove{
    private static final int ENERGY_FROM_HERBIVORE = 5;
    private static final int ENERGY_FOR_STEP = 2;

    @Override
    protected EntitySprite getTargetType() {
        return EntitySprite.HERBIVORE;
    }

    @Override
    protected boolean canEatTarget(Entity target) {
        return target != null && target.getType() == EntitySprite.HERBIVORE;
    }

    @Override
    protected void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
        worldMap.moveEntity(worldMap.getEntityCoordinate(creature).orElse(null), targetCoordinate, creature);

        creature.setEnergy(creature.getEnergy() + ENERGY_FROM_HERBIVORE);
    }

    @Override
    protected void onNoTargetFound(Creature creature, WorldMap worldMap) {
        MoveService.moveRandomly(creature,worldMap);
        creature.setEnergy(creature.getEnergy() - ENERGY_FOR_STEP);
    }
}
