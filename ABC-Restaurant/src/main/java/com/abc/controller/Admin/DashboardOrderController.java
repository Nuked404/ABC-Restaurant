package com.abc.controller.Admin;

import com.abc.model.Order;
import com.abc.model.User;
import com.abc.model.Branch;
import com.abc.enums.OrderStatus;
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
 * Servlet implementation class DashboardOrderController
 */
public class DashboardOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService; 
    private UserService userService;
    private BranchService branchService; 

    
    private String mainFile = "WEB-INF/view/admin/dashboardorder.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardOrderController() {
        super();
        // TODO Auto-generated constructor stub
        orderService = OrderService.getInstance();
        userService = UserService.getInstance();
        branchService = BranchService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("updateOrderStatus")) {				
				int id = Integer.parseInt(request.getParameter("id"));
		        String statusParam = request.getParameter("status");
                // Handle update status
                OrderStatus status = OrderStatus.valueOf(statusParam.toUpperCase());
                try {
					orderService.updateOrderStatus(id, status);
				} catch (SQLException e) {
		            request.setAttribute("errorMessage", e.getMessage());
		            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
		        }
            }
		}
		
		
		List<Order> orders = orderService.getAllOrders();
        Map<Integer, User> userMap = new HashMap<>();
        Map<Integer, Branch> branchMap = new HashMap<>();
        Collections.reverse(orders);// For keeping the new ones at top

        // Fetch user details for each order
        for (Order order : orders) {
            int userId = order.getUserId();
            User user = userService.getUserById(userId);
            Branch branch = branchService.getBranchById(user.getNearestLocation());
            userMap.put(userId, user);
            branchMap.put(user.getNearestLocation(), branch);
        }

        request.setAttribute("orders", orders);
        request.setAttribute("userMap", userMap);
        request.setAttribute("branchMap", branchMap);
        request.setAttribute("orderStatuses", OrderStatus.values()); // For the action buttons

        request.getRequestDispatcher(mainFile).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		
		doGet(request, response);
	}

}
