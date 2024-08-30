package com.abc.controller;

import com.abc.model.Order;
import com.abc.model.OrderItem;
import com.abc.model.Branch;
import com.abc.model.MenuItem;
import com.abc.model.User;
import com.abc.service.OrderService;
import com.abc.service.OrderItemService;
import com.abc.service.BranchService;
import com.abc.service.MenuItemService;
import com.abc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Servlet implementation class ViewOrderController
 */
public class ViewOrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService;
    private OrderItemService orderItemService;
    private MenuItemService menuItemService;
    private UserService userService;
    private BranchService branchService; 
    
    private String mainFile = "WEB-INF/view/vieworder.jsp";
    //private String redirFile = "DashboardOrder";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewOrderController() {
        super();
        // TODO Auto-generated constructor stub
        orderService = OrderService.getInstance();
        orderItemService = OrderItemService.getInstance();
        menuItemService = MenuItemService.getInstance();
        userService = UserService.getInstance();
        branchService = BranchService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String orderIdParam = request.getParameter("id");
        if (orderIdParam != null && !orderIdParam.isEmpty()) {
            int orderId = Integer.parseInt(orderIdParam);

            Order order = orderService.getOrderById(orderId);
            List<OrderItem> orderItems = orderItemService.getOrderItemsByOrderId(orderId);

            // Fetch menu items for order items
            Map<Integer, MenuItem> menuItems = new HashMap<>();
            for (OrderItem orderItem : orderItems) {
                MenuItem menuItem;
				try {
					menuItem = menuItemService.getMenuItemById(orderItem.getMenuItemId());
					if (menuItem != null) {
	                    menuItems.put(menuItem.getId(), menuItem);
	                }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}                
            }

            // Fetch user details
            User user = userService.getUserById(order.getUserId());

            // Create a map for user details
            Map<Integer, User> userMap = new HashMap<>();
            userMap.put(user.getId(), user);
            
            Branch branch = branchService.getBranchById(user.getNearestLocation());            
            
            Map<Integer, Branch> branchMap = new HashMap<>();
            branchMap.put(branch.getId(), branch);

            request.setAttribute("order", order);
            request.setAttribute("orderItems", orderItems);
            request.setAttribute("menuItems", menuItems);
            request.setAttribute("userMap", userMap);
            request.setAttribute("branchMap", branchMap);

            request.getRequestDispatcher(mainFile).forward(request, response);
        } 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
