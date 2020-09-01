package org.bonn.se.carlook.services.util;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConnection {
    private static JDBCConnection connection = null;
    private Logger logger;

    private static Connection conn;
    String url = "jdbc:postgresql://dumbo.inf.h-brs.de/iasani2s";

    public static JDBCConnection getInstance(){
        if (connection == null){
            connection = new JDBCConnection();
        }
        return connection;
    }

    private JDBCConnection(){
        logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
        this.initConnection();
    }

    public void initConnection() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database Error: Could not initialize a connection!", ex);
        }

        this.openConnection();
    }

    public void openConnection() {

        Properties props = new Properties();
        props.setProperty("user", "iasani2s");
        props.setProperty("password", "iasani2s");

        try{
            this.conn = DriverManager.getConnection(this.url, props);
        } catch(SQLException ex){
            logger.log(Level.SEVERE, "Database Error: Could not open connection!", ex);
        }
    }

    public Statement getStatement() {
        try{
            if(this.conn.isClosed()) {
                this.openConnection();
            }

            return this.conn.createStatement();
        }catch(SQLException ex){
            logger.log(Level.SEVERE, "Database Error: Could not create a statement!", ex);
            return null;
        }catch(Exception ex){
            logger.log(Level.SEVERE, "Database Error: There is no valid connection!", ex);
            return null;
        }
    }

    public PreparedStatement getPreparedStatement(String sql, int returnGeneratedKeys){
        try{
            if(this.conn.isClosed()) {
                this.openConnection();
            }

            return this.conn.prepareStatement(sql, returnGeneratedKeys);
        }catch(SQLException ex){
            logger.log(Level.SEVERE, "Database Error: Could not create a preparedStatement!", ex);
            return null;
        }catch(Exception ex){
            logger.log(Level.SEVERE, "Database Error: There is no valid connection!", ex);
            return null;
        }
    }

    public void closeConnection(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Database Error: Could not close connection!", ex);
        }
    }
}
