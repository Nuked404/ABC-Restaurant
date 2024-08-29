package com.abc.controller;

import com.abc.model.Order;
import com.abc.model.User;
import com.abc.enums.OrderStatus;
import com.abc.model.Branch;
import com.abc.service.OrderService;
import com.abc.service.UserService;
import com.abc.service.BranchService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

/**
 * Servlet implementation class OrdersController
 */
public class OrdersController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private OrderService orderService;
	private UserService userService;
	private BranchService branchService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OrdersController() {
		super();
		// TODO Auto-generated constructor stub
		orderService = OrderService.getInstance();
		userService = UserService.getInstance();
		branchService = BranchService.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Retrieve the current user's ID
        int userId = 3;//Integer.parseInt(request.getSession().getAttribute("userId").toString());

        // Fetch orders for the current user
        List<Order> orders = orderService.getOrdersByUserId(userId);
        Collections.reverse(orders);// For keeping the new ones at top
        Map<Integer, User> userMap = new HashMap<>();
        Map<Integer, Branch> branchMap = new HashMap<>();       
        
        int branchId = userService.getUserById(userId).getNearestLocation();
        User user = userService.getUserById(userId);
        Branch branch = branchService.getBranchById(branchId);

        userMap.put(userId, user);
        branchMap.put(branchId, branch);

        request.setAttribute("orders", orders);
        request.setAttribute("userMap", userMap);
        request.setAttribute("branchMap", branchMap);

        request.getRequestDispatcher("orders.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("cancelOrder")) {				
				int id = Integer.parseInt(request.getParameter("orderId"));
                // Handle update status
                OrderStatus status = OrderStatus.CANCELED;
                try {
					orderService.updateOrderStatus(id, status);
				} catch (SQLException e) {
		            request.setAttribute("errorMessage", e.getMessage());
		            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
		        }
            }
		}		
		
		doGet(request, response);
	}

}
