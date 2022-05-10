package ch.shipster.service;

import ch.shipster.ShipsterApplication;

import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

// Jonas
// Software engineering class REPLACE ME WITH REPO LINK

public class ShipsterLogger {
    public static final String LOGGER_NAME = ShipsterApplication.class.getSimpleName();
    private Logger logger = null;

    public ShipsterLogger() {
        this.logger = Logger.getLogger(LOGGER_NAME);
        logger.setLevel(Level.INFO);

        Handler[] defaultHandlers = Logger.getLogger("").getHandlers();
        System.out.println(defaultHandlers.length);
        if (defaultHandlers.length == 1) {
            defaultHandlers[0].setLevel(Level.INFO);
        } else {
            throw new RuntimeException("More than one default handler found");
        }

        // File handler with rotating files in the java project directory
        try {
            Handler logHandler = new FileHandler("%t/" + LOGGER_NAME + "_%u" + "_%g" + ".log", 1000000, 9);
            logHandler.setLevel(Level.FINE);
            logger.addHandler(logHandler);
        } catch (Exception e) {
            throw new RuntimeException("Unable to initialize log files: " + e.toString());
        }
    }


    public void doStuff() {
        logger.fine("Fine message from main class");
        logger.info("Info message from main class");
        logger.warning("Warning message from main class");
        //OtherClass other = new OtherClass();
        //OtherClass2 other2 = new OtherClass2();
    }
}
