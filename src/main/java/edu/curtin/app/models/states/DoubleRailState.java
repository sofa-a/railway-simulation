package edu.curtin.app.models.states;

/*
 * State for a double railway.
 */

import edu.curtin.app.models.*;

import java.util.logging.Logger;

public class DoubleRailState implements RailwayState {
    private static final Logger logger =
            Logger.getLogger(DoubleRailState.class.getName());
    @Override
    public void construction(Railway railway){
        /* pass */
        logger.config("construction method called in doubleRailState therefore nothing happening");
    }

    @Override
    public void simulateDay(Railway railway, Town townA, Town townB) {
        /* no more state transitions can occur after this point */
        railway.simulateDoubleRails(townA, townB);
    }
}
