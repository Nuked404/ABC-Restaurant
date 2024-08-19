package com.abc.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionFactory {

    public static Connection getConnection() {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            if (connection == null || connection.isClosed()) {
                throw new SQLException("Failed to establish a database connection.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving database connection", e);
        }
        return connection;
    }
}

