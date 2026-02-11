package com.petproject.simulation.world.pathfinding;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.DirectionService;
import com.petproject.simulation.world.WorldMap;

import java.util.*;

public class BFSPathfinder {
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static List<Coordinates> findPathToNearest2(WorldMap worldMap, Entity entity,
                                                       String targetType) {
        String entityType = entity.getType().toString();
        Optional<Coordinates> startOpt = worldMap.getEntityCoordinate(entity);
        if (startOpt.isEmpty()) {
            return new ArrayList<>();
        }
        Coordinates start = startOpt.get();

        int maxRows = worldMap.getWorldLength();
        int maxCols = worldMap.getWorldWidth();

        boolean[][] visited = new boolean[maxRows][maxCols];
        Queue<PathNode> queue = new LinkedList<>();

        visited[start.getX()][start.getY()] = true;
        queue.add(new PathNode(start, null));

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            // Пропускаем проверку самой стартовой клетки
            if (current.coordinates == start){//(current.coordinates.equals(start)) {
                // Продолжаем искать соседей, но не проверяем эту клетку как цель
            } else {
                Optional<Entity> currentEntity = worldMap.getEntity(current.coordinates);
                // Проверяем, нашли ли цель
                if (currentEntity.isPresent() &&
                        currentEntity.get().getType().toString().equals(targetType)) {
                    return reconstructPath(current);
                }
            }


            // Проверяем соседей
            for (int i = 0; i < DirectionService.DIRECTION_COUNT; i++) {
                int newX = current.coordinates.getX() + DirectionService.DX[i];
                int newY = current.coordinates.getY() + DirectionService.DY[i];

                if (isValidCoordinate(newX, newY, maxRows, maxCols) &&
                        !visited[newX][newY] &&
                        isPassable(worldMap, newX, newY, entityType)) {

                    visited[newX][newY] = true;
                    Coordinates newCoord = new Coordinates(newX, newY);
                    queue.add(new PathNode(newCoord, current));
                }
            }
        }

        return new ArrayList<>();
    }

    //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public static List<Coordinates> findPathToNearest(WorldMap worldMap, Coordinates start,
                                                      String targetType, String entityType) {

        if (worldMap == null || start == null || targetType == null || entityType == null) {
            System.err.println("Ошибка: один из параметров равен null");
            return new ArrayList<>();
        }


        int maxRows = worldMap.getWorldLength();
        int maxCols = worldMap.getWorldWidth();

        boolean[][] visited = new boolean[maxRows][maxCols];
        Queue<PathNode> queue = new LinkedList<>();

        visited[start.getX()][start.getY()] = true;
        queue.add(new PathNode(start, null));

        while (!queue.isEmpty()) {
            PathNode current = queue.poll();

            // Пропускаем проверку самой стартовой клетки
            if (current.coordinates.equals(start)) {
                // Продолжаем искать соседей, но не проверяем эту клетку как цель
            } else {
                Optional<Entity> currentEntity = worldMap.getEntity(current.coordinates);

                // Проверяем, нашли ли цель
                if (currentEntity.isPresent() &&
                        currentEntity.get().getType().toString().equals(targetType)) {
                    return reconstructPath(current);
                }
            }

            // Проверяем соседей
            for (int i = 0; i < DirectionService.DIRECTION_COUNT; i++) {
                int newX = current.coordinates.getX() + DirectionService.DX[i];
                int newY = current.coordinates.getY() + DirectionService.DY[i];

                if (isValidCoordinate(newX, newY, maxRows, maxCols) &&
                        !visited[newX][newY] &&
                        isPassable(worldMap, newX, newY, entityType)) {

                    visited[newX][newY] = true;
                    Coordinates newCoord = new Coordinates(newX, newY);
                    queue.add(new PathNode(newCoord, current));
                }
            }
        }

        return new ArrayList<>();
    }

//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    static class PathNode {
        Coordinates coordinates;
        PathNode parent;

        public PathNode(Coordinates coordinates, PathNode parent) {
            this.coordinates = coordinates;
            this.parent = parent;
        }
    }

    /**
     * Проверяет, можно ли пройти через клетку
     */
    private static boolean isPassable(WorldMap worldMap, int x, int y, String entityType) {
        // Если клетка пустая - можно пройти
        if (worldMap.isCellEmpty(x, y)) {
            return true;
        }

        // Получаем сущность в клетке
        Entity entity = worldMap.getEntity(x, y);
        if (entity == null) {
            return true;
        }


        // Логика проходимости в зависимости от типа сущности
        switch (entityType) {
            case "HERBIVORE":
                // Травоядные могут проходить через траву
                return entity.getType() == EntityType.GRASS ||
                        entity.getType() == EntityType.HERBIVORE;
            case "PREDATOR":
                // Хищники могут проходить через траву и травоядных
                return entity.getType() == EntityType.GRASS ||
                        entity.getType() == EntityType.HERBIVORE||
                        entity.getType() == EntityType.PREDATOR;
            default:
                return worldMap.isCellEmpty(x, y);
        }
    }

    /**
     * Восстанавливает путь от конечной точки к начальной
     */
    private static List<Coordinates> reconstructPath(PathNode endNode) {
        List<Coordinates> path = new ArrayList<>();

        for (PathNode node = endNode; node != null; node = node.parent) {
            path.add(node.coordinates);
        }

        Collections.reverse(path);
        return path;
    }

    private static boolean isValidCoordinate(int x, int y, int maxRows, int maxCols) {
        return x >= 0 && x < maxRows && y >= 0 && y < maxCols;
    }


}