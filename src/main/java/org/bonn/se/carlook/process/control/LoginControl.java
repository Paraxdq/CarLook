package org.bonn.se.carlook.process.control.exception;

import org.bonn.se.carlook.model.dao.UserDAO;
import org.bonn.se.carlook.model.factory.UserFactory;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.model.objects.entity.User;

import java.util.logging.Level;
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

    public UserDTO loginUser(UserDTO userDTO) throws InvalidLoginData, DatabaseConnectionError {
        UserDAO userDAO = UserDAO.getInstance();

        User user = userDAO.select(userDTO.getEMail());

        if(user == null)
            throw new DatabaseConnectionError();

        if(user.getPassword().equals(userDTO.getPassword())){
            return UserFactory.createDTOFromEntity(user);
        } else {
            throw new InvalidLoginData();
        }
    }

}
