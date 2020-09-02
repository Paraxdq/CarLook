package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.factory.UserFactory;
import org.bonn.se.carlook.model.objects.entity.User;
import org.bonn.se.carlook.services.util.GlobalHelper;
import org.bonn.se.carlook.services.util.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class UserDAO extends AbstractDAO<User> {

    private static UserDAO userDAO = null;

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    private UserDAO() {
        super.table = Globals.TABLE_USER;
    }

    @Override
    public User add(User entity) {
        String sql = String.format(
                "INSERT INTO %s.%s " +
                "(%s, %s, %s, %s) " +
                "VALUES(?, ?, ?, ?);",
                Globals.DATABASE_NAME,
                super.table,
                Globals.TABLE_USER_EMAIL,
                Globals.TABLE_USER_PASSWORD,
                Globals.TABLE_USER_FORENAME,
                Globals.TABLE_USER_SURNAME);

        ResultSet rs = null;

        try(PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            if(stm == null)
                return null;

            stm.setString(1, entity.getEMail());
            stm.setString(2, entity.getPassword());
            stm.setString(3, entity.getForename());
            stm.setString(4, entity.getSurname());

            stm.executeUpdate();

            rs = stm.getGeneratedKeys();

            if (rs.next()) {
                entity.setUserId(rs.getInt("userid"));
            } else{
                logger.log(Level.SEVERE, "UserDAO - Error: No userid was found!");
                return null;
            }

        } catch(SQLException ex){
            logger.log(Level.SEVERE, "UserDAO - Error: Error in add function!", ex);
            return null;
        }

        return entity;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public User select(String identifier) {
        User user = UserFactory.createEntity();

        String sql = String.format(
                "SELECT * " +
                "FROM %s.%s " +
                "WHERE %s = ?",
                Globals.DATABASE_NAME,
                super.table,
                Globals.TABLE_USER_EMAIL);

        try(PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            if(stm == null)
                return null;

            stm.setString(1, identifier);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next())
                    super.mapResultSetToEntity(rs, user);
                else
                    return null;
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

}
