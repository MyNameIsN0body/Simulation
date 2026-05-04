package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.simulation.GameStats;
import com.petproject.simulation.world.WorldMap;

public class Herbivore extends Creature {
    private final Reproducible reproducible;
    private final HerbivoreMovable herbivoreMove;

    @Override
    public boolean canBeEatenBy(Creature creature) {
        return creature instanceof Predator;
    }

    @Override
    public boolean canBeEnteredBy(Entity entity) {
        return entity instanceof Predator;
    }

    public Herbivore() {
        this.energy = 9;
        this.reproductionCooldown = 0;
        this.herbivoreMove = new HerbivoreMovable();
        this.reproducible = new HerbivoreReproducible();
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        herbivoreMove.move(this, worldMap);
        reproducible.updateCooldown(this);
        soulHarvester(worldMap);
    }

    @Override
    public void makeReproduce(WorldMap worldMap) {
        if (reproducible.canReproduce(this, worldMap)) {
            reproducible.reproduce(this, worldMap);
            soulHarvester(worldMap);
        }
    }

    public GameStats updateStats(GameStats stats) {
        return stats.incrementHerbivore();
    }
}
