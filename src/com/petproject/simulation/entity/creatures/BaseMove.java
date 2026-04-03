package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public abstract class BaseMove implements Move{
    protected abstract Class<? extends Entity> getTargetClass();
    protected abstract boolean canEatTarget(Entity target);
    protected abstract void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap);
    protected abstract void onNoTargetFound(Creature creature, WorldMap worldMap);

    @Override
    public void move(Creature creature, WorldMap worldMap) {

        Optional<Coordinates> nextStep = FinderService.findTarget(creature, worldMap, getTargetClass());

        if (nextStep.isPresent()) {
            moveToStep(creature, nextStep.get(), worldMap);
        } else {
            onNoTargetFound(creature, worldMap);
        }
    }
    protected void moveToStep(Creature creature, Coordinates nextStep, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return;
        }

        Optional<Entity> target = worldMap.getEntity(nextStep);

        if (target.isEmpty()) {
            worldMap.moveEntity(currentPos.get(), nextStep, creature);
            return;
        }
         if (canEatTarget(target.get())) {
            onReachTarget(creature, nextStep, worldMap);
        } else {
            onNoTargetFound(creature, worldMap);
        }
    }

}
