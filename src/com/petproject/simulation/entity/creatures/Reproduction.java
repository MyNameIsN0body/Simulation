package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.WorldMap;

public interface Reproduction {
    boolean canReproduce(Creature creature, WorldMap worldMap);
    void reproduce(Creature creature, WorldMap worldMap);
    void updateCooldown(Creature creature);
}
