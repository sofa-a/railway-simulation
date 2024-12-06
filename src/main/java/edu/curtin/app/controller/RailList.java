package edu.curtin.app.controller;

/*
 * Container for the Railway objects.
 * Is one of the subjects in the observer pattern that has been implemented
 * Any changes to the list, and it will notify the FileManager and ConsoleOutput objects.
 */


import edu.curtin.app.models.*;
import java.util.*;
import java.util.logging.Logger;

public class RailList {
    private static final Logger logger =
            Logger.getLogger(RailList.class.getName());
    private List<Railway> railways;
    private Set<RailObserver> observers;

    public RailList() {
        railways = new ArrayList<>();
        observers = new HashSet<>();
    }

    /** add observers to the observer set*/
    public void addObserver(RailObserver obs) {
        observers.add(obs);
    }

    /** notify all the observers */
    public void notifyObservers() {
        logger.finer("observers are being notified");
        for(RailObserver obs : observers) {
            obs.updateRails(this.railways);
        }
    }

    /** Add a railway to the list */
    public void addRailway(Railway newRailway) {
        logger.finest("railway is being added");
        railways.add(newRailway);
        notifyObservers();
    }

    /** Construct a double railway from a single railway */
    public void constructRailway(String townA, String townB) {
        for (Railway railway : railways) {
            /* check if a railway exists between the two towns */
            String nameA = railway.getTownA().getTownName();
            String nameB = railway.getTownB().getTownName();
            if (townA.equals(nameA) && townB.equals(nameB)) {
                logger.fine("railway townA is equal to townA provided");
                /* call construction on it there is a railway
                * if one is under construction, the state logic will handle it*/
                railway.construction();
                notifyObservers();
            }
            else if (townA.equals(nameB) && townB.equals(nameA)) {
                logger.fine("railway townA is equal to townB provided");
                /* read comment above - same thing happening but allowing for if towns are in
                * different order (used separate town variables instead of a list)*/
                railway.construction();
                notifyObservers();
            }
        }
    }

    /** Get a copy of the railway list */
    public List<Railway> getRailways() {
        return Collections.unmodifiableList(railways);
    }
}
