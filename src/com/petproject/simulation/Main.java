package com.petproject.simulation;

public class Main {
    public static void main(String[] args) {
        Map map = new Map(9,9);

        Entity rock = new Rock(new Coordinates(2,2));
        Entity tiger = new Predator(new Coordinates(1,1));
        Entity grass = new Grass(new Coordinates(1,3));
        Entity tree = new Tree(new Coordinates(6,7));
        Entity sheep = new Herbivore(new Coordinates(5,7));

        map.setEntity(rock.coordinates, rock);
        map.setEntity(tiger.coordinates, tiger);
        map.setEntity(grass.coordinates, grass);
        map.setEntity(tree.coordinates, tree);
        map.setEntity(sheep.coordinates, sheep);

        MapConsoleRenderer mapConsoleRenderer = new MapConsoleRenderer();
        mapConsoleRenderer.renderWorld(map);

    }
}