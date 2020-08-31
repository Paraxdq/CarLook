package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.objects.entity.AbstractEntity;
import org.bonn.se.carlook.model.objects.entity.User;
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

    public abstract E add(E entity) throws SQLException;

    public abstract boolean update(E entity) throws SQLException;

    public abstract E select(String identifier) throws SQLException;

    public abstract boolean remove(E entity);

    protected void mapResultSetToEntity(ResultSet rs, User entity) throws SQLException {
        // Mapping
        entity.setUserId(rs.getInt("userid"));

        entity.setEMail(rs.getString("email"));
        entity.setPassword(rs.getString("password"));

        entity.setForename(rs.getString("forename"));
        entity.setSurname(rs.getString("surname"));
    }
}
