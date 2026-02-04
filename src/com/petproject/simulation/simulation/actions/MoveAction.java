package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Herbivore;
import com.petproject.simulation.entity.creatures.Predator;
import com.petproject.simulation.world.WorldMap;

import java.util.List;

public class MoveAction implements Actions {

    @Override
    public void execute(WorldMap worldMap) {
        List<Entity> allEntities = worldMap.getAllEntities();
        for (Entity entity : allEntities) {
            switch (entity.getType()) {
                case HERBIVORE -> {
                    Herbivore herbivore = (Herbivore) entity;
                    herbivore.makeMove(worldMap);
                }
                case PREDATOR -> {
                    Predator predator = (Predator) entity;
                    predator.makeMove(worldMap);
                }
            }
        }
    }
}
