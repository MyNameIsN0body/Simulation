package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;


public class Predator extends Creature {
    private final Reproduction reproduction;
    private final PredatorMove predatorMove;
    private final PredatorHunting predatorHunting;

    public Predator() {
        super(EntityType.PREDATOR);
        this.energy = 10;
        this.reproductionCooldown = 0;
        this.predatorMove = new PredatorMove();
        this.reproduction = new PredatorReproduction();
        this.predatorHunting = new PredatorHunting();
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        predatorMove.move(this, worldMap);
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
        predatorHunting.hunt(this,worldMap);
        MoveService.soulHarvester(this);
    }

}
