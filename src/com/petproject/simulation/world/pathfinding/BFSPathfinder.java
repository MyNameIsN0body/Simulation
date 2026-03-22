package com.petproject.simulation.world.pathfinding;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.DirectionService;
import com.petproject.simulation.world.WorldMap;

import java.util.*;

public class BFSPathfinder {
    private BFSPathfinder() {}
    public static List<Coordinates> findPath(WorldMap worldMap, Coordinates start, EntityType targetType) {

        Queue<PathNode> queue = new LinkedList<>();
        Set<Coordinates> visited = new HashSet<>();

        queue.add(new PathNode(start, null));
        visited.add(start);

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();


            if (!current.coordinates.equals(start)) {
                Optional<Entity> entity = worldMap.getEntity(current.coordinates);
                if (entity.isPresent() && entity.get().getType() == targetType) {
                    return reconstructPath(current);
                }
            }

            for (int i = 0; i < DirectionService.DIRECTION_COUNT; i++) {
                int newX = current.coordinates.x() + DirectionService.DX[i];
                int newY = current.coordinates.y() + DirectionService.DY[i];
                Coordinates nextCoordinates = new Coordinates(newX, newY);

                if (worldMap.isValidCoordinate(nextCoordinates) && !visited.contains(nextCoordinates)) {
                    if (isPassable(worldMap, nextCoordinates, targetType)) {
                        visited.add(nextCoordinates);
                        queue.add(new PathNode(nextCoordinates, current));
                    }
                }
            }
        }
        return Collections.emptyList();
    }

    private static boolean isPassable(WorldMap map, Coordinates coordinates, EntityType targetType) {
        Optional<Entity> entity = map.getEntity(coordinates);
        return entity.isEmpty() || entity.get().getType() == targetType;
    }

    private static List<Coordinates> reconstructPath(PathNode endNode) {
        List<Coordinates> path = new ArrayList<>();
        PathNode current = endNode;
        while (current != null && current.parent != null) {
            path.add(0, current.coordinates);
            current = current.parent;
        }
        return path;
    }

    private static class PathNode {
        final Coordinates coordinates;
        final PathNode parent;

        PathNode(Coordinates coordinates, PathNode parent) {
            this.coordinates = coordinates;
            this.parent = parent;
        }
    }
}
