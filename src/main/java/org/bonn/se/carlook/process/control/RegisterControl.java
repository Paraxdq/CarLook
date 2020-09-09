package org.bonn.se.carlook.process.control;

import org.bonn.se.carlook.model.dao.CustomerDAO;
import org.bonn.se.carlook.model.dao.SalesmanDAO;
import org.bonn.se.carlook.model.dao.UserDAO;
import org.bonn.se.carlook.model.factory.CustomerFactory;
import org.bonn.se.carlook.model.factory.SalesmanFactory;
import org.bonn.se.carlook.model.factory.UserFactory;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.model.objects.entity.Customer;
import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.model.objects.entity.User;
import org.bonn.se.carlook.process.control.exception.DatabaseConnectionError;
import org.bonn.se.carlook.process.control.exception.UserAlreadyRegisteredException;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bonn.se.carlook.services.util.GlobalHelper.IsCompanyMember;

public class RegisterControl {

    // Endkunde sowie Vertriebler registrieren
    // Email von Vertriebler muss gültig vom Unternehmen sein (name@carlook.de)
    // Name + Passwort zur Registrierung nötig, mehr nicht

    private Logger logger;

    //private List<FailureType> failures = new ArrayList<>();

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

    public boolean registerUser(UserDTO userDTO) throws UserAlreadyRegisteredException, DatabaseConnectionError {
        UserDAO userDAO = UserDAO.getInstance();

        User user = UserFactory.createEntityFromDTO(userDTO);

        if(userDAO.select(user.getEMail()) != null)
            throw new UserAlreadyRegisteredException();

        user = userDAO.add(user);

        // ist der User ein Vertriebler?
        if(IsCompanyMember(user.getEMail())){
            Salesman salesman = SalesmanFactory.createEntityFromUserEntity(user);

            SalesmanDAO salesmanDAO = SalesmanDAO.getInstance();
            return salesmanDAO.add(salesman) != null;
        } else{
            Customer customer = CustomerFactory.createEntityFromUserEntity(user);

            CustomerDAO customerDAO = CustomerDAO.getInstance();
            return customerDAO.add(customer) != null;
        }
    }

    /*private void checkValue(String value, FailureType failureType){
        if(StringIsEmptyOrNull(value)) {
            failures.add(failureType);
        }
    }*/
}
