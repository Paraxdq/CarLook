package org.bonn.se.carlook.process.control;

import org.apache.commons.lang3.RandomStringUtils;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;
import org.bonn.se.carlook.services.util.RegistrationResult;
import org.junit.jupiter.api.Test;

import javax.servlet.Registration;

import static org.junit.jupiter.api.Assertions.*;

class RegisterControlTest {

    @Test
    void TestUserAlreadyRegisteredException() {
        RegisterControl reg = RegisterControl.getInstance();
        //RegistrationResult<UserDTO> result = new RegistrationResult<>();

        String exceptedResult = "org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException";
        String result = "";

        UserDTO userDTO = new UserDTO();

        userDTO.setEMail("test@test.de");
        userDTO.setPassword("abc");
        userDTO.setForename("Günther");
        userDTO.setSurname("Müller");

        try {
            reg.registerUser(userDTO);
        } catch (UserAlreadyRegisteredException e) {
            result = e.toString();
        }

        assertEquals(exceptedResult, result);
    }

    @Test
    void TestRegisterUser() {
        RegisterControl reg = RegisterControl.getInstance();

        boolean exceptedResult = true;
        boolean result = false;

        UserDTO userDTO = new UserDTO();

        userDTO.setEMail(generateRandomEmail(8));
        userDTO.setPassword("abc");
        userDTO.setForename("Günther");
        userDTO.setSurname("Müller");

        System.out.println(userDTO.getEMail());

        try {
            result = reg.registerUser(userDTO);
        } catch (UserAlreadyRegisteredException e) {
            e.printStackTrace();
        }

        assertEquals(exceptedResult, result);
    }

    public static String generateRandomEmail(int length) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "_-.";
        String email = "";
        String temp = RandomStringUtils.random(length, allowedChars);
        email += temp + "@test.de";
        return email;
    }
}