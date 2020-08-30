package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.objects.entity.AbstractEntity;
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

    public abstract ResultSet add(E entity) throws SQLException;

    public abstract boolean update(E entity) throws SQLException;

    public abstract E select(String identifier) throws SQLException;

    public abstract boolean remove(E entity);
}
