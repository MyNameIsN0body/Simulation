package com.petproject.simulation.services;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public class MoveService {
    private MoveService() {
    }

    public static boolean tryMove(Entity entity, Coordinates target, WorldMap worldMap) {
        return canMove(entity, target, worldMap);
    }

    public static void moveRandomly(Entity entity, WorldMap worldMap) {
        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty()) {
            return;
        }
        Coordinates entityCoordinate = entityCoordinateOpt.get();
        for (Direction direction: Direction.shuffled()) {
            Coordinates newPosition = direction.move(entityCoordinate);
            if (tryMove(entity, newPosition, worldMap)) {
                worldMap.moveEntity(entityCoordinateOpt.get(), newPosition, entity);
                break;
            }
        }
    }
    public static void moveCreature(Entity entity, Coordinates newPlace, WorldMap worldMap) {
        if (creatureIsDead(entity,worldMap)) {
            worldMap.removeEntity(entity);
            return;
        }

        Optional<Coordinates> entityCoordinateOpt = worldMap.getEntityCoordinate(entity);
        if (entityCoordinateOpt.isEmpty() ) {
            return;
        }
        Coordinates entityCoordinate = entityCoordinateOpt.get();
        worldMap.removeEntity(entityCoordinate);
        worldMap.putEntity(newPlace, entity);
    }

    private static boolean canMove(Entity entity, Coordinates target, WorldMap worldMap) {
        if (!worldMap.isValidCoordinate(target)) {
            return false;
        }

        Optional<Entity> occupant = worldMap.getEntity(target);
        if (occupant.isEmpty()) {
            return true;
        }
        return occupant.get().canBeEnteredBy(entity);
    }

    public static void soulHarvester(Creature creature) {
        creature.setEnergy(creature.getEnergy() - 1);
    }
    private static boolean creatureIsDead(Entity entity, WorldMap worldMap) {
        if (entity instanceof Creature creature) {
            if (creature.getEnergy() <= 0) {
                return true;
            }
        }
        return false;
    }
}
