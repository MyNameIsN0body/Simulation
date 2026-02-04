package com.petproject.simulation.render;

import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.world.WorldMap;

public class MapConsoleRenderer {

    public void renderWorld(WorldMap worldMap) {
        for (int y = 0; y < worldMap.getWorldWidth(); y++) {
            System.out.println();
            for (int x = 0; x < worldMap.getWorldLength(); x++) {
                if (worldMap.isCellEmpty(x, y)) {
                    System.out.print(EntityType.EMPTY.getSprite());
                } else {
                    System.out.print(renderSprite(x, y, worldMap));
                }
            }
        }
        System.out.println("\n");
    }

    private String renderSprite(int x, int y, WorldMap worldMap) {
        return worldMap.getEntity(x, y).getType().getSprite();
    }
}
