package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.factory.CarFactory;
import org.bonn.se.carlook.model.objects.entity.Car;
import org.bonn.se.carlook.services.util.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class CarDAO extends AbstractDAO<Car> {

    private static CarDAO carDAO = null;

    public static CarDAO getInstance() {
        if (carDAO == null) {
            carDAO = new CarDAO();
        }
        return carDAO;
    }

    private CarDAO() {
        super.table = Globals.TABLE_CAR;
    }

    @Override
    public Car add(Car entity){
        String sql = String.format(
            "INSERT INTO %s.%s " +
            "(%s, %s, %s, %s) " +
            "VALUES(?, ?, ?, ?);", //(SELECT %s FROM %s.%s WHERE %s = %s));",
            Globals.DATABASE_NAME,
            super.table,
            Globals.TABLE_CAR_BRAND,
            Globals.TABLE_CAR_CONSTRUCTION,
            Globals.TABLE_CAR_DESC,
            Globals.TABLE_CAR_SALESMAN);

            /*Globals.TABLE_USER_IDENTIFIER,
            Globals.DATABASE_NAME,
            Globals.TABLE_USER,
            Globals.TABLE_USER_EMAIL,
            entity.getSalesman());*/

        ResultSet rs = null;

        try(PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            if(stm == null)
                return null;

            stm.setString(1, entity.getCarBrand());
            stm.setInt(2, entity.getYearOfConstruction());
            stm.setString(3, entity.getDescription());
            stm.setInt(4, entity.getSalesman());

            stm.executeUpdate();

            rs = stm.getGeneratedKeys();

            if (rs.next()) {
                entity.setCarId(rs.getInt(Globals.TABLE_CAR_IDENTIFIER));
            } else{
                logger.log(Level.SEVERE, "CarDAO - Error: No carid was found!");
                return null;
            }

        } catch(SQLException ex){
            logger.log(Level.SEVERE, "CarDAO - Error: Error in add function!", ex);
            return null;
        }

        return entity;
    }

    @Override
    public boolean update(Car entity) {
        return false;
    }

    @Override
    public Car select(String identifier) {
        Car car = CarFactory.createEntity();

        String sql = String.format(
            "SELECT * " +
            "FROM %s.%s " +
            "WHERE %s = ?",
            Globals.DATABASE_NAME,
            super.table,
            Globals.TABLE_CAR_IDENTIFIER);

        try(PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            if(stm == null)
                return null;

            stm.setString(1, identifier);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next())
                    super.mapResultSetToCarEntity(rs, car);
                else
                    return null;
            }
        } catch(SQLException ex){
            logger.log(Level.SEVERE, "UserDAO: Error in select function!", ex);
            return null;
        }

        return car;
    }

    @Override
    public boolean remove(Car entity) {
        return false;
    }
}