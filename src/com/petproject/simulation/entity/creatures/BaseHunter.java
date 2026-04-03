package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public abstract class BaseHunter implements Hunter {

    protected abstract Class<? extends Entity> getTargetClass();
    protected abstract int getEnergyGain();
    protected abstract boolean canEatTarget(Entity target);
    protected abstract void onEatTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap);


    @Override
    public void hunt(Creature target, WorldMap worldMap) {
        Optional<Coordinates> targetStep = FinderService.findTarget(target, worldMap, getTargetClass());

        if (targetStep.isPresent()) {
            moveToTarget(target, targetStep.get(), worldMap);
        } else {
            MoveService.moveRandomly(target, worldMap);
        }
    }

    private void moveToTarget(Creature creature, Coordinates targetStep, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return;
        }

        Optional<Entity> target = worldMap.getEntity(targetStep);

        if (target.isEmpty()) {
            worldMap.moveEntity(currentPos.get(), targetStep, creature);
        } else if (canEatTarget(target.orElse(null))) {
            // Съедаем цель
            onEatTarget(creature, targetStep, worldMap);
            creature.setEnergy(creature.getEnergy() + getEnergyGain());
        } else {
            MoveService.moveRandomly(creature, worldMap);
        }
    }
}