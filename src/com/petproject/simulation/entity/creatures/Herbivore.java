package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;

public class Herbivore extends Creature {
    private final Reproduction reproduction;

    public Herbivore() {
        super(EntityType.HERBIVORE);
        this.healthPoint = 10;
        this.speed = 2;
        this.satiety = 10;
        this.reproduction = new HerbivoreReproduction();
        this.reproductionCooldown = 4;
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        MoveService.moveTowardsGrass(worldMap, this);
        reproduction.updateCooldown(this);
    }

    @Override
    public void makeReproduce(WorldMap worldMap) {
        if (reproduction.canReproduce(this,worldMap)) {
            reproduction.reproduce(this, worldMap);
        }
    }

}
