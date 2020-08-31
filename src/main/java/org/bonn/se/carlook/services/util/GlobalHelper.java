package org.bonn.se.carlook.services.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalHelper {

    /**
        Checks if string is empty or null
        returns true if at least one condition is true.
    */
    public static boolean StringIsEmptyOrNull(String value) {
        return value == null || value.equals("");
    }

    public static boolean IsCompanyMember(String email){
        Pattern p = Pattern.compile("\\b[a-zA-Z\\d._%-]+@carlook.de\\b");
        Matcher m = p.matcher(email.toLowerCase());

        if (m.find())
            return true;

        return false;
    }
}
