package org.bonn.se.carlook.model.objects.dto;

public class CarDTO extends AbstractDTO {

    int carId;
    String carBrand;
    int yearOfConstruction;

    String description;

    int salesman;

    public int getCarId(){
        return this.carId;
    }

    public void setCarId(int carId){
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public int getYearOfConstruction() {
        return yearOfConstruction;
    }

    public void setYearOfConstruction(int yearOfConstruction) {
        this.yearOfConstruction = yearOfConstruction;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSalesman() {
        return salesman;
    }

    public void setSalesman(int salesman) {
        this.salesman = salesman;
    }
}
