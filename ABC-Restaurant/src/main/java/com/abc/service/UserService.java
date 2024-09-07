package com.abc.service;

import com.abc.dao.UserDAO;
import com.abc.model.User;
import com.abc.enums.UserRole;

import java.util.List;

import javax.servlet.http.HttpSession;

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
	
	// Service wrapper to check if email exists
    public boolean checkIfEmailExists(String email) {
        return userDAO.emailExists(email);
    }

	// Method to add a User
	public void addUser(User user) {
		userDAO.addUser(user);
	}

	// Method to get a User by ID
	public User getUserById(int id) {
		return userDAO.getUserById(id);
	}

	// Method to get Users by Role
	public List<User> getUsersByRole(UserRole role) {
		return userDAO.getUsersByRole(role);
	}

	// Method to update a Staff
	public void updateStaff(User user) {
		userDAO.updateStaff(user);
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

	// Authenticate user and start session
	public boolean authenticateUser(HttpSession session, String email, String password) {
		User user = userDAO.authenticateUser(email, password);
		if (user != null) {
			session.setAttribute("loggedInUser", user);
			return true;
		}
		return false;
	}

	public boolean hasRole(HttpSession session, UserRole role) {
		if (isLoggedIn(session)) {
			User loggedInUser = (User) session.getAttribute("loggedInUser");
			return loggedInUser.getRole() == role;
		}
		return false;
	}

	public boolean isLoggedIn(HttpSession session) {
		return session != null && session.getAttribute("loggedInUser") != null;
	}

	public void logoutUser(HttpSession session) {
		if (session != null) {
			session.invalidate(); // Invalidate the session to log out the user
		}
	}	
	
	public int getLoggedInUserID(HttpSession session)
	{
		User loggedInUser = getLoggedInUser(session);
		if(loggedInUser != null)
		{
			return loggedInUser.getId();
		}
		return -1;
	}
	
	public User getLoggedInUser(HttpSession session) {
        if (isLoggedIn(session)) {
            return (User) session.getAttribute("loggedInUser");
        }
        return null;
    }
}
