package com.petproject.simulation.simulation.actions;

import com.petproject.simulation.entity.Coordinates;
import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntityType;
import com.petproject.simulation.entity.creatures.Herbivore;
import com.petproject.simulation.entity.creatures.Predator;
import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.entity.resources.Rock;
import com.petproject.simulation.entity.resources.Tree;
import com.petproject.simulation.world.WorldMap;

public class InitAction implements Actions {
    private final int[] counts = new int[EntityType.values().length - 1];  //без empty
    private final EntityFactory[] factories = new EntityFactory[EntityType.values().length - 1];  //без empty

    private interface EntityFactory {
        Entity create();
    }

    public InitAction(int grassCount, int rockCount, int treeCount, int herbivoreCount, int predatorCount) {
        counts[EntityType.GRASS.ordinal()] = grassCount;
        counts[EntityType.ROCK.ordinal()] = rockCount;
        counts[EntityType.TREE.ordinal()] = treeCount;
        counts[EntityType.HERBIVORE.ordinal()] = herbivoreCount;
        counts[EntityType.PREDATOR.ordinal()] = predatorCount;

        factories[EntityType.GRASS.ordinal()] = Grass::new;
        factories[EntityType.ROCK.ordinal()] = Rock::new;
        factories[EntityType.TREE.ordinal()] = Tree::new;
        factories[EntityType.HERBIVORE.ordinal()] = Herbivore::new;
        factories[EntityType.PREDATOR.ordinal()] = Predator::new;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            EntityFactory entityFactory = factories[i];
            if (count > 0 && entityFactory != null) {
                for (int j = 0; j < count; j++) {
                    Coordinates coordinates = worldMap.getRandomEmptyCoordinates();
                    if (coordinates != null) {
                        Entity entity = entityFactory.create();
                        worldMap.setEntity(coordinates, entity);
                    } else {
                        break;
                    }
                }
            }
        }
    }
}