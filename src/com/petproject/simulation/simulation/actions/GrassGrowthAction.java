package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public class GrassGrowthAction implements Action {

    @Override
    public void execute(WorldMap worldMap) {
        Optional<Coordinates> optionalCoordinates = worldMap.getRandomEmptyCoordinates();
        if (optionalCoordinates.isEmpty()) {
            return;
        }
        Coordinates randomEmptyCoordinates = optionalCoordinates.get();
        worldMap.putEntity(randomEmptyCoordinates, new  Grass());
    }
}
