package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.services.Direction;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public abstract class BaseMovable {
    protected abstract Class<? extends Entity> getTargetClass();

    protected abstract void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap);

    protected abstract void onEmptyCell(Creature creature, Coordinates targetCoordinate, WorldMap worldMap);

    protected abstract void onBlocked(Creature creature, WorldMap worldMap);

    public void move(Creature creature, WorldMap worldMap) {

        Optional<Coordinates> nextStepOpt = FinderService.findTarget(creature, worldMap, getTargetClass());

        if (nextStepOpt.isEmpty()) {
            onBlocked(creature, worldMap);
            return;
        }
        Coordinates nextStep = nextStepOpt.get();
        resolveStep(creature, nextStep, worldMap);
    }

    private void resolveStep(Creature creature, Coordinates nextStep, WorldMap worldMap) {
        Optional<Entity> occupant = worldMap.getEntity(nextStep);
        if (occupant.isEmpty()) {
            onEmptyCell(creature, nextStep, worldMap);
            return;
        }
        Entity target = occupant.get();

        if (target.canBeEnteredBy(creature)) {
            onReachTarget(creature, nextStep, worldMap);
        } else {
            onBlocked(creature, worldMap);
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
