package com.abc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.model.Branch;
import com.abc.service.BranchService;


/**
 * Servlet implementation class DashboardLocationController
 */
public class DashboardLocationController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private BranchService branchService;
	
	private String mainFile = "WEB-INF/view/admin/dashboardlocation.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardLocationController() {
        super();
        branchService = BranchService.getInstance();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		List<Branch> branchList = new ArrayList<>();
		try {
			branchList = branchService.getAllBranches();
			request.setAttribute("branches", branchList);
		} catch (SQLException e) {
			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
			return;
		}

		Branch updateBranch = new Branch();
		String action = request.getParameter("action");
		if (action != null && action.equals("editBranch")) {
			try {
				int id = Integer.parseInt(request.getParameter("branchId"));
				updateBranch = branchService.getBranchById(id);
				request.setAttribute("ubranch", updateBranch);
			} catch (Exception e) { // Just to be sure
				request.setAttribute("errorMessage", e.getMessage());
				request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
				return;
			}
		}
		
		request.getRequestDispatcher(mainFile).forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equals("addBranch")) {
				addBranch(request, response);
			} else if (action.equals("deleteBranch")) {
				deleteBranch(request, response);
			} else if (action.equals("updateBranch")) {
				try {
					updateBranch(request, response);
				} catch (ServletException | IOException e) {
					request.setAttribute("errorMessage", e.getMessage());
					request.getRequestDispatcher("WEB-INF/view/error.jsp").forward(request, response);
					return;
				}
			}
		}
		
		doGet(request, response);
	}
	
	private void addBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String location = request.getParameter("location");
	    int maxSeats = Integer.parseInt(request.getParameter("maxSeats"));  // Get maxSeats from form

	    Branch branch = new Branch();
	    branch.setLocation(location);
	    branch.setMaxSeats(maxSeats);  // Set maxSeats

	    branchService.addBranch(branch);
	    //response.sendRedirect("branch?action=list");
	}

	private void deleteBranch(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("branchId"));
		branchService.deleteBranch(id);
		// response.sendRedirect("branch?action=list");
	}

	private void updateBranch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    int id = Integer.parseInt(request.getParameter("branchId"));
	    String location = request.getParameter("location");
	    int maxSeats = Integer.parseInt(request.getParameter("maxSeats"));  // Get maxSeats from form

	    Branch branch = new Branch();
	    branch.setId(id);
	    branch.setLocation(location);
	    branch.setMaxSeats(maxSeats);  // Update maxSeats

	    branchService.updateBranch(branch);
	    //response.sendRedirect("branch?action=list");
	}

}
