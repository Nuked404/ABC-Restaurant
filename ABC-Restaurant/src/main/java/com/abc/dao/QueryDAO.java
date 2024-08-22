package com.abc.dao;

import com.abc.model.Query;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDAO {

    public void addQuery(Query query) {
        String sql = "INSERT INTO query (user_id, name, email, query) VALUES (?, ?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            if (query.getUserId() != null) {
                statement.setInt(1, query.getUserId());
            } else {
                statement.setNull(1, Types.INTEGER);
            }

            statement.setString(2, query.getName());
            statement.setString(3, query.getEmail());
            statement.setString(4, query.getQuery());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Query> getQueriesByUserId(int userId) {
        List<Query> queries = new ArrayList<>();
        String sql = "SELECT * FROM query WHERE user_id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                queries.add(new Query(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("query"),
                    resultSet.getString("response")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }

    public List<Query> getAllExternalQueries() {
        List<Query> queries = new ArrayList<>();
        String sql = "SELECT * FROM query WHERE user_id IS NULL";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                queries.add(new Query(
                    resultSet.getInt("id"),
                    null,
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("query"),
                    resultSet.getString("response")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }

    public void updateQuery(Query query) {
        String sql = "UPDATE query SET response = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, query.getResponse());
            statement.setInt(2, query.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQuery(int id) {
        String sql = "DELETE FROM query WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<Query> getAllQueries() {
        List<Query> queries = new ArrayList<>();
        String sql = "SELECT * FROM query";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                queries.add(new Query(
                    resultSet.getInt("id"),
                    resultSet.getObject("user_id", Integer.class),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("query"),
                    resultSet.getString("response")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }
    
    public List<Query> getUnrespondedQueries() {
        List<Query> queries = new ArrayList<>();
        String sql = "SELECT * FROM query WHERE response IS NULL OR response = ''";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                queries.add(new Query(
                    resultSet.getInt("id"),
                    resultSet.getObject("user_id", Integer.class),
                    resultSet.getString("name"),
                    resultSet.getString("email"),
                    resultSet.getString("query"),
                    resultSet.getString("response")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return queries;
    }
}
