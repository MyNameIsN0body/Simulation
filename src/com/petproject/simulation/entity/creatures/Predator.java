package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.creatures.movement.PredatorMovable;
import com.petproject.simulation.entity.creatures.reproduction.PredatorReproducible;
import com.petproject.simulation.entity.creatures.reproduction.Reproducible;
import com.petproject.simulation.simulation.GameStats;
import com.petproject.simulation.world.WorldMap;


public class Predator extends Creature {
    private final Reproducible reproducible;
    private final PredatorMovable predatorMove;
    private static final int DEFAULT_ENERGY = 10;

    public Predator() {
        setEnergy(DEFAULT_ENERGY);
        this.predatorMove = new PredatorMovable();
        this.reproducible = new PredatorReproducible();
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        predatorMove.move(this, worldMap);
        soulHarvester(worldMap);
    }

    @Override
    public void makeReproduce(WorldMap worldMap) {
        if (reproducible.canReproduce(this,worldMap)) {
            reproducible.reproduce(this, worldMap);
            soulHarvester(worldMap);
        }
    }

    @Override
    public GameStats updateStats(GameStats stats) {
        return stats.incrementPredators();
    }

}
