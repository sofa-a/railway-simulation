package edu.curtin.app.models;

public interface RailwayState {
    void simulateDay(Railway railway, Town townA, Town townB);
    void construction(Railway railway);
}
