package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;


public class Predator extends Creature {
    private final Reproduction reproduction;

    public Predator() {
        super(EntityType.PREDATOR);
        this.healthPoint = 4;
        this.speed = 3;
        this.reproduction = new PredatorReproduction();
        this.reproductionCooldown = 0;
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        MoveService.moveTowardsHerbivore(worldMap, this);
    }

    @Override
    public void makeReproduce(WorldMap worldMap) {
        if (reproduction.canReproduce(this,worldMap)) {
            reproduction.reproduce(this, worldMap);
        }
    }

}
