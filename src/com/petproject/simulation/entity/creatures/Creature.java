package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;

public abstract class Creature extends Entity {
    public Creature(Coordinates coordinates) {
        super(coordinates);
    }

}
