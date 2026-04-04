package com.petproject.simulation.services;

import com.petproject.simulation.world.Coordinates;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public Coordinates move(Coordinates current) {
        return new Coordinates(
                current.x() + dx,
                current.y() + dy
        );
    }

    public static List<Direction> shuffled() {
        List<Direction> directions = new ArrayList<>(List.of(values()));
        Collections.shuffle(directions);
        return directions;
    }
}
