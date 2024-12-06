package edu.curtin.app.models.states;

/*
 * State for a single railway under construction between two towns.
 */

import edu.curtin.app.models.*;

import java.util.logging.Logger;

public class SingleCRailState implements RailwayState {
    private static final Logger logger =
            Logger.getLogger(SingleCRailState.class.getName());
    @Override
    public void construction(Railway railway){
        logger.config("construction method called in singleCRailState therefore nothing happening");
    }

    @Override
    public void simulateDay(Railway railway, Town townA, Town townB) {
        if (railway.getTime() == 5) {
            logger.fine("changing town relationship");
            /* time is set to 5 when initialising the railway. Therefore, signal enough to change
            * the relationship in the respective towns */
            railway.getTownA().addRailway(townB.getTownName(), "single_constr");
            railway.getTownB().addRailway(townA.getTownName(), "single_constr");
        }
        /* handling the time variable */
        int time = railway.getTime() - 1;
        railway.setTime(time);
        if (time == 0) {
            logger.finer("changing town relationship and state transition occurring");
            /* condition for this state to be left */
            railway.setState(new SingleRailState());
            townA.addRailway(townB.getTownName(), "single");
            townB.addRailway(townA.getTownName(), "single");
        }
    }
}
