package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.services.Direction;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public abstract class BaseMovable {

    protected abstract void interact(Creature creature, Coordinates targetCoordinate, WorldMap worldMap);

    protected abstract void onBlocked(Creature creature, WorldMap worldMap);

    protected abstract Optional<Coordinates> findStep(Creature creature, WorldMap worldMap);

    public void move(Creature creature, WorldMap worldMap) {

        Optional<Coordinates> nextStepOpt = findStep(creature, worldMap);

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
            relocate(creature, nextStep, worldMap);
            return;
        }
        Entity target = occupant.get();

        if (target.canBeEnteredBy(creature)) {
            interact(creature, nextStep, worldMap);
        } else {
            onBlocked(creature, worldMap);
        }
    }

    protected void relocate(Creature creature, Coordinates to, WorldMap worldMap) {
        Optional<Coordinates> currentPositionOpt = worldMap.getEntityCoordinate(creature);
        if (currentPositionOpt.isEmpty()) {
            return;
        }

        worldMap.removeEntity(currentPositionOpt.get());
        worldMap.putEntity(to, creature);
    }

    protected void moveRandomly(Creature creature, WorldMap worldMap) {
        Optional<Coordinates> currentPositionOpt = worldMap.getEntityCoordinate(creature);
        if (currentPositionOpt.isEmpty()) {
            return;
        }
        Coordinates currentPosition = currentPositionOpt.get();
        for (Direction direction : Direction.shuffled()) {
            Coordinates nextPosition = direction.move(currentPosition);

            if (!worldMap.isValidCoordinate(nextPosition)) {
                continue;
            }

            Optional<Entity> occupant = worldMap.getEntity(nextPosition);

            if (occupant.isEmpty()) {
                worldMap.removeEntity(currentPosition);
                worldMap.putEntity(nextPosition, creature);
                return;
            }
        }
    }

}
