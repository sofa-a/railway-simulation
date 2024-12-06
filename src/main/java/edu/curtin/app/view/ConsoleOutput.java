package edu.curtin.app.view;

/*
 * Class that handles what is displayed to the user.
 */

import edu.curtin.app.models.*;
import java.util.*;
import java.util.logging.Logger;


public class ConsoleOutput implements RailObserver, TownObserver {
    private static final Logger logger =
            Logger.getLogger(ConsoleOutput.class.getName());
    private int day;
    private List<Railway> railways;
    private Map<String, Town> towns;

    public ConsoleOutput() {
        this.day = 0;
        this.railways = new ArrayList<>();
        this.towns = new HashMap<>();
    }

    /** provides changes in the railway list from the RailList class - observer pattern being used */
    @Override
    public void updateRails(List<Railway> railways) {
        this.railways = railways;
    }

    /** provides changes in the town map from the TownMap class - observer pattern being used*/
    @Override
    public void updateTowns(Map<String, Town> towns) {
        this.towns = towns;
    }

    /** displays the iteration that the simulation is at */
    public void displayDay() {
        logger.finest("displaying day");
        System.out.println("---");
        System.out.println("Day " + day + ":");
        System.out.println();
        day++;
    }

    /** displays the message that is about to be passed through the readMessage function in Simulation */
    public void displayMessages(String msg) {
        logger.fine("displaying messages");
       System.out.println(msg);
    }

    /** counts the single railways in a town object */
    private int countSingles(Town town) {
        int singleRail = 0;
        for (Railway railway : railways) {
            /* iterating through all the railway objects */
            String townAName = railway.getTownA().getTownName();
            String townBName = railway.getTownB().getTownName();


            if (town.getTownName().equals(townAName)) {
                /* if town correlates to townA, then checks are performed on townB */
                if (town.getRailways().get(townBName).equals("single")) {
                    /* check if railway is single */
                    singleRail++;
                } else if (town.getRailways().get(townBName).equals("double_constr")) {
                    /* check if railway is single with double railway being constructed */
                    singleRail++;
                }
            } else if (town.getTownName().equals(townBName)) {
                /* if town correlates to townB, then checks are performed on townA */
                if (town.getRailways().get(townAName).equals("single")) {
                    /* check if railway is single */
                    singleRail++;
                } else if (town.getRailways().get(townAName).equals("double_constr")) {
                    /* check if railway is single with double railway being constructed */
                    singleRail++;
                }
            }
            logger.config("updating single rail amount");
            /* external checks required, or program will attempt to look for townA in
            *  townA's relationship map */
        }
        return singleRail;
    }

    /** counts the double railways in a town object */
    public int countDoubles(Town town) {
        int doubleRail = 0;
        for (Railway railway : railways) {
            /* iterating through all the railway objects */
            String townAName = railway.getTownA().getTownName();
            String townBName = railway.getTownB().getTownName();
            /* if town correlates to townA, then checks are performed on townB  + check if double railway */
            if (town.getTownName().equals(townAName) && town.getRailways().get(townBName).equals("double")) {
                doubleRail ++;
            }
            /* if town correlates to townB, then checks are performed on townB  + check if double railway */
            else if (town.getTownName().equals(townBName) && town.getRailways().get(townAName).equals("double")) {
                doubleRail ++;
            }
            logger.config("updating double rail amount");
        }
        return doubleRail;
    }

    /** displays all the current information in the simulation */
    public void displaySimulation() {
        System.out.println();
        if (!towns.isEmpty()) {
            for (Town town : towns.values()) {
                /* iterating through all the town objects in the town map to display all information */
                int singleRail = 0;
                int doubleRail = 0;
                if (!railways.isEmpty()) {
                    /* retrieving the railway information of the towns */
                    logger.finer("there exists a railway in the simulation");
                    singleRail = countSingles(town);
                    doubleRail = countDoubles(town);
                }

                /* print the relevant information to the screen */
                logger.finest("simulation is being displayed.");
                System.out.println(town.getTownName() + " pop: " + town.getPopulation() + " rs: " + singleRail
                        + " rd: " + doubleRail + " gs: " + town.getStockpile() + " gt: " + town.getGoodsTransported());
            }
            System.out.println("...");
        }
    }
}
