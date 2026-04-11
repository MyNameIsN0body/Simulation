package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;
import com.petproject.simulation.world.pathfinding.BFSPathfinder;

import java.util.List;
import java.util.Optional;

public class HerbivoreMovable extends BaseMovable {
    private static final int ENERGY_FROM_GRASS = 3;

    @Override
    protected  Class<? extends Entity> getTargetClass() {
        return Grass.class;
    }

    @Override
    protected void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
        worldMap.moveEntity(worldMap.getEntityCoordinate(creature).orElse(null),targetCoordinate, creature);
        creature.setEnergy(creature.getEnergy() + ENERGY_FROM_GRASS);
    }

    @Override
    protected void onNoTargetFound(Creature creature, WorldMap worldMap) {
        if (!avoidPredators(creature, worldMap)) {
            moveRandomly(creature, worldMap);
        }
    }
    private boolean avoidPredators(Creature creature, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return false;
        }

        List<Coordinates> predatorPath = BFSPathfinder.findPath(worldMap, currentPos.get(), Predator.class);

        if (!predatorPath.isEmpty() && predatorPath.size() <= 10) {
            Coordinates predatorDirection = predatorPath.get(0); // ??????????????
            Coordinates runDirection = calculateOppositeDirection(currentPos.get(), predatorDirection);

            if (worldMap.isValidCoordinate(runDirection) &&
                    worldMap.isCellEmpty(runDirection)) {
                worldMap.moveEntity(currentPos.get(),runDirection, creature);
                return true;
            }
        }

        return false;
    }
    private Coordinates calculateOppositeDirection(Coordinates current, Coordinates predatorStep) {
        int dx = current.x() - predatorStep.x();
        int dy = current.y() - predatorStep.y();

        // Нормализуем направление
        if (Math.abs(dx) > Math.abs(dy)) {
            return new Coordinates(current.x() + Integer.signum(dx), current.y());
        } else {
            return new Coordinates(current.x(), current.y() + Integer.signum(dy));
        }
    }

}
