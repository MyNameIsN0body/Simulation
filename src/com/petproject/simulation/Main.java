package com.petproject.simulation;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Herbivore;
import com.petproject.simulation.entity.creatures.Predator;
import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.entity.resources.Rock;
import com.petproject.simulation.entity.resources.Tree;
import com.petproject.simulation.render.MapConsoleRenderer;
import com.petproject.simulation.simulation.Simulation;
import com.petproject.simulation.world.Map;

public class Main {
    public static void main(String[] args) {
//        Map map = new Map(9,9);
//
//        Entity rock = new Rock(new Coordinates(2,2));
//        Entity tiger = new Predator(new Coordinates(1,1));
//        Entity grass = new Grass(new Coordinates(1,3));
//        Entity tree = new Tree(new Coordinates(6,7));
//        Entity sheep = new Herbivore(new Coordinates(5,7));
//
//        map.setEntity(new Coordinates(2,2), rock);
//        map.setEntity(new Coordinates(1,2), tiger);
//        map.setEntity(new Coordinates(2,3), grass);
//        map.setEntity(new Coordinates(6,7), tree);
//        map.setEntity(new Coordinates(5,8), sheep);
//
//        MapConsoleRenderer mapConsoleRenderer = new MapConsoleRenderer();
//        mapConsoleRenderer.renderWorld(map);
        Simulation sim = new Simulation(15, 15);
        sim.initialize();

//        for (int i = 0; i < 10; i++) {
//            sim.nextTurn();
//            render(sim.getMap());
//            Thread.sleep(1000);
//        }
    }
}