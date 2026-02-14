package com.petproject.simulation.render;

import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.world.WorldMap;

public class MapConsoleRenderer {
    private static final int SPRITE_WIDTH = 2;
    private static final int INDENT_OUT = 3;
    private static final int INDENT_IN = 1;
    final static String RED = "\u001B[31m";

    public void renderWorld(WorldMap worldMap) {
        int countSprite = worldMap.getWorldWidth();
        printBorderLine('╔', '╗', countSprite);
        for (int y = 0; y < countSprite; y++) {
            System.out.print(" ".repeat(INDENT_OUT) + "\u001B[36m║" + " ".repeat(INDENT_IN) + "\u001B[0m");
            for (int x = 0; x < countSprite; x++) {
                if (worldMap.isCellEmpty(x, y)) {
                    System.out.print(EntityType.EMPTY.getSprite());
                } else {
                    System.out.print(renderSprite(x, y, worldMap));
                }
            }
            System.out.println("\u001B[36m" + " ".repeat(INDENT_IN) + "║\u001B[0m");
        }
        printBorderLine('╚', '╝', countSprite);
//        System.out.println("\n");
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

    private String renderSprite(int x, int y, WorldMap worldMap) {
        return worldMap.getEntity(x, y).getType().getSprite();
    }
}
