package com.petproject.simulation.world;

public record Coordinates(int x, int y) {

    @Override
    public String toString() {
        return String.format("{%d:%d}", x, y);
    }
}