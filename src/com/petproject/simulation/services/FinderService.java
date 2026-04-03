package com.petproject.simulation.services;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;
import com.petproject.simulation.world.pathfinding.BFSPathfinder;

import java.util.List;
import java.util.Optional;

public class FinderService {
    private FinderService() {}

    public static Optional<Coordinates> findEmptyCellNear(Entity entity, WorldMap worldMap) {
        Optional<Coordinates> position = worldMap.getEntityCoordinate(entity);
        if(position.isEmpty()) {
            return Optional.empty();
        }
        return findEmptyCellNear(worldMap, position.get());
    }


    public static Optional<Coordinates> findTarget(Creature creature, WorldMap worldMap,
                                                   Class<? extends Entity> targetClass) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return Optional.empty();
        }

        List<Coordinates> path = BFSPathfinder.findPath(worldMap, currentPos.get(), targetClass);

        return path.isEmpty()
                ? Optional.empty()
                : Optional.of(path.get(0));
    }

    public static Optional<Coordinates> findEmptyCellNear(WorldMap worldMap, Coordinates position) {
        for(Direction direction: Direction.values()) {
            Coordinates cell = direction.move(position);
            if(worldMap.isValidCoordinate(cell) && worldMap.isCellEmpty(cell)) {
                return Optional.of(cell);
            }
        }

        return Optional.empty();
    }

}
