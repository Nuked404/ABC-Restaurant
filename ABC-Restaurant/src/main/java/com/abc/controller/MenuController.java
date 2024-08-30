package com.abc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.model.MenuItem;
import com.abc.service.MenuItemService;
import com.abc.model.Cart;
import com.abc.model.OrderItem;

/**
 * Servlet implementation class MenuController
 */
public class MenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MenuItemService menuItemService;

	private String mainFile = "WEB-INF/view/menu.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MenuController() {
		super();
		// TODO Auto-generated constructor stub
		menuItemService = MenuItemService.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		listMenuItems(request, response);
		loadCart(request, response);

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
		cartActions(request, response);
		doGet(request, response);
	}

	protected void loadCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("localCart");
		int cartItemCount = (cart != null) ? cart.getTotalQuantity() : 0;
		request.setAttribute("cartItemCount", cartItemCount);
	}

	protected void cartActions(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("localCart");

		if (cart == null) {
			cart = new Cart();
			session.setAttribute("localCart", cart);
		}

		switch (action) {
		case "addToCart":
			int menuItemId = Integer.parseInt(request.getParameter("menuItemId"));
			int qty = Integer.parseInt(request.getParameter("quantity"));

			OrderItem item = new OrderItem();
			item.setMenuItemId(menuItemId);
			item.setQty(qty);

			cart.addItem(item);
			break;

		case "removeFromCart":
			int removeItemId = Integer.parseInt(request.getParameter("menuItemId"));
			cart.removeItem(removeItemId);
			break;

		case "clearCart":
			cart.clearCart();
			break;
		}
	}

	private void listMenuItems(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			List<MenuItem> menuItems = menuItemService.getAllMenuItems();
			request.setAttribute("menuItems", menuItems);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
		}
	}
}
