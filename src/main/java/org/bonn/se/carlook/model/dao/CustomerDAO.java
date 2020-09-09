package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.factory.CustomerFactory;
import org.bonn.se.carlook.model.objects.entity.Customer;
import org.bonn.se.carlook.process.control.exception.DatabaseConnectionError;
import org.bonn.se.carlook.services.util.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class CustomerDAO extends AbstractDAO<Customer>{

    private static CustomerDAO customerDAO = null;

    public static CustomerDAO getInstance() {
        if (customerDAO == null) {
            customerDAO = new CustomerDAO();
        }
        return customerDAO;
    }

    private CustomerDAO() {
        super.table = Globals.TABLE_CUSTOMER;
    }


    @Override
    public Customer add(Customer entity) {
        String sql = String.format(
            "INSERT INTO %s.%s " +
            "(%s) " +
            "VALUES(?);",
            Globals.DATABASE_NAME,
            super.table,
            Globals.TABLE_USER_IDENTIFIER);

        try(PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            if(stm == null)
                return null;

            stm.setInt(1, entity.getUserId());

            stm.executeUpdate();

            /*
            rs = stm.getGeneratedKeys();
            //TODO UNNÖTIG
            if (rs.next()) {
                entity.setUserId(rs.getInt("userid"));
            } else{
                logger.log(Level.SEVERE, "UserDAO - Error: No userid was found!");
                return null;
            }*/

        } catch(SQLException ex){
            logger.log(Level.SEVERE, "UserDAO - Error: Error in add function!", ex);
            return null;
        }

        return entity;
    }

    @Override
    public boolean update(Customer entity) {
        return false;
    }

    @Override
    public Customer select(String identifier) throws DatabaseConnectionError {
        Customer customer = CustomerFactory.createEntity();

        //TODO TEST SQL STATEMENT (WHERE CLAUSE?) same in salesman
        String sql = String.format(
            "SELECT * " +
            "FROM %s.%s " +
            "NATURAL JOIN %s.%s " +
            "WHERE %s = ?",
            Globals.DATABASE_NAME,
            Globals.TABLE_USER,
            Globals.DATABASE_NAME,
            super.table,
            Globals.TABLE_USER_EMAIL);

        try(PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)){
            if(stm == null)
                throw new DatabaseConnectionError();

            stm.setString(1, identifier);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next())
                    super.mapResultSetToUserEntity(rs, customer);
                else
                    return null;
            }
        } catch(SQLException ex){
            logger.log(Level.SEVERE, "UserDAO: Error in select function!", ex);
            return null;
        }

        return customer;
    }

    @Override
    public boolean remove(Customer entity) {
        return false;
    }
}