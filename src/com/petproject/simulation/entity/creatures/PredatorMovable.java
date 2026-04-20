package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;


public class PredatorMovable extends BaseMovable {
    private static final int ENERGY_FROM_HERBIVORE = 5;
    private static final int ENERGY_COST_ON_BLOCK = 2;

    @Override
    protected void interact(Creature creature, Coordinates targetCoordinate, WorldMap worldMap) {
        worldMap.removeEntity(targetCoordinate);
        relocate(creature, targetCoordinate, worldMap);

        creature.setEnergy(creature.getEnergy() + ENERGY_FROM_HERBIVORE);
    }

    @Override
    protected Optional<Coordinates> findStep(Creature creature, WorldMap worldMap) {
        return FinderService.findTarget(creature, worldMap, Herbivore.class);
    }

    @Override
    protected void onBlocked(Creature creature, WorldMap worldMap) {
        moveRandomly(creature, worldMap);
        creature.setEnergy(creature.getEnergy() - ENERGY_COST_ON_BLOCK);
    }

}
