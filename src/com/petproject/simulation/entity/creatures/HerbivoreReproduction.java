package com.petproject.simulation.entity.creatures;


public class HerbivoreReproduction extends  BaseReproduction {
    private static final int COOLDOWN = 7;
    private static final int MIN_HEALTH = 5;
    private static final int MIN_SATIETY = 5;

    @Override
    protected int getCooldown() {
        return COOLDOWN;
    }

    @Override
    protected int getMinHealth() {
        return MIN_HEALTH;
    }

    @Override
    protected int getMinSatiety() {
        return MIN_SATIETY;
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
        creature.setHealthPoint(creature.getHealthPoint() + 1);
        partner.setHealthPoint(partner.getHealthPoint() + 1);
    }
}
