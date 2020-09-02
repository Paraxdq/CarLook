package org.bonn.se.carlook.process.control;

import org.apache.commons.lang3.RandomStringUtils;
import org.bonn.se.carlook.model.dao.SalesmanDAO;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.process.control.exception.RegisterControl;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;
import org.junit.jupiter.api.Test;

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
    void TestRegisterCustomer() {
        RegisterControl reg = RegisterControl.getInstance();

        boolean exceptedResult = true;
        boolean result = false;

        UserDTO userDTO = new UserDTO();

        userDTO.setEMail(generateRandomEmail(8, "@test.de"));
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

        //TODO CustomerDAO select
    }

    @Test
    void TestRegisterSalesman() {
        RegisterControl reg = RegisterControl.getInstance();

        boolean exceptedResult = true;
        boolean result = false;

        UserDTO userDTO = new UserDTO();

        userDTO.setEMail(generateRandomEmail(8, "@cArLoOk.de"));
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

        Salesman salesman = SalesmanDAO.getInstance().select(userDTO.getEMail());

        assertNotNull(salesman);
    }

    public static String generateRandomEmail(int length, String domain) {
        String allowedChars = "abcdefghijklmnopqrstuvwxyz" + "1234567890" + "_-.";
        String email = "";
        String temp = RandomStringUtils.random(length, allowedChars);
        email += temp + domain;
        return email;
    }
}