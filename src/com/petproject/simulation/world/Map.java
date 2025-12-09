package com.petproject.simulation.world;


import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;

import java.util.HashMap;
import java.util.Random;

public class Map {
    private final int worldLength;
    private final int worldWidth;
    private final Random random;

    public Map(int worldLength, int worldWidth) {
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
        this.random = new Random();
    }

    public HashMap<Coordinates, Entity> getMap() {
        return map;
    }

    HashMap<Coordinates, Entity> map = new HashMap<>();

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.setCoordinates(coordinates);
        map.put(coordinates, entity);
    }

    public int getWorldLength() {
        return worldLength;
    }

    public int getWorldWidth() {
        return worldWidth;
    }

    public boolean isCellEmpty(int x, int y) {
        return !map.containsKey(new Coordinates(x, y));
    }

    public String getEntityType(int x, int y) {
        return map.get(new Coordinates(x, y)).getTypeName();
    }

    public Entity getEntity(int x, int y) {
        return map.get(new Coordinates(x, y));
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
        return null;
    }
}