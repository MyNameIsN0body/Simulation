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

    private void moveToStep(Creature creature, Coordinates nextPosition, WorldMap worldMap) {
        Optional<Coordinates> currentPositionOpt = worldMap.getEntityCoordinate(creature);
        if (currentPositionOpt.isEmpty()) {
            return;
        }
        Coordinates currentPosition = currentPositionOpt.get();
        Optional<Entity> target = worldMap.getEntity(nextPosition);

        if (target.isEmpty()) {
            worldMap.removeEntity(currentPosition);
            worldMap.putEntity(nextPosition, creature);
            return;
        }
        Entity targetEntity = target.get();
        if (targetEntity.canBeEatenBy(creature)) {
            worldMap.removeEntity(nextPosition);
            worldMap.removeEntity(currentPosition);
            worldMap.putEntity(nextPosition, creature);

            onReachTarget(creature, nextPosition, worldMap);
        } else {
            onNoTargetFound(creature, worldMap);
        }
    }

    public static void moveRandomly(Entity entity, WorldMap worldMap) {
        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty()) {
            return;
        }
        Coordinates currentPosition = entityCoordinateOpt.get();
        for (Direction direction : Direction.shuffled()) {
            Coordinates nextPosition = direction.move(currentPosition);

            if (!worldMap.isValidCoordinate(nextPosition)) {
                continue;
            }

            Optional<Entity> occupant = worldMap.getEntity(nextPosition);

            if (occupant.isEmpty()) {
                worldMap.removeEntity(currentPosition);
                worldMap.putEntity(nextPosition, entity);
                break;
            }
            if (occupant.get().canBeEnteredBy(entity)) {
                worldMap.removeEntity(occupant.get());
                worldMap.removeEntity(currentPosition);
                worldMap.putEntity(nextPosition, entity);
                break;
            }
        }
    }

}
