package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;

public abstract class Creature extends Entity {
    private int energy;
    protected int reproductionCooldown;
    protected int maxReproductionCooldown;

    public Creature() {
    }

    public void startReproductionCooldown(int cooldown) {
        resetReproductionCooldown(cooldown);
    }

    public int getReproductionCooldown() {
        return reproductionCooldown;
    }

    public void reduceReproductionCooldown() {
        if (reproductionCooldown > 0) {
            reproductionCooldown--;
        }
    }

    protected void resetReproductionCooldown(int maxCooldown) {
        this.maxReproductionCooldown = maxCooldown;
        this.reproductionCooldown = maxCooldown;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public abstract void makeMove(WorldMap worldMap);

    public abstract void makeReproduce(WorldMap worldMap);

    public void soulHarvester(WorldMap worldMap) {
        this.setEnergy(getEnergy() - 1);
        checkDeath(worldMap);
    }
    protected void checkDeath(WorldMap worldMap) {
        if (this.energy <= 0) {
            worldMap.removeEntity(this);
        }
    }
}
