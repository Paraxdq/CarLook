package org.bonn.se.carlook.process.control;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CarControlTest {

    @Test
    void reserveCar() {
    }

    @Test
    void addNewCar() {
    }

    @Test
    void getInsertedCarsFromUser() {
    }

    @Test
    void getReservedCarsFromUser() {
        int userid = 31;

        User user = UserFactory.createEntity();
        user.setUserId(userid);

        CarDAO carDAO = CarDAO.getInstance();

        List<CarDTO> carDTOList = carDAO.getAllReservedCars(user);

        for (CarDTO carDTO:carDTOList) {
            System.out.println(carDTO.getCarId());
        }
        
        assertFalse(carDTOList.isEmpty());
    }

    @Test
    void getAllCars() {
        CarDAO carDAO = CarDAO.getInstance();

        List<CarDTO> carDTOList = carDAO.getAllCars();

        assertFalse(carDTOList.isEmpty());
    }
}
