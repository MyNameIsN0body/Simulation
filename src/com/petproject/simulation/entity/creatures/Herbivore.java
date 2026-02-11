package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;

public class Herbivore extends Creature {
    private final Reproduction reproduction;
    private final HerbivoreMove herbivoreMove;
    private final HerbivoreHunting herbivoreHunting;

    public Herbivore() {
        super(EntityType.HERBIVORE);
        this.energy = 9;
        this.reproductionCooldown = 0;
        this.herbivoreMove = new HerbivoreMove();
        this.reproduction = new HerbivoreReproduction();
        this.herbivoreHunting = new HerbivoreHunting();
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        herbivoreMove.move(this, worldMap);
        reproduction.updateCooldown(this);
        MoveService.soulHarvester(this);
    }

    @Override
    public void makeReproduce(WorldMap worldMap) {
        if (reproduction.canReproduce(this,worldMap)) {
            reproduction.reproduce(this, worldMap);
            MoveService.soulHarvester(this);
        }
    }

    @Override
    public void makeEat(WorldMap worldMap) {
        herbivoreHunting.hunt(this, worldMap);
        reproduction.updateCooldown(this);
        MoveService.soulHarvester(this);
    }
    private void die(WorldMap worldMap) {
        if (this.getEnergy() <= 0) {
            worldMap.getEntityCoordinate(this).isPresent();
        }
    }
}
