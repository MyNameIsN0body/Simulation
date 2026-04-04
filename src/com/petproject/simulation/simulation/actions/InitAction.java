package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.world.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntitySprite;
import com.petproject.simulation.entity.creatures.Herbivore;
import com.petproject.simulation.entity.creatures.Predator;
import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.entity.resources.Rock;
import com.petproject.simulation.entity.resources.Tree;
import com.petproject.simulation.world.WorldMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class InitAction implements Action {
    private final int[] counts = new int[EntitySprite.values().length - 1];  //без empty
    private final Map<Supplier<Entity>, Integer> entityCounts = new LinkedHashMap<>();

    public InitAction(int grassCount, int rockCount, int treeCount, int herbivoreCount, int predatorCount) {
        entityCounts.put(Grass::new, grassCount);
        entityCounts.put(Rock::new, rockCount);
        entityCounts.put(Tree::new, treeCount);
        entityCounts.put(Herbivore::new, herbivoreCount);
        entityCounts.put(Predator::new, predatorCount);
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (Map.Entry<Supplier<Entity>, Integer> entry : entityCounts.entrySet()) {
            Supplier<Entity> factory = entry.getKey();
            int count = entry.getValue();

            for (int i = 0; i < count; i++) {
                Optional<Coordinates> optionalCoordinates = worldMap.getRandomEmptyCoordinates();
                if (optionalCoordinates.isEmpty()) {
                    break;
                }
                Coordinates coordinates = optionalCoordinates.get();
                Entity entity = factory.get();
                worldMap.putEntity(coordinates, entity);
            }
        }
    }
}