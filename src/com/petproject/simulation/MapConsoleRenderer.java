package com.petproject.simulation;

public class MapConsoleRenderer {

    public void renderWorld(Map map){

        for (int  y = 0; y < map.getWorldWidth() ; y++) {
            System.out.println(" ");
            for (int x = 0; x < map.getWorldLength(); x++) {
                if(isEntity(x,y,map)) {
//                    System.out.print("⛰");
//                    System.out.print("\uD83D\uDDFB");
//                    System.out.print("\uD83C\uDFD4\uFE0F");
//                    System.out.print("🐅");            // тигр
//                    System.out.print("\uD83E\uDEBF");   //гусь
//                      System.out.print("🌳");            // дерево
                    //System.out.print("\uD83E\uDEA8"); //  камень
                    // System.out.print("\uD83D\uDC11"); // овца
                    //System.out.print("\uD83C\uDF32");  // дерево
//                    System.out.print("🌱");  // трава
                    System.out.print(renderSprite(x, y, map));
                }else {
                    System.out.print("⬜"); //□ 🏼
                }
            }
        }
    }
    private boolean isEntity(int x, int y, Map map) {
        return map.getMap().containsKey(new Coordinates(x, y));
    }
    private String renderSprite(int x, int y, Map map) {
        return map.getMap().get(new Coordinates(x, y)).getSprite();
    }
}
