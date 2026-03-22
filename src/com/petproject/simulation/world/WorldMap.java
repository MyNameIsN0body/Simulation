package com.petproject.simulation.world;

import com.petproject.simulation.entity.Entity;

import java.util.*;

public class WorldMap {
    private final int length;
    private final int width;
    private final Random random;

    private final Map<Coordinates, Entity> entityMap = new HashMap<>();

    public WorldMap(int worldLength, int worldWidth) {
        this.length = worldLength;
        this.width = worldWidth;
        this.random = new Random();
    }

    public List<Entity> getAllEntities() {
        return new ArrayList<>(entityMap.values());
    }

    public void putEntity(Coordinates coordinates, Entity entity) {
        entityMap.put(coordinates, entity);
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        return Optional.ofNullable(entityMap.get(coordinates));
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        return !entityMap.containsKey(coordinates);
    }

    public Optional<Coordinates> getEntityCoordinate(Entity entity) {
        for (Map.Entry<Coordinates, Entity> currentEntity : entityMap.entrySet()) {
            if (currentEntity.getValue().equals(entity)) {
                return Optional.of(currentEntity.getKey());
            }
        }
        return Optional.empty();
    }

    public Optional<Coordinates> getRandomEmptyCoordinates() {
        for (int i = 0; i < 100; i++) {
            int x = random.nextInt(length);
            int y = random.nextInt(width);
            if (isCellEmpty(new Coordinates(x, y))) {
                return Optional.of(new Coordinates(x, y));
            }
        }
        return findAnyEmptyCoordinate();
    }

    private Optional<Coordinates> findAnyEmptyCoordinate() {
        for (int y = 0; y < width; y++) {
            for (int x = 0; x < length; x++) {
                if (isCellEmpty(new Coordinates(x, y))) {
                    return Optional.of(new Coordinates(x, y));
                }
            }
        }

        return Optional.empty();
    }

    public boolean isValidCoordinate(Coordinates coordinates) {
        return coordinates.x() >= 0 && coordinates.x() < length && coordinates.y() >= 0 && coordinates.y() < width;
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
    public boolean moveEntity(Coordinates from, Coordinates to, Entity entity) {
        Optional <Entity> entityAtSource = getEntity(from);
        if (entityAtSource.isEmpty() || entityAtSource.get() != entity) {
            return false;
        }
        if (getEntity(to).isPresent() && !from.equals(to)) {
            return false;
        }

        removeEntity(from);
        putEntity(to, entity);
        return true;
    }
}