package com.petproject.simulation.services;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;
import com.petproject.simulation.world.pathfinding.BFSPathfinder;

import java.util.List;
import java.util.Optional;

public class FinderService {
    private FinderService() {}

    public static Coordinates findEmptyCellNear(Entity entity, WorldMap worldMap) {
        return findEmptyCellNear(worldMap, worldMap.getEntityCoordinate(entity).orElse(null));
    }


    public static Optional<Coordinates> findTarget(Creature creature, WorldMap worldMap,
                                                   EntityType targetType) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return Optional.empty();
        }

        List<Coordinates> path = BFSPathfinder.findPath(worldMap, currentPos.get(), targetType);

        if (!path.isEmpty()) {
            return Optional.of(path.get(0));
        }

        return Optional.empty();
    }

    public static Coordinates findEmptyCellNear(WorldMap worldMap, Coordinates position) {
        if (position == null) {
            return null;
        }

        for (int i = 0; i < DirectionService.DIRECTION_COUNT; i++) {
            Coordinates cell = DirectionService.calculateNewPosition(position, i);
            if (worldMap.isValidCoordinate(cell) && worldMap.isCellEmpty(cell)) {
                return cell;      
            }
        }
        return null;
    }

}
