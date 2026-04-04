package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;

public class ReproduceAction implements Action {

    @Override
    public void execute(WorldMap worldMap) {

        for (Entity entity : worldMap.getAllEntities()) {
            if (entity instanceof Creature creature) {
                creature.makeReproduce(worldMap);
            }
        }
    }
}

