package org.bonn.se.carlook.process.control;

import org.bonn.se.carlook.model.dao.CarDAO;
import org.bonn.se.carlook.model.dao.SalesmanDAO;
import org.bonn.se.carlook.model.factory.CarFactory;
import org.bonn.se.carlook.model.objects.dto.CarDTO;
import org.bonn.se.carlook.model.objects.entity.Car;
import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.process.control.exception.LoginControl;
import org.bonn.se.carlook.services.util.ViewHelper;

import java.time.LocalDate;
import java.util.logging.Logger;

public class CarControl {

    // Such Funktionalität für Autos
    // Endkunde soll nach Autos suchen können

    private Logger logger;
    private static CarControl instance = null;

    public static CarControl getInstance() {
        if (instance == null) {
            instance = new CarControl();
        }
        return instance;
    }

    private CarControl() {
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public void ReserveCar(){
        // Endkunde kann beliebig viele Autos reservieren
        // Ein Auto kann von beliebig vielen Endkunden reserviert werden
    }

    public void AddNewCar(CarDTO carDTO){
        // Vertriebler kann neue Autos einfügen
        // Auto einem Vertriebler zugeordnet
        // Vertriebler kann beliebig viele Autos einstellen

        //TODO TESTING !!!!!!
        Car car = CarFactory.createEntityFromDTO(carDTO);

        Salesman salesman = SalesmanDAO.getInstance().select(ViewHelper.getLoggedInUserDTO().getEMail());
        car.setSalesman(salesman.getUserId());

        CarDAO carDAO = CarDAO.getInstance();
        carDAO.add(car);

    }
}
