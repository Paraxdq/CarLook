package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.factory.UserFactory;
import org.bonn.se.carlook.model.objects.entity.User;
import org.bonn.se.carlook.services.util.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class UserDAO extends AbstractDAO<User> {

    private UserDAO() {
        super.table = Globals.TABLE_USER;
    }

    @Override
    public ResultSet add(User entity) throws SQLException {
        String sql = String.format(
                "INSERT INTO %s.%s " +
                "(email, password, surname, lastname) " +
                "VALUES(?, ?, ?, ?);",
                Globals.DATABASE_NAME,
                super.table);

        ResultSet rs = null;
        PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stm.setString(1, entity.getEMail());
            stm.setString(2, entity.getPassword());
            stm.setString(3, entity.getForename());
            stm.setString(4, entity.getSurname());

            stm.executeUpdate();

            rs = stm.getGeneratedKeys();
        } catch(SQLException ex){
            logger.log(Level.SEVERE, "UserDAO - Error: Error in add function!", ex);
            return null;
        }

        return rs;
    }

    @Override
    public boolean update(User entity) throws SQLException {
        return false;
    }

    @Override
    public User select(String identifier) throws SQLException {
        User user = UserFactory.createEntity();

        String sql = String.format(
                "SELECT * " +
                "FROM %s.%s " +
                "WHERE %s = ?",
                Globals.DATABASE_NAME,
                super.table,
                Globals.TABLE_USER_EMAIL);

        try(PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            stm.setString(1, identifier);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next())
                    mapResultSetToEntity(rs, user);
            }
        } catch(SQLException ex){
            logger.log(Level.SEVERE, "UserDAO: Error in select function!", ex);
            return null;
        }

        return user;
    }

    @Override
    public boolean remove(User entity) {
        return false;
    }

    private void mapResultSetToEntity(ResultSet rs, User entity) throws SQLException {
        // Mapping
        entity.setEMail(rs.getString("email"));
        entity.setPassword(rs.getString("password"));

        entity.setForename(rs.getString("forename"));
        entity.setSurname(rs.getString("surname"));
    }
}