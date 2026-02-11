package com.petproject.simulation.world;


import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.customException.NoEmptyCellsException;

import java.util.*;

public class WorldMap {
    private final int worldLength;
    private final int worldWidth;
    private final Random random;

    HashMap<Coordinates, Entity> entityMap = new HashMap<>();

    public WorldMap(int worldLength, int worldWidth) {
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
        this.random = new Random();
    }

    public List<Entity> getAllEntities() {
        return new ArrayList<>(entityMap.values());
    }

    public void setEntity(Coordinates coordinates, Entity entity) {
        entityMap.put(coordinates, entity);
    }
    public Optional<Entity> getEntity(Coordinates coordinates) {
        return Optional.ofNullable(entityMap.get(coordinates));
    }

    public int getWorldLength() {
        return worldLength;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public boolean isCellEmpty(int x, int y) {
        return !entityMap.containsKey(new Coordinates(x, y));
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        return !entityMap.containsKey(coordinates);
    }

    public Entity getEntity(int x, int y) {
        return entityMap.get(new Coordinates(x, y));
    }

    public Optional<Coordinates> getEntityCoordinate(Entity entity) {
        for (Map.Entry<Coordinates, Entity> currentEntity : entityMap.entrySet()) {
            if (currentEntity.getValue().equals(entity)) {
                return Optional.of(currentEntity.getKey());
            }
        }
        return Optional.empty();
    }

    public Coordinates getRandomEmptyCoordinates() {
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(worldLength);
            int y = random.nextInt(worldWidth);
            if (isCellEmpty(x, y)) {
                return new Coordinates(x, y);
            }
        }
        return findAnyEmptyCoordinate();
    }

    private Coordinates findAnyEmptyCoordinate() {
        for (int y = 0; y < worldLength; y++) {
            for (int x = 0; x < worldWidth; x++) {
                if (isCellEmpty(x, y)) {
                    return new Coordinates(x, y);
                }
            }
        }
        throw new NoEmptyCellsException("Карта полностью заполнена");
    }

    public boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < worldLength && y >= 0 && y < worldWidth;
    }
    public boolean isValidCoordinate(Coordinates coordinates) {
        return coordinates.getX() >= 0 && coordinates.getX() < worldLength && coordinates.getY() >= 0 && coordinates.getY() < worldWidth;
    }

    public void removeEntity(Coordinates coordinates) {
        entityMap.remove(coordinates);
    }
    public void removeEntity(Entity entityRemove) {
        Coordinates keyRemove = null;
        for (Map.Entry<Coordinates, Entity> entry: entityMap.entrySet()) {
            if (entry.getValue().equals(entityRemove)) {
                keyRemove = entry.getKey();
                break;
            }
        }
        if (keyRemove != null) {
            entityMap.remove(keyRemove);
        }
    }
}