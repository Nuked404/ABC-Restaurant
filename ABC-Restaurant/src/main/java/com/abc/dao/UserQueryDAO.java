package com.abc.dao;

import com.abc.model.UserQuery;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserQueryDAO {
    
    public void addUserQuery(UserQuery userQuery) {
        String query = "INSERT INTO user_query (user_id, query) VALUES (?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, userQuery.getUserId());
            statement.setString(2, userQuery.getQuery());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserQuery> getUserQueriesByUserId(int userId) {
        List<UserQuery> userQueries = new ArrayList<>();
        String query = "SELECT * FROM user_query WHERE user_id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                userQueries.add(new UserQuery(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("query"),
                    resultSet.getString("response")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return userQueries;
    }

    public void updateUserQuery(UserQuery userQuery) {
        String query = "UPDATE user_query SET response = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setString(1, userQuery.getResponse());
            statement.setInt(2, userQuery.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUserQuery(int id) {
        String query = "DELETE FROM user_query WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
             
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
