package org.bonn.se.carlook.process.control.exception;

import java.util.logging.Logger;

public class LoginControl {

    // Fehlerhandling bei nicht vorhandenem User
    // ???? Fehlerhandling bei vorhandenem User ?????

    private Logger logger;
    private static LoginControl instance = null;

    public static LoginControl getInstance() {
        if (instance == null) {
            instance = new LoginControl();
        }
        return instance;
    }

    private LoginControl() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }
}
