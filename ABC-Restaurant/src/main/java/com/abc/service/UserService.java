package com.abc.service;

import com.abc.dao.UserDAO;
import com.abc.model.User;
import com.abc.enums.UserRole;

import java.util.List;

public class UserService {

    // Singleton instance of UserService
    private static UserService instance;
    private UserDAO userDAO;

    // Private constructor to prevent instantiation
    private UserService() {
        this.userDAO = new UserDAO();
    }

    // Static method to get the singleton instance
    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    // Method to add a User
    public void addUser(User user) {
        userDAO.addUser(user);
    }

    // Method to get a User by ID
    public User getUserById(int id) {
        return userDAO.getUserById(id);
    }

    // Method to authenticate a User
    public User authenticateUser(String email, String password) {
        return userDAO.authenticateUser(email, password);
    }

    // Method to get Users by Role
    public List<User> getUsersByRole(UserRole role) {
        return userDAO.getUsersByRole(role);
    }

    // Method to update a User
    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    // Method to update a User's Password
    public void updatePassword(int userId, String newPassword) {
        userDAO.updatePassword(userId, newPassword);
    }

    // Method to delete a User by ID
    public void deleteUser(int id) {
        userDAO.deleteUser(id);
    }
}
