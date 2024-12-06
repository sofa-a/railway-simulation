package edu.curtin.app.models;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Town {
    private Map<String, String> connectedTowns;
    private String townName;
    private int population;
    private int stockpile;
    private int goodsTransported;

    public Town(String townName, int population) {
        this.townName = townName;
        this.population = population;
        this.connectedTowns = new HashMap<>();
    }

    /** all the setters for the town */
    public void setPopulation(int population) {this.population = population;}
    public void setStockpile(int stockpile) {this.stockpile = stockpile;}
    public void setGoodsTransported(int goodsTransported) {this.goodsTransported = goodsTransported;}

    /** all the getters for the town */
    public String getTownName() {return this.townName;}
    public int getPopulation() {return this.population;}
    public int getStockpile() {return this.stockpile;}
    public int getGoodsTransported() {return this.goodsTransported;}
    public Map<String, String> getRailways() {return Collections.unmodifiableMap(connectedTowns);}

    /** add town object to the map */
    public void addRailway(String townName, String relationship) {
        connectedTowns.put(townName, relationship);
    }

    /** increase stockpile based on the population and the current stockpile */
    public void produceGoods() {
        stockpile = stockpile + population;
    }
}
