package com.abc.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.model.Query;
import com.abc.service.QueryService;

/**
 * Servlet implementation class QueryController
 */
public class QueryController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private QueryService queryService;
	private String controllerUrl = "Query";
	private String mainFile = "WEB-INF/view/query.jsp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public QueryController() {
		super();
		// TODO Auto-generated constructor stub
		queryService = QueryService.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
        if ("editQuery".equals(action)) {
            int queryId = Integer.parseInt(request.getParameter("queryId"));
            Query queryToUpdate = queryService.getQueryById(queryId);
            request.setAttribute("queryToUpdate", queryToUpdate);
        }
		
		
		Integer userId = 3;//(Integer) request.getSession().getAttribute("userId"); // Assuming user is logged in and userId
																				// is in session
		List<Query> userQueries = queryService.getQueriesByUserId(userId);
		Collections.reverse(userQueries);
		request.setAttribute("queries", userQueries);
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
		int userId = 3;//(Integer) request.getSession().getAttribute("userId"); // Assuming user is logged in and userId
																				// is in session
		
		String action = request.getParameter("action");
        if ("addQuery".equals(action)) {        	
            String queryText = request.getParameter("query");
            Query query = new Query();
            query.setUserId(userId);
    		query.setQuery(queryText);
    		queryService.addQuery(query);
        } else if ("updateQuery".equals(action)) {
            int queryId = Integer.parseInt(request.getParameter("queryId"));
            String queryText = request.getParameter("query");
            queryService.updateQuery(queryId, queryText);
        } else if ("deleteQuery".equals(action)) {
            int queryId = Integer.parseInt(request.getParameter("queryId"));
            queryService.deleteQuery(queryId);
        }

		response.sendRedirect(controllerUrl);
		
		//doGet(request, response);
	}

}
