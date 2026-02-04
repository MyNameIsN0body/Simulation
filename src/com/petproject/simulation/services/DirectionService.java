package com.petproject.simulation.services;

import com.petproject.simulation.entity.Coordinates;

import java.util.Random;

public class DirectionService {
    private static final Random RANDOM = new Random();
    public static final int[] DX = {-1, 0, 1, 0, -1, -1, 1, 1};
    public static final int[] DY = {0, 1, 0, -1, -1, 1, -1, 1};
    public static final int DIRECTION_COUNT = DX.length;
    private DirectionService() {}

    public static Coordinates calculateNewPosition(Coordinates current, int direction) {
        return new Coordinates(
                current.getX() + DX[direction],
                current.getY() + DY[direction]
        );
    }

    public static int[] getShuffledDirections() {
        int[] directions = new int[DIRECTION_COUNT];
        for (int i = 0; i < directions.length; i++) {
            directions[i] = i;
        }
        for (int i = 0; i < directions.length; i++) {
            int j = RANDOM.nextInt(i + 1);
            int temp = directions[i];
            directions[i] = directions[j];
            directions[j] = temp;
        }
        return directions;
    }

    public static boolean isValidDirection(int direction) {
        return direction >= 0 && direction < DIRECTION_COUNT;
    }
}
