package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.EntityType;

public class PredatorReproduction extends BaseReproduction {
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
    protected EntityType getTargetType() {
        return EntityType.PREDATOR;
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
