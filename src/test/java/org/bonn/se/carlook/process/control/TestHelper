package org.bonn.se.carlook.process.control;

import org.apache.commons.lang3.RandomStringUtils;

public class TestHelper {
    public static String generateRandomEmail(int length, String domain) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "_-.";
        String email = "";
        String temp = RandomStringUtils.random(length, allowedChars);
        email += temp + domain;
        return email;
    }
}
