package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.services.Direction;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.services.FinderService;
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
            moveRandomly(target, worldMap);
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
            moveRandomly(creature, worldMap);
        }
    }

    public static void moveRandomly(Entity entity, WorldMap worldMap) {
        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty()) {
            return;
        }
        Coordinates entityCoordinate = entityCoordinateOpt.get();
        for (Direction direction: Direction.shuffled()) {
            Coordinates newPosition = direction.move(entityCoordinate);
            if (canMove(entity, newPosition, worldMap)) {
                worldMap.moveEntity(entityCoordinateOpt.get(), newPosition, entity);
                break;
            }
        }
    }
    private static boolean canMove(Entity entity, Coordinates target, WorldMap worldMap) {
        if (!worldMap.isValidCoordinate(target)) {
            return false;
        }

        Optional<Entity> occupant = worldMap.getEntity(target);
        if (occupant.isEmpty()) {
            return true;
        }
        return occupant.get().canBeEnteredBy(entity);
    }
}