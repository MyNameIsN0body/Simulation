package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.WorldMap;

public interface Hunting {
    void hunt(Creature creature, WorldMap worldMap);
}