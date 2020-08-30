package org.bonn.se.carlook.services.util;

public class GlobalHelper {

    /**
        Checks if string is empty or null
        returns true if at least one condition is true.
    */
    public static boolean StringIsEmptyOrNull(String value) {
        return value == null || value.equals("");
    }
}
