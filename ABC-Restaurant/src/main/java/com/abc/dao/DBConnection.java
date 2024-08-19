package com.abc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/abc_restaurant";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "12345";
    
    private static volatile DBConnection instance;
    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class.getName());

    private DBConnection() {
        if (instance != null) {
            throw new RuntimeException("Use getInstance() method to create.");
        }
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Database connection creation failed", e);
            throw new RuntimeException("Database connection creation failed", e);
        }
    }

    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "Failed to close database connection", e);
            } finally {
                connection = null;
            }
        }
    }
}