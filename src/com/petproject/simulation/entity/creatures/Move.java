package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.WorldMap;


public interface Move {
    void move(Creature creature, WorldMap worldMap);

}
