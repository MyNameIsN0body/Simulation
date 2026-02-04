package com.petproject.simulation.services;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;

public class CellFinderService {
    private CellFinderService() {}

    public static Coordinates findEmptyCellNear(Entity entity, WorldMap worldMap) {
        return findEmptyCellNear(worldMap, worldMap.getEntityCoordinate(entity).orElse(null));
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
    public static Coordinates findNearestEmptyCell(WorldMap worldMap, Coordinates start, int maxRadius) {
        for (int radius = 1; radius <= maxRadius; radius++) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    if (dx == 0 && dy == 0) continue;

                    Coordinates cell = new Coordinates(start.getX() + dx, start.getY() + dy);
                    if (worldMap.isValidCoordinate(cell) && worldMap.isCellEmpty(cell)) {
                        return cell;
                    }
                }
            }
        }
        return null;
    }
}
