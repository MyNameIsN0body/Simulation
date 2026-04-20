package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public class HerbivoreMovable extends BaseMovable {
    private static final int ENERGY_FROM_GRASS = 3;

    @Override
    protected Optional<Coordinates> findStep(Creature creature, WorldMap worldMap) {
        return FinderService.findTarget(creature, worldMap, Grass.class);
    }

    @Override
    protected void interact(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
        relocate(creature, targetCoordinate, worldMap);

        creature.setEnergy(creature.getEnergy() + ENERGY_FROM_GRASS);
    }

    @Override
    protected void onBlocked(Creature creature, WorldMap worldMap) {
        moveRandomly(creature, worldMap);
    }

}

