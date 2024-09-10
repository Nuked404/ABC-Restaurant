package com.abc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.model.Branch;
import com.abc.model.User;
import com.abc.service.BranchService;
import com.abc.service.UserService;

/**
 * Servlet implementation class ManageAccountController
 */
public class ManageAccountController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BranchService branchService;
	private UserService userService;
	
	private String mainFile = "WEB-INF/view/account.jsp";
	private String controllerUrl = "ManageAccount";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManageAccountController() {
        super();
        branchService = BranchService.getInstance();
		userService = UserService.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Branch> branchList = new ArrayList<>();
		try {
			branchList = branchService.getAllBranches();
			request.setAttribute("branches", branchList);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
			return;
		}
		
		int userId = ((User) request.getSession().getAttribute("loggedInUser")).getId();
		User user = userService.getUserById(userId);
        request.setAttribute("user", user);
		
		request.getRequestDispatcher(mainFile).forward(request, response);		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("updateUser")) {
				updateUser(request, response);
				return;
			} else if (action.equals("updateUserPassword")) {
				updateUserPassword(request, response);
				return;
			}
		}
		response.sendRedirect(controllerUrl);
		//doGet(request, response);
	}
	
	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form parameters
		int id = Integer.parseInt(request.getParameter("userID"));
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String addressLine1 = request.getParameter("addressLine1");
		String addressLine2 = request.getParameter("addressLine2");
		String city = request.getParameter("city");

		// Parsing the nearest location ID as an integer
		int nearestLocation = Integer.parseInt(request.getParameter("nearestLocation"));
		
		if (userService.checkIfEmailExists(email)) {
			response.sendRedirect(controllerUrl +"?error=2");			
			return;
		}

		// Create a new User object
		User newUser = new User();
		newUser.setId(id);
		newUser.setName(name);
		newUser.setEmail(email);
		newUser.setPhone(phone);
		newUser.setNearestLocation(nearestLocation);
		newUser.setAddressLine1(addressLine1);
		newUser.setAddressLine2(addressLine2);
		newUser.setCity(city);

		// Update the user using the service
		userService.updateUser(newUser);
	}
	
	private void updateUserPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Retrieve form parameters	
		int id = Integer.parseInt(request.getParameter("userID"));
		
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");		
		
		// Check if passwords match
		if (!password.equals(confirmPassword)) {
			response.sendRedirect(controllerUrl +"?error=1");			
			return;
		}

		// Add the user using the service
		userService.updatePassword(id, password);
		response.sendRedirect(controllerUrl);
	}

}
