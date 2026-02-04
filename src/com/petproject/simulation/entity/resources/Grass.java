package com.petproject.simulation.entity.resources;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;

public class Grass extends Entity {
    int heal = 3;

    public Grass() {
        super(EntityType.GRASS);
    }
}
