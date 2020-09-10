package org.bonn.se.carlook.process.control;

import org.bonn.se.carlook.model.dao.CarDAO;
import org.bonn.se.carlook.model.dao.SalesmanDAO;
import org.bonn.se.carlook.model.dao.UserDAO;
import org.bonn.se.carlook.model.factory.CarFactory;
import org.bonn.se.carlook.model.objects.dto.CarDTO;
import org.bonn.se.carlook.model.objects.dto.UserDTO;
import org.bonn.se.carlook.model.objects.entity.Car;
import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.model.objects.entity.User;
import org.bonn.se.carlook.process.control.exception.CarAlreadyReservedException;
import org.bonn.se.carlook.process.control.exception.DatabaseConnectionError;
import org.bonn.se.carlook.services.util.GlobalHelper;
import org.bonn.se.carlook.services.util.ViewHelper;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CarControl {

    // Such Funktionalität für Autos
    // Endkunde soll nach Autos suchen können
    private final CarDAO carDAO = CarDAO.getInstance();

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

    public void ReserveCar(int carId) throws CarAlreadyReservedException, DatabaseConnectionError {
        UserDTO userDTO = ViewHelper.getLoggedInUserDTO();
        User user = UserDAO.getInstance().select(userDTO.getEMail());

        carDAO.reserveCar(carId, user);
    }

    public boolean AddNewCar(CarDTO carDTO) throws DatabaseConnectionError {
        // Vertriebler kann neue Autos einfügen
        // Auto einem Vertriebler zugeordnet
        // Vertriebler kann beliebig viele Autos einstellen

        //TODO TESTING !!!!!!
        Car car = CarFactory.createEntityFromDTO(carDTO);

        Salesman salesman = SalesmanDAO.getInstance().select(ViewHelper.getLoggedInUserDTO().getEMail());
        car.setSalesman(salesman.getUserId());

        carDAO.add(car);

        return true;
    }

    public List<CarDTO> getInsertedCarsFromUser(UserDTO userDTO) throws DatabaseConnectionError {
        User user = UserDAO.getInstance().select(userDTO.getEMail());

        return carDAO.getAllInsertedCars(user);
    }

    public List<CarDTO> getReservedCarsFromUser(UserDTO userDTO) throws DatabaseConnectionError {
        User user = UserDAO.getInstance().select(userDTO.getEMail());

        return carDAO.getAllReservedCars(user);
    }

    public List<CarDTO> getAllCars() {
        return carDAO.getAllCars();
    }

    public List<CarDTO> getFilteredCars(String carBrand, int yearOfConstruction, String carDescription) throws DatabaseConnectionError {
        boolean filterCarBrand = false;
        boolean filterCarDescription = false;
        boolean filterYearOfConstruction = false;

        if(!GlobalHelper.StringIsEmptyOrNull(carBrand))
            filterCarBrand = true;

        if(!GlobalHelper.StringIsEmptyOrNull(carDescription))
            filterCarDescription = true;

        if(yearOfConstruction != 0)
            filterYearOfConstruction = true;

        List<CarDTO> carDTOList = carDAO.getAllCars();
        List<CarDTO> filteredCarDTOList = null;

        if(filterCarBrand){
            filteredCarDTOList = carDTOList.stream().filter(c -> c.getCarBrand().toLowerCase().
                    contains(carBrand.toLowerCase())).collect(Collectors.toList());
        }

        if(filterYearOfConstruction){
            if(filteredCarDTOList == null){
                filteredCarDTOList = carDTOList.stream().filter(d -> d.getYearOfConstruction() == yearOfConstruction).
                        collect(Collectors.toList());
            } else{
                filteredCarDTOList = filteredCarDTOList.stream().filter(d -> d.getYearOfConstruction() == yearOfConstruction).
                        collect(Collectors.toList());
            }
        }

        if(filterCarDescription){
            if(filteredCarDTOList == null){
                filteredCarDTOList = carDTOList.stream().filter(e -> e.getDescription().toLowerCase().
                        contains(carDescription.toLowerCase())).collect(Collectors.toList());
            } else{
                filteredCarDTOList = filteredCarDTOList.stream().filter(e -> e.getDescription().toLowerCase().
                        contains(carDescription.toLowerCase())).collect(Collectors.toList());
            }
        }

        return filteredCarDTOList;
    }
}
