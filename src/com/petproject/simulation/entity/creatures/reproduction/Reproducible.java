package com.petproject.simulation.entity.creatures.reproduction;

import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.world.WorldMap;

public interface Reproducible {
    boolean canReproduce(Creature creature, WorldMap worldMap);
    void reproduce(Creature creature, WorldMap worldMap);
    void updateCooldown(Creature creature);
}
