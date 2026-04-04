package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;

public class MoveAction implements Action {

    @Override
    public void execute(WorldMap worldMap) {

        for (Entity entity : worldMap.getAllEntities()) {
            if (entity instanceof Creature creature) {
                creature.makeMove(worldMap);
            }
        }

    }
}
