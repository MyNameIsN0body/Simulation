package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.services.Direction;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public abstract class BaseMovable implements Movable {
    protected abstract Class<? extends Entity> getTargetClass();
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

    private void moveToStep(Creature creature, Coordinates nextStep, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return;
        }

        Optional<Entity> target = worldMap.getEntity(nextStep);

        if (target.isEmpty()) {
            worldMap.moveEntity(currentPos.get(), nextStep, creature);
            return;
        }
        if (target.get().canBeEatenBy(creature)) {
            onReachTarget(creature, nextStep, worldMap);
        } else {
            onNoTargetFound(creature, worldMap);
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
