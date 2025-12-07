package com.petproject.simulation;


import java.util.HashMap;

public class Map {
    private final int worldLength;
    private final int worldWidth;

    public Map(int worldLength, int worldWidth) {
        this.worldLength = worldLength;
        this.worldWidth = worldWidth;
    }

    public HashMap<Coordinates, Entity> getMap() {
        return map;
    }

    HashMap<Coordinates, Entity> map = new HashMap<>();

    public void setEntity(Coordinates coordinates, Entity entity) {
        entity.coordinates= coordinates;
        map.put(coordinates,entity);
    }

    public int getWorldLength() {
        return worldLength;
    }

    public int getWorldWidth() {
        return worldWidth;
    }
}