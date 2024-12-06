package edu.curtin.app.models.states;

/*
 * State for a single railway.
 */

import edu.curtin.app.models.*;

import java.util.logging.Logger;

public class SingleRailState implements RailwayState {
    private static final Logger logger =
            Logger.getLogger(SingleRailState.class.getName());
    @Override
    public void construction(Railway railway) {
        /* state transition */
        logger.finest("state transition occurring");
        railway.setState(new DoubleCRailState());
        railway.setTime(5);
    }

    @Override
    public void simulateDay(Railway railway, Town townA, Town townB) {
        railway.simulateSingleRails(townA, townB);
    }
}
