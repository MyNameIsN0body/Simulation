package com.petproject.simulation.entity.creatures;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.services.FinderService;
import com.petproject.simulation.services.MoveService;
import com.petproject.simulation.world.WorldMap;

import java.util.Optional;

public abstract class BaseHunting implements Hunting {

    // Абстрактные методы для реализации в подклассах
    protected abstract EntityType getTargetType();
    protected abstract int getEnergyGain();
    protected abstract boolean canEatTarget(Entity target);
    protected abstract void onEatTarget(Creature creature, Coordinates targetCoordinate, WorldMap worldMap);


    @Override
    public void hunt(Creature creature, WorldMap worldMap) {
        Optional<Coordinates> targetStep = FinderService.findTarget(creature, worldMap, getTargetType());

        if (targetStep.isPresent()) {
            moveToTarget(creature, targetStep.get(), worldMap);
        } else {
            MoveService.moveRandomly(creature, worldMap);
        }
    }

    protected void moveToTarget(Creature creature, Coordinates targetStep, WorldMap worldMap) {
        Optional<Coordinates> currentPos = worldMap.getEntityCoordinate(creature);
        if (currentPos.isEmpty()) {
            return;
        }

        Optional<Entity> target = worldMap.getEntity(targetStep);

        if (target.isEmpty()) {
            // Просто двигаемся в пустую клетку
            MoveService.moveCreature(creature, targetStep, worldMap);
        } else if (canEatTarget(target.orElse(null))) {
            // Съедаем цель
            onEatTarget(creature, targetStep, worldMap);
            creature.setEnergy(creature.getEnergy() + getEnergyGain());
        } else {
            // Цель не съедобна
            MoveService.moveRandomly(creature, worldMap);
        }
    }
}