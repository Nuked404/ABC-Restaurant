package com.abc.controller.Admin;

import com.abc.model.Branch;
import com.abc.model.User;
import com.abc.enums.UserRole;
import com.abc.service.BranchService;
import com.abc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet implementation class DashboardEmployeeManagementController
 */
public class DashboardEmployeeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private UserService userService;
	private BranchService branchService;

	private String mainFile = "WEB-INF/view/admin/dashboardemployee.jsp";
	private String controllerUrl = "DashboardEmployee";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardEmployeeController() {
		super();
		userService = UserService.getInstance();
		branchService = BranchService.getInstance();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("editEmployee")) {
				int id = Integer.parseInt(request.getParameter("employeeId"));
			    User user = userService.getUserById(id);
			    request.setAttribute("uemployee", user);
			} else if (action.equals("deleteEmployee")) {
				deleteUser(request, response);
			}
		}

		List<Branch> branchList = new ArrayList<>();
		try {
			branchList = branchService.getAllBranches();
			request.setAttribute("branches", branchList);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
			return;
		}

		List<User> userList = userService.getUsersByRole(UserRole.STAFF); // Or other criteria
		Map<Integer, Branch> branchMap = new HashMap<>();		
		 for (User user : userList) {
	            Branch branch = branchService.getBranchById(user.getNearestLocation());
	            branchMap.put(user.getNearestLocation(), branch);
	        }
		
		request.setAttribute("employees", userList);
		request.setAttribute("branchMap", branchMap);

		request.getRequestDispatcher(mainFile).forward(request, response);
		// response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action.equals("addEmployee")) {
			addUser(request, response);
			return;
		} else if (action.equals("updateEmployee")) {
			updateUser(request, response);
			return;
		}

		doGet(request, response);
	}

	private void addUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("fullName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phoneNumber");
		int nearestLocation = Integer.parseInt(request.getParameter("branchLocation"));
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		if (!password.equals(confirmPassword)) {
			response.sendRedirect(controllerUrl +"?error=1");			
			return;
		}
		if (userService.checkIfEmailExists(email)) {
			response.sendRedirect(controllerUrl +"?error=2");			
			return;
		}

		User user = new User(name, email, phone, null, null, null, nearestLocation, UserRole.STAFF, password);
		userService.addUser(user);
		doGet(request, response);
	}

	private void updateUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employeeId"));
		String name = request.getParameter("fullName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phoneNumber");
		int nearestLocation = Integer.parseInt(request.getParameter("branchLocation"));

		User user = new User();
		user.setId(id);
		user.setName(name);
		user.setEmail(email);
		user.setPhone(phone);
		user.setNearestLocation(nearestLocation);	
		

		userService.updateStaff(user);

		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");

		if (password != null && !password.trim().isEmpty() && confirmPassword != null
				&& !confirmPassword.trim().isEmpty()) { // Checking if the passwords are empty
			if (!password.equals(confirmPassword)) {
				response.sendRedirect(controllerUrl +"?error=true");
				return;
			}
			userService.updatePassword(id, password); // Updating the password as well
		}
		doGet(request, response);
	}

	private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("employeeId"));
		userService.deleteUser(id);
	}

}
