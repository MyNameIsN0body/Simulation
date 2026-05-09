package com.petproject.simulation.entity.creatures.reproduction;


import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.creatures.Creature;
import com.petproject.simulation.entity.creatures.Herbivore;

public class HerbivoreReproducible extends BaseReproducible {
    private static final int COOLDOWN = 3;
    private static final int MIN_ENERGY = 3;

    @Override
    protected int getCooldown() {
        return COOLDOWN;
    }

    @Override
    protected int getMinEnergy() {
        return MIN_ENERGY;
    }

    @Override
    protected Class<? extends Entity> getTargetType() {
        return Herbivore.class;
    }

    @Override
    protected Creature createBabyCreature() {
        return new Herbivore();
    }

    @Override
    protected void applyReproductionCost(Creature creature, Creature partner) {
        creature.setEnergy(creature.getEnergy() - 1);
        partner.setEnergy(partner.getEnergy() - 1);
    }
}
