package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.services.CellFinderService;
import com.petproject.simulation.world.WorldMap;
import com.petproject.simulation.world.pathfinding.BFSPathfinder;

import java.util.List;
import java.util.Optional;

public abstract class BaseReproduction implements Reproduction {

    protected abstract int getCooldown();
    protected abstract int getMinHealth();
    protected abstract int getMinSatiety();
    protected abstract String getTargetType();
    protected abstract Creature createBabyCreature();
    protected abstract void postReproductionActions(Creature creature, Creature partner);

    @Override
    public boolean canReproduce(Creature creature, WorldMap worldMap) {
        boolean isCooldownReady = creature.getReproductionCooldown() <= 0;
        boolean isEnoughHealth = creature.getHealthPoint() >= getMinHealth();
        boolean isEnoughSatiety = creature.getSatiety() >= getMinSatiety();
        boolean hasPartner = findPartner(creature, worldMap, getTargetType()).isPresent();

        return isCooldownReady && isEnoughHealth && isEnoughSatiety && hasPartner;
    }

    @Override
    public void reproduce (Creature creature, WorldMap worldMap) {
        Optional<Creature> partnerOptional = findPartner(creature, worldMap, getTargetType());
        if(partnerOptional.isEmpty()) {return;}
        Creature partner = partnerOptional.get();
        Coordinates babyPosition = CellFinderService.findEmptyCellNear(creature, worldMap);
        if (babyPosition == null) {
            babyPosition = CellFinderService.findEmptyCellNear(partner, worldMap);
        }
        if (babyPosition != null) {
            worldMap.setEntity(babyPosition, createBabyCreature());
            resetCooldowns(creature, partner);
            postReproductionActions(creature, partner);
        }
    }
    protected void resetCooldowns(Creature creature, Creature partner) {
        creature.resetReproductionCooldown(getCooldown());
        partner.resetReproductionCooldown(getCooldown());
    }

    @Override
    public void updateCooldown(Creature creature) {
        creature.reduceReproductionCooldown();
    }


    protected Optional<Creature> findPartner(Creature seeker, WorldMap worldMap, String targetType) {
        List<Coordinates> path = BFSPathfinder.findPathToNearest2(
                worldMap,
                seeker,
                targetType
        );

        if (!path.isEmpty() && path.size() > 1) {
            Coordinates partnerCoordinate = path.get(1);

            Entity entity = worldMap.getEntity(partnerCoordinate.getX(), partnerCoordinate.getY());

            if (entity instanceof Creature &&
                    entity.getType().toString().equals(targetType)) {
                return Optional.of((Creature) entity);
            }
        }

        return Optional.empty();
    }
}


