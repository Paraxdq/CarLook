package org.bonn.se.carlook.model.dao;

import org.bonn.se.carlook.model.objects.entity.Salesman;
import org.bonn.se.carlook.services.util.Globals;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public ResultSet add(Salesman entity) throws SQLException {
        return null;
    }

    @Override
    public boolean update(Salesman entity) throws SQLException {
        return false;
    }

    @Override
    public Salesman select(String identifier) throws SQLException {
        return null;
    }

    @Override
    public boolean remove(Salesman entity) {
        return false;
    }
}
