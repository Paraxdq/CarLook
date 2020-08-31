package org.bonn.se.carlook.process.control;

import org.bonn.se.carlook.model.dao.SalesmanDAO;
import org.bonn.se.carlook.model.dao.UserDAO;
import org.bonn.se.carlook.model.factory.UserFactory;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.model.objects.entity.User;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;
import org.bonn.se.carlook.services.util.GlobalHelper;
import org.bonn.se.carlook.services.util.RegistrationResult;
import org.bonn.se.carlook.services.util.RegistrationResult.FailureType;

import javax.jws.soap.SOAPBinding;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bonn.se.carlook.services.util.GlobalHelper.IsCompanyMember;
import static org.bonn.se.carlook.services.util.GlobalHelper.StringIsEmptyOrNull;

public class RegisterControl {

    private Logger logger;

    private List<FailureType> failures = new ArrayList<>();

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

    public boolean registerUser(UserDTO userDTO) throws UserAlreadyRegisteredException {
        //RegistrationResult<UserDTO> result = new RegistrationResult<>();
        UserDAO userDAO = UserDAO.getInstance();

        User user = UserFactory.createEntityFromDTO(userDTO);

        if (userDAO.select(user.getEMail()) != null)
            throw new UserAlreadyRegisteredException();

        ResultSet rs = userDAO.add(user);

        if(IsCompanyMember(user.getEMail())){
            SalesmanDAO salesmanDAO = SalesmanDAO.getInstance();

            //TODO continue
        } else{
            //TODO CustomerDAO add
        }

        try {
            if (rs.next()) {
                user.setUserId(rs.getInt("userid"));
            } else{
                logger.log(Level.SEVERE, "RegisterControl - Error: No userid was found!");
                return false;
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "RegisterControl - Error: Error in registerUser function!", ex);
            return false;
        }

        //return result;
        return true;
    }

    private void checkValue(String value, FailureType failureType){
        if(StringIsEmptyOrNull(value)) {
            failures.add(failureType);
        }
    }
}
