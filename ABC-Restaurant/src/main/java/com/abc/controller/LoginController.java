package com.abc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.enums.UserRole;
import com.abc.model.User;
import com.abc.service.UserService;

/**
 * Servlet implementation class LoginController
 */
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private String mainFile = "WEB-INF/view/login.jsp";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
        this.userService = UserService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher(mainFile).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		handleLogin(request, response);
	}
	
	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    HttpSession session = request.getSession(false);
	    if (userService.authenticateUser(session, email, password)) {
	    	if(userService.hasRole(session, UserRole.ADMIN))
	    	{
	    		response.sendRedirect("DashboardReport");
	    	}else if(userService.hasRole(session, UserRole.STAFF))
	    	{
	    		response.sendRedirect("DashboardMenu");
	    	}else if(userService.hasRole(session, UserRole.CUSTOMER))
	    	{
	    		response.sendRedirect("index.jsp"); // For customers
	    	}else
	    	{
	    		response.sendRedirect(mainFile +"?error=true");
	    	}
            
        } else {
            response.sendRedirect(mainFile +"?error=true");
        }
	}

}
