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

import java.util.Optional;

public class InitAction implements Action {
    private final int[] counts = new int[EntitySprite.values().length - 1];  //без empty
    private final EntityFactory[] factories = new EntityFactory[EntitySprite.values().length - 1];  //без empty

    private interface EntityFactory {
        Entity create();
    }

    public InitAction(int grassCount, int rockCount, int treeCount, int herbivoreCount, int predatorCount) {
        counts[EntitySprite.GRASS.ordinal()] = grassCount;
        counts[EntitySprite.ROCK.ordinal()] = rockCount;
        counts[EntitySprite.TREE.ordinal()] = treeCount;
        counts[EntitySprite.HERBIVORE.ordinal()] = herbivoreCount;
        counts[EntitySprite.PREDATOR.ordinal()] = predatorCount;

        factories[EntitySprite.GRASS.ordinal()] = Grass::new;
        factories[EntitySprite.ROCK.ordinal()] = Rock::new;
        factories[EntitySprite.TREE.ordinal()] = Tree::new;
        factories[EntitySprite.HERBIVORE.ordinal()] = Herbivore::new;
        factories[EntitySprite.PREDATOR.ordinal()] = Predator::new;
    }

    @Override
    public void execute(WorldMap worldMap) {
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            EntityFactory entityFactory = factories[i];
            if (count > 0 && entityFactory != null) {
                for (int j = 0; j < count; j++) {
                    Optional<Coordinates> optionalCoordinates = worldMap.getRandomEmptyCoordinates();
                    if (optionalCoordinates.isEmpty()) {
                        break;
                    }
                    Coordinates coordinates = optionalCoordinates.get();
                    Entity entity = entityFactory.create();
                    worldMap.putEntity(coordinates, entity);
                    //Coordinates coordinates = worldMap.getRandomEmptyCoordinates();
//                    if (coordinates != null) {
//                        Entity entity = entityFactory.create();
//                        worldMap.setEntity(coordinates, entity);
//                    } else {
//                        break;
//                    }
                }
            }
        }
    }
}