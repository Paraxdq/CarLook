package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.objects.dto.CarDTO;
import org.bonn.se.carlook.model.objects.entity.AbstractEntity;
import org.bonn.se.carlook.model.objects.entity.Car;
import org.bonn.se.carlook.model.objects.entity.User;
import org.bonn.se.carlook.process.control.exception.InvalidLoginData;
import org.bonn.se.carlook.services.util.Globals;
import org.bonn.se.carlook.services.util.JDBCConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

public abstract class AbstractDAO<E extends AbstractEntity> {
    JDBCConnection connection;

    protected String table;
    protected Logger logger;

    public AbstractDAO() {
        connection = JDBCConnection.getInstance();
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    }

    public abstract E add(E entity);

    public abstract boolean update(E entity);

    public abstract E select(String identifier) throws InvalidLoginData;

    public abstract boolean remove(E entity);

    protected void mapResultSetToUserEntity(ResultSet rs, User entity) throws SQLException {
        // Mapping
        entity.setUserId(rs.getInt(Globals.TABLE_USER_IDENTIFIER));

        entity.setEMail(rs.getString(Globals.TABLE_USER_EMAIL));
        entity.setPassword(rs.getString(Globals.TABLE_USER_PASSWORD));

        entity.setForename(rs.getString(Globals.TABLE_USER_FORENAME));
        entity.setSurname(rs.getString(Globals.TABLE_USER_SURNAME));
    }

    protected void mapResultSetToCarEntity(ResultSet rs, Car entity) throws SQLException {
        // Mapping
        entity.setCarId(rs.getInt(Globals.TABLE_CAR_IDENTIFIER));

        entity.setCarBrand(rs.getString(Globals.TABLE_CAR_BRAND));
        entity.setYearOfConstruction(rs.getInt(Globals.TABLE_CAR_CONSTRUCTION));
        entity.setDescription(rs.getString(Globals.TABLE_CAR_DESC));
    }

    protected void mapResultSetToCarDTO(ResultSet rs, CarDTO dto) throws SQLException {
        // Mapping
        dto.setCarId(rs.getInt(Globals.TABLE_CAR_IDENTIFIER));

        dto.setCarBrand(rs.getString(Globals.TABLE_CAR_BRAND));
        dto.setYearOfConstruction(rs.getInt(Globals.TABLE_CAR_CONSTRUCTION));
        dto.setDescription(rs.getString(Globals.TABLE_CAR_DESC));
    }
}
