package com.abc.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.model.Branch;
import com.abc.model.Query;
import com.abc.model.User;
import com.abc.service.BranchService;
import com.abc.service.QueryService;
import com.abc.service.UserService;

/**
 * Servlet implementation class DashboardQueryManagementController
 */
public class DashboardQueryManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private QueryService queryService;
	private UserService userService;
    private BranchService branchService; 
    private String controllerUrl = "DashboardQueryManagement";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardQueryManagementController() {
        super();
        // TODO Auto-generated constructor stub
        queryService = QueryService.getInstance();
        userService = UserService.getInstance();
        branchService = BranchService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Query> userQueries = queryService.getAllQueries();
        Collections.reverse(userQueries);
        
        Map<Integer, User> userMap = new HashMap<>();
        Map<Integer, Branch> branchMap = new HashMap<>();
        for (Query query : userQueries) {
            User user = userService.getUserById(query.getUserId());
            userMap.put(query.getUserId(), user);
            Branch branch = branchService.getBranchById(user.getNearestLocation());
            userMap.put(user.getId(), user);
            branchMap.put(user.getNearestLocation(), branch);
        }
        
        request.setAttribute("queries", userQueries);
        request.setAttribute("userMap", userMap);
        request.setAttribute("branchMap", branchMap);
        request.getRequestDispatcher("querymanagement.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		String action = request.getParameter("action");

        if ("updateResponse".equals(action)) {
            int queryId = Integer.parseInt(request.getParameter("queryId"));
            String responseText = request.getParameter("response");
            queryService.updateQueryResponse(queryId, responseText);
        } else if ("deleteQuery".equals(action)) {
            int queryId = Integer.parseInt(request.getParameter("queryId"));
            queryService.deleteQuery(queryId);
        }

        response.sendRedirect(controllerUrl);
	}

}
