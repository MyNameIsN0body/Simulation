package com.petproject.simulation.world.customException;

public class NoEmptyCellsException extends RuntimeException{
    public NoEmptyCellsException(String message) {
        super(message);
    }
}
