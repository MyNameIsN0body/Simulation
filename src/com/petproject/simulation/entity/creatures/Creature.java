package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.world.WorldMap;

public abstract class Creature extends Entity {
    protected int energy;
    protected int reproductionCooldown;
    protected int maxReproductionCooldown;

    public Creature() {
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

    public abstract void makeEat(WorldMap worldMap);

    public void soulHarvester(WorldMap worldMap) {
        this.setEnergy(energy - 1);
        checkDeath(worldMap);
    }
    protected void checkDeath(WorldMap worldMap) {
        if (this.energy <= 0) {
            worldMap.removeEntity(this);
        }
    }
}
