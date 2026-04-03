package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.world.WorldMap;


public interface Movable {
    void move(Creature creature, WorldMap worldMap);

}
