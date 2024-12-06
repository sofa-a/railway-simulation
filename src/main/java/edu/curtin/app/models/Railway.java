package edu.curtin.app.models;

import edu.curtin.app.models.states.SingleCRailState;

import java.util.logging.Logger;

public class Railway {
    private static final Logger logger =
            Logger.getLogger(Railway.class.getName());
    private RailwayState state;
    private Town townA;
    private Town townB;
    private int time;
    private boolean direction;

    public Railway(Town townA, Town townB) {
        this.townA = townA;
        this.townB = townB;
        state = new SingleCRailState();
        time = 5;
        direction = true;
    }

    /** all the setters for the railway */
    public void setState(RailwayState state){this.state = state;}
    public void setTime(int time) {this.time = time;}
    public void setDirection(boolean direction) {this.direction = direction;}

    /** all the getters for the railway */
    public int getTime() { return this.time; }
    public boolean getDirection() { return this.direction; }
    public Town getTownA() { return this.townA; }
    public Town getTownB() { return this.townB; }

    /** the methods that are dependent on the state of the railway */
    public void simulateDay(Town townA, Town townB) {state.simulateDay(this, townA, townB);}
    public void construction() {state.construction(this);}

    /** this method is used by the states to simulate the single railway
     * town objects are altered according to the current direction and size of their stockpiles */
    public void simulateSingleRails(Town townA, Town townB) {
        logger.finest("update based on direction and amount of stockpile of chosen town available");
        if (getDirection() && townA.getStockpile() > 100) {
            /* case for if left over goods are available */
            townA.setStockpile(townA.getStockpile()-100);
            townA.setGoodsTransported(townA.getGoodsTransported()+100);
            setDirection(false);
        }
        else if (getDirection() && townA.getStockpile() < 100) {
            /* enough room to transport all goods that the town has */
            townA.setStockpile(0);
            townA.setGoodsTransported(townA.getGoodsTransported()+townA.getStockpile());
            setDirection(false);
        }
        else if (!getDirection() && townB.getStockpile() > 100) {
            /* case for if left over goods are available */
            townB.setStockpile(townB.getStockpile()-100);
            townB.setGoodsTransported(townB.getGoodsTransported()+100);
            setDirection(true);
        }
        else if (!getDirection() && townB.getStockpile() < 100) {
            /* enough room to transport all goods that the town has */
            townB.setStockpile(0);
            townB.setGoodsTransported(townB.getGoodsTransported()+townB.getStockpile());
            setDirection(true);
        }
        else {
            logger.warning("incorrect values being passed into method");
        }
    }

    /** this method is used by the states to simulate the double railway
     * town objects are altered according to the current direction and size of their stockpiles */
    public void simulateDoubleRails(Town townA, Town townB) {
        logger.finest("update based amount of stockpile of chosen town available");
        if (townA.getStockpile() > 100) {
            /* case for if left over goods are available */
            townA.setStockpile(townA.getStockpile()-100);
            townA.setGoodsTransported(townA.getGoodsTransported()+100);
        }
        else if (townA.getStockpile() < 100) {
            /* enough room to transport all goods that the town has */
            townA.setGoodsTransported(townA.getGoodsTransported()+townA.getStockpile());
            townA.setStockpile(0);
        }
        if (townB.getStockpile() > 100) {
            /* case for if left over goods are available */
            townB.setStockpile(townB.getStockpile()-100);
            townB.setGoodsTransported(townB.getGoodsTransported()+100);
        }
        else if (townB.getStockpile() < 100) {
            /* enough room to transport all goods that the town has */
            townB.setGoodsTransported(townB.getGoodsTransported()+townB.getStockpile());
            townB.setStockpile(0);
        }
    }
}
