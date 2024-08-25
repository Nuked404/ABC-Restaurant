package com.abc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.model.MenuItem;
import com.abc.service.MenuItemService;

/**
 * Servlet implementation class MenuController
 */
public class MenuController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MenuItemService menuItemService;

	private String mainFile = "menu.jsp";

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
		doGet(request, response);
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
