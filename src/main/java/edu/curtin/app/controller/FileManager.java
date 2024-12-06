package edu.curtin.app.controller;

/*
 * Handles the file writing part of the program.
 * Through the observer pattern, fileManager will make any changes necessary to the file.
 */

import edu.curtin.app.models.*;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

public class FileManager implements RailObserver, TownObserver {
    private static final Logger logger =
            Logger.getLogger(FileManager.class.getName());
    private List<Railway> railways;
    private Map<String, Town> towns;

    public FileManager() {
        this.railways = new ArrayList<>();
        this.towns = new HashMap<>();
    }

    /** provides changes in the railway list from the RailList class - observer pattern being used */
    @Override
    public void updateRails(List<Railway> railways) {
        logger.finest("updating railways");
        this.railways = railways;
        updateFile();
    }

    /** provides changes in the town map from the TownMap class - observer pattern being used*/
    @Override
    public void updateTowns(Map<String, Town> towns) {
        logger.finest("updating towns");
        this.towns = towns;
        updateFile();
    }

    /** for printing what the relationship between towns are - in the style needed for graphviz */
    private String getRelationship(Town townA, Town townB) {
        logger.finer("relationship being retrieved");
        String relationship = townA.getRailways().get(townB.getTownName());
        switch (relationship) {
            /* matches what needs to be put in file to the town relationship */
            case "single_constr":
                return "[style=\"dashed\"]";
            case "single":
                return "";
            case "double_constr":
                return "[style=\"dashed\",color=\"black:black\"]";
            case "double":
                return "[color=\"black:black\"]";
            default:
                return null;
            /* complex exception handling would be required here so settled for making it null
             *  as it is not possible for the program to create a different message */
        }
    }

    /** this method writes to the simoutput.dot file */
    public void updateFile() {
        String contents = "graph Towns {\n";
        if (!towns.isEmpty()) {
            for (Town town : towns.values()) {
                /* adding towns to a temp string */
                contents = contents.concat("\t" + town.getTownName() + "\n");
            }
        }
        if (!railways.isEmpty()) {
            for (Railway railway : railways) {
                /* adding railways to a contents string */
                String townA = railway.getTownA().getTownName();
                String townB = railway.getTownB().getTownName();
                String relationship = getRelationship(railway.getTownA(), railway.getTownB());
                /* temp string holds the town names and the relationship in the correct format */
                String temp = townA + " -- " + townB + relationship;
                /* temp string then put into the contents string in the correct format */
                contents = contents.concat("\t" + temp + "\n");
            }
        }
        contents = contents.concat("}");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("simoutput.dot"))) {
            /* writes the contents string to the file */
            logger.finer("file has been written to");
            writer.write(contents);
            writer.flush();
        }
        catch (IOException e) {
            System.out.println("error occurred while writing to file.");
            logger.severe("problem with writing to the dot file has occurred");
        }
    }
}

