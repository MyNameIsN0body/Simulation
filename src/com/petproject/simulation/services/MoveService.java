package com.petproject.simulation.services;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;

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

    public static void moveRandomly(Entity entity, WorldMap worldMap) {
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
    public static void moveCreature(Entity entity, Coordinates newPlace, WorldMap worldMap) {
        if (CreatureIsDead(entity,worldMap)) {
            worldMap.removeEntity(entity);
            return;
        }

        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty() ) {
            return;
        }
        Coordinates entityCoordinate = entityCoordinateOpt.get();
        worldMap.removeEntity(entityCoordinate);
        worldMap.setEntity(newPlace, entity);
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

    private static boolean checkEntityCoordinate(Creature creature, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        return currentPos.isPresent();
    }
    private static boolean checkEntityCoordinate(Entity entity, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(entity);
        return currentPos.isPresent();
    }
    public static void soulHarvester(Creature creature) {
        int countEnergy = creature.getEnergy();
        if (countEnergy > 0) {
            creature.setEnergy(countEnergy - 1);
        }
    }
    private static boolean CreatureIsDead(Entity entity, WorldMap worldMap) {
        if (entity instanceof Creature creature) {
            if (creature.getEnergy() <= 0) {
                return true;
            }
        }
        return false;
    }
}
