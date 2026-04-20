package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class MoveAction implements Action {

    @Override
    public void execute(WorldMap worldMap) {
        List<Entity> entities = new ArrayList<>(worldMap.getAllEntities());

        for (Entity entity : entities) {
            if (entity instanceof Creature creature) {
                creature.makeMove(worldMap);
            }
        }

    }
}
