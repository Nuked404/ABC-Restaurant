package com.abc.dao;

import com.abc.model.Query;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryDAO {

	// Add a query
	public void addQuery(Query query) {
		String sql = "INSERT INTO query (user_id, query) VALUES (?, ?)";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			if (query.getUserId() != null) {
				statement.setInt(1, query.getUserId());
			} else {
				statement.setNull(1, Types.INTEGER);
			}

			statement.setString(2, query.getQuery());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Get queries by user ID
	public List<Query> getQueriesByUserId(int userId) {
		List<Query> queries = new ArrayList<>();
		String sql = "SELECT * FROM query WHERE user_id = ?";

		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {

			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				queries.add(new Query(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getString("query"),
						resultSet.getString("response")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return queries;
	}

	// Get all queries
	public List<Query> getAllQueries() {
		List<Query> queries = new ArrayList<>();
		String sql = "SELECT * FROM query";

		try (Connection connection = DBConnectionFactory.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				queries.add(new Query(resultSet.getInt("id"), resultSet.getObject("user_id", Integer.class),
						resultSet.getString("query"), resultSet.getString("response")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return queries;
	}
	
	public Query getQueryById(int id) {
	    Query query = null;
	    String sql = "SELECT * FROM query WHERE id = ?";

	    try (Connection connection = DBConnectionFactory.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

	        preparedStatement.setInt(1, id);
	        try (ResultSet resultSet = preparedStatement.executeQuery()) {
	            if (resultSet.next()) {
	                query = new Query(
	                    resultSet.getInt("id"),
	                    resultSet.getObject("user_id", Integer.class),
	                    resultSet.getString("query"),
	                    resultSet.getString("response")
	                );
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return query;
	}


	// Update a query
	public void updateQuery(int queryId, String query) {
		String sql = "UPDATE query SET query = ? WHERE id = ?";
		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, query);
			statement.setInt(2, queryId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Update the response of a query
	public void updateResponse(int queryId, String response) {
		String sql = "UPDATE query SET response = ? WHERE id = ?";
		try (Connection connection = DBConnectionFactory.getConnection();
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setString(1, response);
			statement.setInt(2, queryId);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Get unresponded queries
	public List<Query> getUnrespondedQueries() {
		List<Query> queries = new ArrayList<>();
		String sql = "SELECT * FROM query WHERE response IS NULL OR response = ''";

		try (Connection connection = DBConnectionFactory.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sql)) {

			while (resultSet.next()) {
				queries.add(new Query(resultSet.getInt("id"), resultSet.getObject("user_id", Integer.class),
						resultSet.getString("query"), resultSet.getString("response")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return queries;
	}

	// Delete a query
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
}
