package com.petproject.simulation.entity.resources;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.entity.creatures.Herbivore;

public class Grass extends Entity {
    public Grass() {
    }

    @Override
    public boolean canBeEatenBy(Creature creature) {
        return creature instanceof Herbivore;
    }

    @Override
    public boolean canBeEnteredBy(Entity entity) {
        return entity instanceof Herbivore;
    }
}
