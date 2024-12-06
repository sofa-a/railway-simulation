package edu.curtin.app.models;

/*
 * Factory that creates the town objects.
 */

public class TownFactory {

    public Town createTown(String name, int population) {
        return new Town(name, population);
    }
}
