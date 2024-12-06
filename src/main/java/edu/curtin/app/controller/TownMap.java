package edu.curtin.app.controller;

/*
 * Container for the town objects.
 * Is one of the subjects in the observer pattern that has been implemented
 * Any changes to the map, and it will notify the FileManager and ConsoleOutput objects.
 */

import edu.curtin.app.models.*;
import java.util.*;
import java.util.logging.Logger;

public class TownMap {
    private static final Logger logger =
            Logger.getLogger(TownMap.class.getName());
    private Map<String, Town> towns;
    private Set<TownObserver> observers;

    public TownMap() {
        towns = new HashMap<>();
        observers = new HashSet<>();
    }

    /** add observers to the observer set*/
    public void addObserver(TownObserver obs) {
        observers.add(obs);
    }

    /** notify all the observers */
    public void notifyObservers() {
        logger.finest("notifying all observers.");
        for(TownObserver obs : observers) {
            obs.updateTowns(this.towns);
        }
    }

    /** update the state of the railway between the towns */
    public void updateRelationships(Town townA, Town townB, String relationship) {
        /* uses town object passed in to replace the old town object in the towns map */
        logger.fine("updating all the relationships of all the town.");
        townA.addRailway(townB.getTownName(), relationship);
        towns.put(townA.getTownName(), townA);
        notifyObservers();
    }

    /** Add a town to the map */
    public void addTown(Town newTown) {
        logger.finest("adding towns to the map.");
        towns.put(newTown.getTownName(), newTown);
        notifyObservers();
    }

    /** Get a town object */
    public Town getTown(String townName) {
        logger.config("retrieving a specific town");
        return towns.get(townName);
    }

    /** updates the population of the town */
    public void updateTownPop(String townName, int population) {
        /* creates town object to replace the old town object in the towns map */
        logger.config("updating the town population of a specific town.");
        Town updatedTown = towns.get(townName);
        updatedTown.setPopulation(population);
        towns.replace(townName, updatedTown);
        notifyObservers();
    }

    /** updates the stockpile of the town */
    public void updateTownStock(String townName, int stockpile) {
        /* creates town object to replace the old town object in the towns map */
        logger.fine("updating the town stockpile of a specific town.");
        Town updatedTown = towns.get(townName);
        updatedTown.setStockpile(stockpile);
        towns.replace(townName, updatedTown);
        notifyObservers();
    }

    /** Get a copy of the towns map */
    public Map<String, Town> getTowns() {
        return Collections.unmodifiableMap(this.towns);
    }
}
