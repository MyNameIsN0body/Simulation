package com.petproject.simulation.entity.creatures;


public class HerbivoreReproduction extends  BaseReproduction {
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
    protected String getTargetType() {
        return "HERBIVORE";
    }

    @Override
    protected Creature createBabyCreature() {
        return new Herbivore();
    }

    @Override
    protected void postReproductionActions(Creature creature, Creature partner) {
        creature.setEnergy(creature.energy + 1);
        partner.setEnergy(partner.energy + 1);
    }
}
