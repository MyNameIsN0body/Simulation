package com.petproject.simulation.services;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.world.WorldMap;
import com.petproject.simulation.world.pathfinding.BFSPathfinder;

import java.util.List;
import java.util.Optional;

public class MoveService {

    private MoveService() {
    }

    public static boolean tryMove(Entity entity, Coordinates target, WorldMap worldMap) {
        if (!canMove(entity, target, worldMap)) {
            return false;
        }
        return true;
    }

    private static boolean canMove(Entity entity, Coordinates target, WorldMap worldMap) {
        if (!worldMap.isValidCoordinate(target)) {
            return false;
        }
        Optional<Entity> occupant = worldMap.getEntity(target);
        if (occupant.isEmpty()) {
            return true;
        }
        return switch (entity.getType()) {
            case HERBIVORE -> occupant.get().getType() == EntityType.GRASS;
            case PREDATOR -> occupant.get().getType() == EntityType.HERBIVORE;
            default -> false;
        };
    }

    public static void moveCreature(Entity entity, Coordinates newPlace, WorldMap worldMap) {
        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty()) {
            return;
        }
        Coordinates entityCoordinate = entityCoordinateOpt.get();
        worldMap.removeEntity(entityCoordinate);
        worldMap.setEntity(newPlace, entity);
    }


    public static List<Coordinates> findPathToTarget(WorldMap worldMap, Coordinates coordinates, String entityTarget, String entityOrigin) {
        return BFSPathfinder.findPathToNearest(worldMap, coordinates, entityTarget, entityOrigin);
    }

    public static void moveRandomly(WorldMap worldMap, Entity entity) {
        int[] direction = DirectionService.getShuffledDirections();
        for (int direct : direction) {
            Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
            if (entityCoordinateOpt.isEmpty()) {
                return;
            }
            Coordinates entityCoordinate = entityCoordinateOpt.get();
            Coordinates newPosition = DirectionService.calculateNewPosition(entityCoordinate, direct);
            if (tryMove(entity, newPosition, worldMap)) {
                moveCreature(entity, newPosition, worldMap);
            }
        }
    }

    public static void moveTowardsHerbivore(WorldMap worldMap, Entity entity) {
        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty()) {
            return;
        }
        Coordinates coordinatesPredator = entityCoordinateOpt.get();
        List<Coordinates> path = findPathToTarget(worldMap, coordinatesPredator, EntityType.HERBIVORE.toString(), EntityType.PREDATOR.toString());

        if (!path.isEmpty() && path.size() > 1) {
            // Берем первую точку пути (после текущей позиции)
            Coordinates nextStep = path.get(1);

            // Проверяем, что находится в целевой клетке
            if (worldMap.isCellEmpty(nextStep.getX(), nextStep.getY())) {
                // Клетка пустая - просто перемещаемся
                moveCreature(entity,nextStep, worldMap);

            } else if (worldMap.getEntity(nextStep.getX(), nextStep.getY()).getType() == EntityType.HERBIVORE) {
                // В клетке травоядное - атакуем
                attackHerbivore(worldMap, nextStep, entity);
            }
        } else {
            // Если пути к травоядным нет, ищем случайное направление
            moveRandomly(worldMap, entity);
        }

    }

    /**
     * Атака травоядного
     */
    private static void attackHerbivore(WorldMap worldMap, Coordinates targetCoord, Entity entity) {
//        Усложнить логику
//        System.out.println("Хищник съел травоядное в ");
        moveCreature(entity, targetCoord, worldMap);
    }

    public static void moveTowardsGrass(WorldMap worldMap, Entity entity) {
        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty()) {
            return;
        }

        Coordinates coordinatesEntity = entityCoordinateOpt.get();
        List<Coordinates> path = findPathToTarget(worldMap, coordinatesEntity, EntityType.GRASS.toString(), EntityType.HERBIVORE.toString());

        if (!path.isEmpty() && path.size() > 1) {
            // Берем первую точку пути (после текущей позиции)
            Coordinates nextStep = path.get(1);

            // Проверяем, можно ли сделать шаг
            Entity target = worldMap.getEntity(nextStep.getX(), nextStep.getY());

            if (target == null || target.getType() == EntityType.GRASS) {

                // Удаляем с текущей позиции
                worldMap.removeEntity(coordinatesEntity);
                // Перемещаемся
                worldMap.setEntity(nextStep, entity);
            }
        }
    }

}
