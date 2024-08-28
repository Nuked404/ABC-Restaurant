package com.abc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.abc.model.Cart;
import com.abc.model.MenuItem;
import com.abc.model.OrderItem;
import com.abc.service.MenuItemService;

import javax.servlet.http.HttpSession;

import com.abc.model.Order;
import com.abc.enums.OrderStatus;
import com.abc.service.OrderService;
import com.abc.service.OrderItemService;

/**
 * Servlet implementation class CartController
 */
public class CartController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private MenuItemService menuItemService;
    private OrderService orderService;
    private OrderItemService orderItemService;
    
    private String mainFile = "cart.jsp";

    public CartController() {
        super();
        // Use the singleton instance of MenuItemService
        this.menuItemService = MenuItemService.getInstance();
        this.orderService = OrderService.getInstance();
        this.orderItemService = OrderItemService.getInstance();
    }

    /**
     * Handles the GET requests for viewing the cart.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = getCartFromSession(request);
        
        try {
            // Get all menu items to calculate the total and show item details
            List<MenuItem> menuItems = menuItemService.getAllMenuItems();
            BigDecimal total = cart.calculateTotal(menuItems);

            // Store attributes in request scope for JSP to access
            request.setAttribute("orderItems", cart.getItems());
            request.setAttribute("menuItems", menuItems);
            request.setAttribute("total", total);

            // Forward to the cart view
            request.getRequestDispatcher(mainFile).forward(request, response);
        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error retrieving menu items: " + e.getMessage());
            request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
        }
    }

    /**
     * Handles POST requests for modifying the cart.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Cart cart = getCartFromSession(request);

        if (action != null) {
            switch (action) {
                case "remove":
                    removeItemFromCart(request, cart);
                    break;
                case "clear":
                    cart.clearCart();
                    break;
                case "checkout":
                    checkout(request, response);
                    break;
            }
        }

        // Save the cart back to the session and redirect to the cart page
        request.getSession().setAttribute("localCart", cart);
        response.sendRedirect("CartController");
    }

    /**
     * Helper method to get or create a cart in the session.
     */
    private Cart getCartFromSession(HttpServletRequest request) {
        Cart cart = (Cart) request.getSession().getAttribute("localCart");
        if (cart == null) {
            cart = new Cart();
            request.getSession().setAttribute("localCart", cart);
        }
        return cart;
    }

    /**
     * Removes an item from the cart.
     */
    private void removeItemFromCart(HttpServletRequest request, Cart cart) {
        int menuItemId = Integer.parseInt(request.getParameter("menuItemId"));
        cart.removeItem(menuItemId);
    }
    
    private void checkout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("localCart");
        int userId = 3 ;//(int) session.getAttribute("userId"); // Assuming user ID is stored in session

        if (cart != null && !cart.getItems().isEmpty()) {
            try {
                Order order = new Order();
                order.setUserId(userId);
                order.setStatus(OrderStatus.PENDING);
                List<MenuItem> menuItems = menuItemService.getAllMenuItems();
                order.setTotal(cart.calculateTotal(menuItems)); // Calculate total using the cart items

                // Add order and get generated ID
                int generatedOrderId = orderService.addOrder(order); 
                order.setId(generatedOrderId); // Set the ID to the Order object

                for (OrderItem item : cart.getItems()) {
                    item.setOrderId(order.getId()); // Now we have the correct order ID
                    orderItemService.addOrderItem(item);
                }

                cart.clearCart();
                session.setAttribute("localCart", cart);
                //response.sendRedirect("OrderController?action=list");

            } catch (SQLException e) {
                request.setAttribute("errorMessage", "Error during checkout: " + e.getMessage());
                request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorMessage", "Cart is empty. Cannot checkout.");
            request.getRequestDispatcher(mainFile).forward(request, response);
        }
    }

}
