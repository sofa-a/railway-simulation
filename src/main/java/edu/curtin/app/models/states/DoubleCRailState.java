package edu.curtin.app.models.states;

/*
 * State for a single railway that is under construction to become a double railway.
 */

import edu.curtin.app.models.*;

import java.util.logging.Logger;

public class DoubleCRailState implements RailwayState {
    private static final Logger logger =
            Logger.getLogger(DoubleCRailState.class.getName());
    @Override
    public void construction(Railway railway) {
        /* pass */
        logger.config("construction method called in doubleCRailState therefore nothing happening");
    }

    @Override
    public void simulateDay(Railway railway, Town townA, Town townB) {
        if (railway.getTime() == 5) {
            logger.fine("changing town relationship");
            /* time is set to 5 in the construction method from single rail state.
             * Therefore, when simulateDay is called in doubleCRailState, the state of the
             * towns relationship can be changed. Occurs within the iteration as construction
             * methods are called before the simulate day method is called */
            railway.getTownA().addRailway(townB.getTownName(), "double_constr");
            railway.getTownB().addRailway(townA.getTownName(), "double_constr");
        }
        /* handling the time variable */
        int time = railway.getTime() - 1;
        railway.setTime(time);
        if (time == 0) {
            logger.finer("changing town relationship and state transition occurring");
            /* condition for this state to be left */
            railway.setState(new DoubleRailState());
            townA.addRailway(townB.getTownName(), "double");
            townB.addRailway(townA.getTownName(), "double");
        }
        railway.simulateSingleRails(townA, townB);
    }
}
