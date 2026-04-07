package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;

public class Herbivore extends Creature {
    private final Reproducible reproducible;
    private final HerbivoreMovable herbivoreMove;
    private final HerbivoreHunter herbivoreHunting;

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
        this.herbivoreHunting = new HerbivoreHunter();
    }

    @Override
    public void makeMove(WorldMap worldMap) {
        herbivoreMove.move(this, worldMap);
        reproducible.updateCooldown(this);
        MoveService.soulHarvester(this);
    }

    @Override
    public void makeReproduce(WorldMap worldMap) {
        if (reproducible.canReproduce(this,worldMap)) {
            reproducible.reproduce(this, worldMap);
            MoveService.soulHarvester(this);
        }
    }

    @Override
    public void makeEat(WorldMap worldMap) {
        herbivoreHunting.hunt(this, worldMap);
        reproducible.updateCooldown(this);
        MoveService.soulHarvester(this);
    }
}
