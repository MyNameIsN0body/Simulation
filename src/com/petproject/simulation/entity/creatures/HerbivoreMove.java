package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;
import com.petproject.simulation.world.pathfinding.BFSPathfinder;

import java.util.List;
import java.util.Optional;

public class HerbivoreMove extends BaseMove{
    private static final int ENERGY_FROM_GRASS = 3;

    @Override
    protected EntityType getTargetType() {
        return EntityType.GRASS;
    }

    @Override
    protected boolean canEatTarget(Entity target) {
        return target != null && target.getType() == EntityType.GRASS;
    }

    @Override
    protected void onReachTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
// Травоядное съедает траву
        worldMap.removeEntity(targetCoordinate);
        performMove(creature,
                worldMap.getEntityCoordinate(creature).orElse(null),
                targetCoordinate,
                worldMap);

        // Восстанавливаем сытость
        creature.setEnergy(creature.getEnergy() + ENERGY_FROM_GRASS);
    }

    @Override
    protected void onNoTargetFound(Creature creature, WorldMap worldMap) {
        if (!avoidPredators(creature, worldMap)) {
            MoveService.moveRandomly(creature, worldMap);
        }
    }
    private boolean avoidPredators(Creature creature, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return false;
        }

        // Ищем ближайшего хищника
        List<Coordinates> predatorPath = BFSPathfinder.findPathToNearest(
                worldMap,
                currentPos.get(),
                EntityType.PREDATOR.toString(),
                EntityType.HERBIVORE.toString()
        );

        // Если хищник близко (в пределах 9 клеток), убегаем
        if (!predatorPath.isEmpty() && predatorPath.size() <= 10) {
            Coordinates predatorDirection = predatorPath.get(1);
            Coordinates runDirection = calculateOppositeDirection(currentPos.get(), predatorDirection);

            if (worldMap.isValidCoordinate(runDirection) &&
                    worldMap.isCellEmpty(runDirection.getX(), runDirection.getY())) {
                performMove(creature, currentPos.get(), runDirection, worldMap);
                return true;
            }
        }

        return false;
    }
    private Coordinates calculateOppositeDirection(Coordinates current, Coordinates predatorStep) {
        int dx = current.getX() - predatorStep.getX();
        int dy = current.getY() - predatorStep.getY();

        // Нормализуем направление
        if (Math.abs(dx) > Math.abs(dy)) {
            return new Coordinates(current.getX() + Integer.signum(dx), current.getY());
        } else {
            return new Coordinates(current.getX(), current.getY() + Integer.signum(dy));
        }
    }

}
