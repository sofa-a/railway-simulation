package edu.curtin.app.controller;

/*
 * Contains the bulk of the logic - main controller of the simulation
 */

import edu.curtin.app.models.*;
import java.util.Map;
import java.util.List;
import java.util.logging.Logger;

public class Simulation {
    private static final Logger logger =
            Logger.getLogger(Simulation.class.getName());
    private TownMap towns;
    private RailList railways;
    private TownFactory townFactory;
    private RailwayFactory railFactory;

    public Simulation(TownFactory townFactory, RailwayFactory railFactory,
                      TownMap townMap, RailList railList) {
        this.towns = townMap;
        this.railways = railList;
        this.townFactory = townFactory;
        this.railFactory = railFactory;
    }

    public void readMessage(String msg) {
        try {
            String[] parts = msg.split(" ");
            if (parts.length != 3) {
                logger.warning("message is of an incorrect length.");
                throw new MessageFormatException("Message is of an incorrect length.");
            }
            switch (parts[0]) {
                case "town-founding":
                    townFounding(parts[1], parts[2]);
                    break;
                case "town-population":
                    townPopulation(parts[1], parts[2]);
                    break;
                case "railway-construction":
                    railwayConstruction(parts[1], parts[2]);
                    break;
                case "railway-duplication":
                    railwayDuplication(parts[1], parts[2]);
                    break;
                default:
                    logger.info("incorrect message type provided");
                    throw new MessageFormatException("Incorrect message type provided. ");
            }
        }
        catch(MessageFormatException | NumberFormatException | InvalidTownException e) {
            System.out.println(e.getMessage());
        }

    }

    private boolean checkTownName(String townName) {
        return towns.getTown(townName) != null;
    }

    private void townFounding(String townName, String population) {
        int pop = Integer.parseInt(population);
        Town newTown = townFactory.createTown(townName, pop);
        towns.addTown(newTown);

    }
    private void townPopulation(String townName, String population) throws InvalidTownException {
        if (!checkTownName(townName)) {
            logger.info("invalid town name provided");
            throw new InvalidTownException("Changing the population of a non-existent town is not allowed. ");
        }
        int pop = Integer.parseInt(population);
        towns.updateTownPop(townName, pop);
    }

    private void railwayConstruction(String townNameA, String townNameB) throws InvalidTownException {
        if (!checkTownName(townNameA) || !checkTownName(townNameB)) {
            logger.info("invalid town name provided");
            throw new InvalidTownException("Incorrect name of a town provided. ");
        }
        if (townNameA.equals(townNameB)) {
            logger.warning("cannot make a railway to the same town");
            throw new InvalidTownException("Cannot make a railway to the same town");
        }
        Town townA = towns.getTown(townNameA);
        Town townB = towns.getTown(townNameB);
        Railway railway = railFactory.createRailway(townA, townB);
        towns.updateRelationships(townA, townB, "single_constr");
        towns.updateRelationships(townB, townA, "single_constr");
        railways.addRailway(railway);
    }
    private void railwayDuplication(String townNameA, String townNameB) throws InvalidTownException, MessageFormatException{
        if (!checkTownName(townNameA) || !checkTownName(townNameB)) {
            logger.info("invalid town name provided");
            throw new InvalidTownException("Incorrect name of a town provided for railway duplication. ");
        }
        if (townNameA.equals(townNameB)) {
            logger.warning("cannot make a railway to the same town");
            throw new InvalidTownException("Cannot make a railway to the same town");
        }
        Town townA = towns.getTown(townNameA);
        Town townB = towns.getTown(townNameB);
        if (!townA.getRailways().get(townNameB).equals("single")) {
            logger.warning("cannot duplicate railways.");
            throw new MessageFormatException("Can't duplicate railway unless single is set up first");
        }
        logger.config("updating town relationships");
        towns.updateRelationships(townA, townB, "double_constr");
        towns.updateRelationships(townB, townA, "double_constr");
        railways.constructRailway(townNameA, townNameB);
    }

    public void simulateDay() {
        Map<String, Town> townsCopy = towns.getTowns();
        for (Town town : townsCopy.values()) {
            logger.finest("resetting transported goods and creating stockpile for all towns");
            town.setGoodsTransported(0);
            town.produceGoods();
        }

        List<Railway> railwaysCopy = railways.getRailways();

        for (Railway railway : railwaysCopy) {
            logger.finest("simulating railway for all the towns that have railways");
            Town townA = railway.getTownA();
            Town townB = railway.getTownB();
            railway.simulateDay(townA, townB);
            /* due to the town stock being updated, no need to worry about updating the relationships
            *   separately */
            towns.updateTownStock(railway.getTownA().getTownName(), railway.getTownA().getStockpile());
            towns.updateTownStock(railway.getTownB().getTownName(), railway.getTownB().getStockpile());
        }
    }
}
