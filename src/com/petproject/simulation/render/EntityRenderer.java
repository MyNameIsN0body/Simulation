package com.petproject.simulation.render;

import com.petproject.simulation.entity.Entity;
import com.petproject.simulation.entity.EntitySprite;
import com.petproject.simulation.entity.creatures.Herbivore;
import com.petproject.simulation.entity.creatures.Predator;
import com.petproject.simulation.entity.resources.Grass;
import com.petproject.simulation.entity.resources.Rock;
import com.petproject.simulation.entity.resources.Tree;

public class EntityRenderer {
    public static String render(Entity entity) {
        if (entity instanceof Predator) {
            return EntitySprite.PREDATOR.getSprite();
        }
        if (entity instanceof Herbivore) {
            return EntitySprite.HERBIVORE.getSprite();
        }
        if (entity instanceof Grass) {
            return EntitySprite.GRASS.getSprite();
        }
        if (entity instanceof Rock) {
            return EntitySprite.ROCK.getSprite();
        }
        if (entity instanceof Tree) {
            return EntitySprite.TREE.getSprite();
        }
        return EntitySprite.EMPTY.getSprite();
    }
}
