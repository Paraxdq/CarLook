package org.bonn.se.carlook.process.control;

import java.util.logging.Logger;

public class RegisterControl {

    private Logger logger;

    // Endkunde sowie Vertriebler registrieren

    // Email von Vertriebler muss gültig vom Unternehmen sein (name@carlook.de)

    // Name + Passwort zur Registrierung nötig, mehr nicht

    private static RegisterControl instance = null;

    public static RegisterControl getInstance() {
        if (instance == null) {
            instance = new RegisterControl();
        }
        return instance;
    }

    private RegisterControl() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

}
