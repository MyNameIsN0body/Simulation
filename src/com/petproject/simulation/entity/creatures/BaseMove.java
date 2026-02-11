package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public abstract class BaseMove implements Move{
    protected abstract EntityType getTargetType();
    protected abstract boolean canEatTarget(Entity target);
    protected abstract void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap);
    protected abstract void onNoTargetFound(Creature creature, WorldMap worldMap);

    @Override
    public void move(Creature creature, WorldMap worldMap) {

        Optional<Coordinates> nextStep = FinderService.findTarget(creature, worldMap, getTargetType());

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

        Entity target = worldMap.getEntity(nextStep.getX(), nextStep.getY());

        if (target == null) {
            performMove(creature, currentPos.get(), nextStep, worldMap);
            return;
        }
         if (canEatTarget(target)) {
            onReachTarget(creature, nextStep, worldMap);
        } else {
            onNoTargetFound(creature, worldMap);
        }
    }

    protected void performMove(Creature creature, Coordinates from, Coordinates to, WorldMap worldMap) {
        worldMap.removeEntity(from);
        worldMap.setEntity(to, creature);
    }

}
