package com.abc.dao;

import com.abc.model.ExternalQuery;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExternalQueryDAO {
    
    public void addExternalQuery(ExternalQuery externalQuery) {
        String query = "INSERT INTO external_query (name, email, query) VALUES (?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, externalQuery.getName());
            statement.setString(2, externalQuery.getEmail());
            statement.setString(3, externalQuery.getQuery());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<ExternalQuery> getAllExternalQueries() {
        List<ExternalQuery> externalQueries = new ArrayList<>();
        String query = "SELECT * FROM external_query";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
             
            while (resultSet.next()) {
                externalQueries.add(new ExternalQuery(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("query")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return externalQueries;
    }

    public void deleteExternalQuery(int id) {
        String query = "DELETE FROM external_query WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
