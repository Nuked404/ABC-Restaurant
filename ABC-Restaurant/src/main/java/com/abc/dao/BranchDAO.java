package com.abc.dao;

import com.abc.model.Branch;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {

    // Add a new branch to the database
    public void addBranch(Branch branch) {
        String query = "INSERT INTO branch (location) VALUES (?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, branch.getLocation());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a branch by its ID
    public Branch getBranchById(int id) {
        Branch branch = null;
        String query = "SELECT * FROM branch WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                branch = new Branch(
                    resultSet.getInt("id"),
                    resultSet.getString("location")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branch;
    }

    // Retrieve all branches from the database
    public List<Branch> getAllBranches() {
        List<Branch> branches = new ArrayList<>();
        String query = "SELECT * FROM branch";

        try (Connection connection = DBConnectionFactory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                branches.add(new Branch(
                    resultSet.getInt("id"),
                    resultSet.getString("location")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branches;
    }

    // Update an existing branch in the database
    public void updateBranch(Branch branch) {
        String query = "UPDATE branch SET location = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, branch.getLocation());
            statement.setInt(2, branch.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete a branch from the database
    public void deleteBranch(int id) {
        String query = "DELETE FROM branch WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
