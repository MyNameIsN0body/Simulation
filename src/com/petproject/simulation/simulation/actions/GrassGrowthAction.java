package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.world.WorldMap;

public class GrassGrowthAction implements Actions {

    @Override
    public void execute(WorldMap worldMap) {
        Coordinates randomEmptyCoordinates = worldMap.getRandomEmptyCoordinates();
        worldMap.setEntity(randomEmptyCoordinates, new Grass());
    }
}
