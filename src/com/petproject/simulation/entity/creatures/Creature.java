package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.world.WorldMap;

public abstract class Creature extends Entity {
    protected int energy;
    protected int reproductionCooldown;
    protected int maxReproductionCooldown;

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
    protected boolean isDead () {
        return getEnergy() <= 0;
    }
    public Creature(EntityType type) {
        super(type);
    }

    public abstract void makeMove(WorldMap worldMap);

    public abstract void makeReproduce(WorldMap worldMap);

    public abstract void makeEat(WorldMap worldMap);


}
