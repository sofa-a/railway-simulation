package edu.curtin.app.models;

/*
 * Observer interface for updating information relating to towns.
 */

import java.util.Map;

public interface TownObserver {
    void updateTowns(Map<String, Town> townMap);
}
