package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Herbivore;
import com.petproject.simulation.entity.creatures.Predator;
import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.entity.resources.Rock;
import com.petproject.simulation.entity.resources.Tree;
import com.petproject.simulation.render.MapConsoleRenderer;
import com.petproject.simulation.world.Map;

import java.util.Random;

public class InitAction implements Actions {
    private final Map map;


    private final int initialGrassCount;
    private final int initialRockCount;
    private final int initialTreeCount;
    private final int initialHerbivoreCount;
    private final int initialPredatorCount;

    public InitAction(Map map, int grassCount, int rockCount, int treeCount,
                      int herbivoreCount, int predatorCount) {
        this.map = map;

        this.initialGrassCount = grassCount;
        this.initialRockCount = rockCount;
        this.initialTreeCount = treeCount;
        this.initialHerbivoreCount = herbivoreCount;
        this.initialPredatorCount = predatorCount;
    }


    @Override
    public void execute() {
        //grass
        for (int i = 0; i < initialGrassCount; i++) {
            Coordinates coordinates = map.getRandomEmptyCoordinates();
            Entity grass = new Grass(coordinates);
            map.setEntity(coordinates, grass);
        }
        //Rock
        for (int i = 0; i < initialRockCount; i++) {
            Coordinates coordinates = map.getRandomEmptyCoordinates();
            Entity rock = new Rock(coordinates);
            map.setEntity(coordinates, rock);
        }
        //Tree
        for (int i = 0; i < initialTreeCount; i++) {
            Coordinates coordinates = map.getRandomEmptyCoordinates();
            Entity tree = new Tree(coordinates);
            map.setEntity(coordinates, tree);
        }
        //Herbivore
        for (int i = 0; i < initialHerbivoreCount; i++) {
            Coordinates coordinates = map.getRandomEmptyCoordinates();
            Entity herbivore = new Herbivore(coordinates);
            map.setEntity(coordinates, herbivore);
        }
        //Predator
        for (int i = 0; i < initialPredatorCount; i++) {
            Coordinates coordinates = map.getRandomEmptyCoordinates();
            Entity predator = new Predator(coordinates);
            map.setEntity(coordinates, predator);
        }
        MapConsoleRenderer mapConsoleRenderer = new MapConsoleRenderer();
        mapConsoleRenderer.renderWorld(map);
    }
}
