package com.abc.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.abc.enums.UserRole;
import com.abc.service.UserService;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
public class AuthenticationFilter extends HttpFilter implements Filter {

	private UserService userService;
	private String accessDeniedPage = "WEB-INF/view/accessdenied.jsp";

	/**
	 * @see HttpFilter#HttpFilter()
	 */
	public AuthenticationFilter() {
		super();
		// TODO Auto-generated constructor stub
		this.userService = UserService.getInstance();
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession(false);
		String debugURI = httpRequest.getRequestURI();

		// Public API Calls
		String loginURI = httpRequest.getContextPath() + "/Login";
		boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
		boolean isRegisterRequest = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/Register");

		// Public pages
		boolean isMainPage = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/index.jsp");
		boolean isGalleryPage = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/gallery.jsp");
		boolean isMenuPage = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/Menu");

		// Public resources
		boolean isStaticResource1 = httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/images/");
		boolean isStaticResource2 = httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/includes/");
		
		// Logged in shared resources
		boolean isViewOrderRequest = httpRequest.getRequestURI().equals(httpRequest.getContextPath() + "/ViewOrder");

		// Dashboard Filters
		boolean isDashboardCall = httpRequest.getRequestURI().startsWith(httpRequest.getContextPath() + "/Dashboard");
		boolean isDashboardReports = httpRequest.getRequestURI()
				.equals(httpRequest.getContextPath() + "/DashboardReport");
		boolean isDashboardBranches = httpRequest.getRequestURI()
				.equals(httpRequest.getContextPath() + "/DashboardLocation");
		boolean isDashboardEmployee = httpRequest.getRequestURI() // Employee management only should be accessible for the admin
				.equals(httpRequest.getContextPath() + "/DashboardEmployee");

		// Check login

		boolean forceChecks = false; // For debugsss // Set this to true to bypass auth checks on most parts

		boolean isLoggedIn = forceChecks ? true : userService.isLoggedIn(session);
		boolean isLoggedInAsAdmin = forceChecks ? true
				: isLoggedIn ? userService.hasRole(session, UserRole.ADMIN) : false;
		boolean isLoggedInAsEmployee = forceChecks ? true
				: isLoggedIn ? userService.hasRole(session, UserRole.STAFF) : false;
		boolean isLoggedInAsCustomer = forceChecks ? true
				: isLoggedIn ? userService.hasRole(session, UserRole.CUSTOMER) : false;

		if (isLoginRequest || isRegisterRequest) {
			chain.doFilter(request, response); // Login and Register Controllers + API
		} else if (isMainPage || isGalleryPage || isMenuPage) {
			chain.doFilter(request, response); // Public Pages
		} else if (isStaticResource1 || isStaticResource2) {
			chain.doFilter(request, response); // Page resources
		} else if (isLoggedIn) {
			
			// Shared resources amongst login first
			if(isViewOrderRequest)
			{
				chain.doFilter(request, response);
			}
			
			if (isDashboardCall) {
				if (isLoggedInAsAdmin || isLoggedInAsEmployee) {
					// Dashboard Pages
					if (isDashboardReports || isDashboardEmployee || isDashboardBranches) {
						if (isLoggedInAsAdmin) {
							chain.doFilter(request, response); // Reports and employees only available for the admins ;P
						} else {
							request.getRequestDispatcher(accessDeniedPage).forward(request, response);
						}
					} else {
						chain.doFilter(request, response); // Employee pages
					}
				} else {
					request.getRequestDispatcher(accessDeniedPage).forward(request, response);
				}

			} else {
				// Customer Pages ( Don't let Emps and Admins since they have null values on
				// some fields)
				if (isLoggedInAsCustomer) {
					chain.doFilter(request, response);
				} else {
					request.getRequestDispatcher(accessDeniedPage).forward(request, response);
				}
			}
		} else {
			request.getRequestDispatcher(accessDeniedPage).forward(request, response);
		}

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
