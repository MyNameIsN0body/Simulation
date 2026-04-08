package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.EntitySprite;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;


public class Predator extends Creature {
    private final Reproducible reproducible;
    private final PredatorMovable predatorMove;
    private final PredatorHunter predatorHunting;

    public Predator() {
        this.energy = 10;
        this.reproductionCooldown = 0;
        this.predatorMove = new PredatorMovable();
        this.reproducible = new PredatorReproducible();
        this.predatorHunting = new PredatorHunter();
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        predatorMove.move(this, worldMap);
        this.soulHarvester();
        if (this.getEnergy() <= 0) {
            worldMap.removeEntity(this);
        }
    }

    @Override
    public void makeReproduce(WorldMap worldMap) {
        if (reproducible.canReproduce(this,worldMap)) {
            reproducible.reproduce(this, worldMap);
            this.soulHarvester();
            if (this.getEnergy() <= 0) {
                worldMap.removeEntity(this);
            }
        }
    }

    @Override
    public void makeEat(WorldMap worldMap) {
        predatorHunting.hunt(this,worldMap);
        this.soulHarvester();
    }

}
