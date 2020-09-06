package org.bonn.se.carlook.model.factory;

import org.bonn.se.carlook.model.objects.dto.CarDTO;
import org.bonn.se.carlook.model.objects.entity.Car;

public class CarFactory {

    private CarFactory(){}

    public static Car createEntity() {
        return new Car();
    }

    public static CarDTO createDTO() {
        return new CarDTO();
    }

    public static Car createEntityFromDTO(CarDTO dto) {
        Car carEntity = createEntity();

        carEntity.setCarId(dto.getCarId());
        carEntity.setCarBrand(dto.getCarBrand());
        carEntity.setYearOfConstruction(dto.getYearOfConstruction());
        carEntity.setDescription(dto.getDescription());
        carEntity.setSalesman(dto.getSalesman());

        return carEntity;
    }

    public static CarDTO createDTOFromEntity(Car entity) {
        CarDTO carDTO = createDTO();

        carDTO.setCarId(entity.getCarId());
        carDTO.setCarBrand(entity.getCarBrand());
        carDTO.setYearOfConstruction(entity.getYearOfConstruction());
        carDTO.setDescription(entity.getDescription());
        carDTO.setSalesman(entity.getSalesman());

        return carDTO;
    }
}
