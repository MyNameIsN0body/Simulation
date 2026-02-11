package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Herbivore;
import com.petproject.simulation.entity.creatures.Predator;
import com.petproject.simulation.world.WorldMap;

import java.util.List;

public class EatAction implements Actions{
    @Override
    public void execute(WorldMap worldMap) {
        List<Entity> allEntities = worldMap.getAllEntities();
        for (Entity entity : allEntities) {
            switch (entity.getType()) {
                case HERBIVORE -> {
                    ((Herbivore) entity).makeEat(worldMap);
                }
                case PREDATOR -> {
                    ((Predator) entity).makeEat(worldMap);
                }
            }
        }
    }
}
