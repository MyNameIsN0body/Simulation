package com.petproject.simulation.render;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.EntitySprite;
import com.petproject.simulation.world.WorldMap;

public class MapConsoleRenderer {
    private static final int SPRITE_WIDTH = 2;
    private static final int INDENT_OUT = 3;
    private static final int INDENT_IN = 1;

    public void renderWorld(WorldMap worldMap) {
        int countSprite = worldMap.getWidth();
        printBorderLine('╔', '╗', countSprite);
        for (int y = 0; y < countSprite; y++) {
            System.out.print(" ".repeat(INDENT_OUT) + "\u001B[36m║" + " ".repeat(INDENT_IN) + "\u001B[0m");
            for (int x = 0; x < countSprite; x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (worldMap.isCellEmpty(coordinates)) {
                    System.out.print(EntitySprite.EMPTY.getSprite());
                } else {
                    System.out.print(renderSprite(coordinates, worldMap));
                }
            }
            System.out.println("\u001B[36m" + " ".repeat(INDENT_IN) + "║\u001B[0m");
        }
        printBorderLine('╚', '╝', countSprite);
    }

    private void printBorderLine(char leftChar, char rightChar, int countSprite) {
        int leftBorder = 1;
        int leftPadding = INDENT_IN;
        int rightPadding = INDENT_IN;
        int rightBorder = 1;
        int spritesTotal = countSprite * SPRITE_WIDTH;
        int curvature = 1;
        int innerWidth = leftBorder + leftPadding + spritesTotal + rightPadding + rightBorder + curvature;

        System.out.print(" ".repeat(INDENT_OUT) + "\u001B[36m" + leftChar);
        System.out.print("═".repeat(innerWidth));
        System.out.println(rightChar + "\u001B[0m");
    }

    private String renderSprite(Coordinates coordinates, WorldMap worldMap) {
        return EntityRenderer.render(worldMap.getEntity(coordinates).orElseThrow());
    }
}
