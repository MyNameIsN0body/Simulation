package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Entity;

public class PredatorReproducible extends BaseReproducible {
    private static final int COOLDOWN = 4;
    private static final int MIN_ENERGY = 6;

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
        return Predator.class;
    }

    @Override
    protected Creature createBabyCreature() {
        return new Predator();
    }

    @Override
    protected void postReproductionActions(Creature creature, Creature partner) {
        creature.setEnergy(creature.getEnergy() - 1);
        partner.setEnergy(partner.getEnergy() - 1);
    }
}
