package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.WorldMap;

public interface Hunter {
    void hunt(Creature creature, WorldMap worldMap);
}