package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.world.WorldMap;

public abstract class Creature extends Entity {
    protected int healthPoint;
    protected int speed;
    protected int satiety;
    protected Reproduction reproduction;
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

    public int getSatiety() {
        return satiety;
    }


    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public Creature(EntityType type) {
        super(type);
    }

    public abstract void makeMove(WorldMap worldMap);
    public abstract void makeReproduce(WorldMap worldMap);

    public void setSatiety(int i) {

    }
}
