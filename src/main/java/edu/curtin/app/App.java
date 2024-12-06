package edu.curtin.app;

/*
 * Entry point into the application. To change the package, and/or the name of this class, make
 * sure to update the 'mainClass = ...' line in build.gradle.
 */

import edu.curtin.app.controller.*;
import edu.curtin.app.models.*;
import edu.curtin.app.view.ConsoleOutput;
import java.io.*;
import java.util.logging.Logger;

public class App
{
    private static final Logger logger =
            Logger.getLogger(App.class.getName());
    public static void main(String[] args) {

        /* CREATING OBJECTS NEEDED */
        TownsInput inp = new TownsInput();

        /* subject classes */
        TownMap townMap = new TownMap();
        RailList railList = new RailList();

        /* factory classes */
        TownFactory townFactory = new TownFactory();
        RailwayFactory railFactory = new RailwayFactory();

        /* this class is the big controller class */
        Simulation sim = new Simulation(townFactory, railFactory, townMap, railList);

        /* observer classes of railways and towns */
        FileManager fileManager = new FileManager();
        ConsoleOutput output = new ConsoleOutput();

        logger.finest("all objects have been created");

        /* initialising all the observers of the town and rail containers */
        townMap.addObserver(fileManager);
        townMap.addObserver(output);
        railList.addObserver(fileManager);
        railList.addObserver(output);
        logger.finer("concrete observers have been added");

        try {
            while(System.in.available() == 0) {
                /* main loop*/
                output.displayDay();
                String msg = inp.nextMessage();
                while(msg != null) {
                    output.displayMessages(msg);
                    sim.readMessage(msg);
                    msg = inp.nextMessage();
                }
                sim.simulateDay();
                output.displaySimulation();

                // Wait 1 second
                try {
                    Thread.sleep(1000);
                }
                catch(InterruptedException e) {
                    logger.severe("interruption has occurred");
                    System.out.println("interruption has occurred.");
                }
            }
        }
        catch(IOException e) {
            logger.severe("problem with file has occurred");
            System.out.println("Error reading user input");
        }
    }
}
