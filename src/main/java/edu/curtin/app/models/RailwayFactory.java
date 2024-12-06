package edu.curtin.app.models;

/*
 * Factory that creates the railway objects.
 */

public class RailwayFactory {
    public Railway createRailway(Town townA, Town townB) {
        return new Railway(townA, townB);
    }
}
