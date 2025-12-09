package com.petproject.simulation.render;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.world.Map;

public class MapConsoleRenderer {

    public void renderWorld(Map map) {

        for (int y = 0; y < map.getWorldWidth(); y++) {
            System.out.println(" ");
            for (int x = 0; x < map.getWorldLength(); x++) {
                if (map.isCellEmpty(x, y)) {
                    System.out.print("⬜"); //□ 🏼
                } else {
                    System.out.print(renderSprite(x, y, map));
                }
            }
        }

    }

    private boolean isEntity(int x, int y, Map map) {
        return map.getMap().containsKey(new Coordinates(x, y));
    }


    private String renderSprite(int x, int y, Map map) {

        return ConsoleSprite.getSprite(map.getEntityType(x, y));
    }
}
