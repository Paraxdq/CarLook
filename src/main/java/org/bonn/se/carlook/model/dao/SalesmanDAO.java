package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.factory.SalesmanFactory;
import org.bonn.se.carlook.model.factory.UserFactory;
import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.model.objects.entity.User;
import org.bonn.se.carlook.services.util.Globals;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class SalesmanDAO extends AbstractDAO<Salesman> {

    private static SalesmanDAO salesmanDAO = null;

    public static SalesmanDAO getInstance() {
        if (salesmanDAO == null) {
            salesmanDAO = new SalesmanDAO();
        }
        return salesmanDAO;
    }

    private SalesmanDAO() {
        super.table = Globals.TABLE_SALESMAN;
    }

    @Override
    public Salesman add(Salesman entity) {
        String sql = String.format(
                "INSERT INTO %s.%s " +
                        "(%s) " +
                        "VALUES(?);",
                Globals.DATABASE_NAME,
                super.table,
                Globals.TABLE_USER_IDENTIFIER);

        ResultSet rs = null;
        PreparedStatement stm = connection.getPreparedStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        try {
            stm.setInt(1, entity.getUserId());

            stm.executeUpdate();

            rs = stm.getGeneratedKeys();

            //TODO UNNÖTIG
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
    public boolean update(Salesman entity) {
        return false;
    }

    @Override
    public Salesman select(String identifier) {
        Salesman salesman = SalesmanFactory.createEntity();

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
            stm.setString(1, identifier);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next())
                    super.mapResultSetToEntity(rs, salesman);
                else
                    return null;
            }
        } catch(SQLException ex){
            logger.log(Level.SEVERE, "UserDAO: Error in select function!", ex);
            return null;
        }

        return salesman;
    }

    @Override
    public boolean remove(Salesman entity) {
        return false;
    }

}
