package com.abc.dao;

import com.abc.model.User;
import com.abc.enums.UserRole;
import com.abc.util.DBConnectionFactory;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Add a new user with hashed password
    public void addUser(User user) {
        String query = "INSERT INTO user (name, email, phone, address_line1, address_line2, city, nearest_location, role, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            String hashedPassword = hashPassword(user.getPassword());

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getAddressLine1()); // New field
            statement.setString(5, user.getAddressLine2()); // New field
            statement.setString(6, user.getCity()); // New field
            statement.setInt(7, user.getNearestLocation());
            statement.setString(8, user.getRole().name());
            statement.setString(9, hashedPassword);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a user by ID
    public User getUserById(int id) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                user = new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address_line1"), // New field
                        resultSet.getString("address_line2"), // New field
                        resultSet.getString("city"), // New field
                        resultSet.getInt("nearest_location"),
                        UserRole.valueOf(resultSet.getString("role")),
                        ""
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Authenticate user by email and password
    public User authenticateUser(String email, String password) {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String storedHash = resultSet.getString("password");

                if (verifyPassword(password, storedHash)) {
                    user = new User(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("phone"),
                            resultSet.getString("address_line1"), // New field
                            resultSet.getString("address_line2"), // New field
                            resultSet.getString("city"), // New field
                            resultSet.getInt("nearest_location"),
                            UserRole.valueOf(resultSet.getString("role")),
                            storedHash
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    // Get users by role
    public List<User> getUsersByRole(UserRole role) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM user WHERE role = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, role.name());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                users.add(new User(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("phone"),
                        resultSet.getString("address_line1"), // New field
                        resultSet.getString("address_line2"), // New field
                        resultSet.getString("city"), // New field
                        resultSet.getInt("nearest_location"),
                        UserRole.valueOf(resultSet.getString("role")),
                        ""
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    // Update user information
    public void updateUser(User user) {
        String query = "UPDATE user SET name = ?, email = ?, phone = ?, address_line1 = ?, address_line2 = ?, city = ?, nearest_location = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getAddressLine1()); // New field
            statement.setString(5, user.getAddressLine2()); // New field
            statement.setString(6, user.getCity()); // New field
            statement.setInt(7, user.getNearestLocation());
            statement.setInt(8, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void updateStaff(User user) {
        String query = "UPDATE user SET name = ?, email = ?, phone = ?, nearest_location = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setInt(4, user.getNearestLocation());
            statement.setInt(5, user.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Update user password
    public void updatePassword(int userId, String newPassword) {
        String query = "UPDATE user SET password = ? WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            String hashedPassword = hashPassword(newPassword);
            statement.setString(1, hashedPassword);
            statement.setInt(2, userId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete user
    public void deleteUser(int id) {
        String query = "DELETE FROM user WHERE id = ?";

        try (Connection connection = DBConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean verifyPassword(String password, String hashedPassword) {
        String hashedInputPassword = hashPassword(password);
        return hashedInputPassword.equals(hashedPassword);
    }

}
