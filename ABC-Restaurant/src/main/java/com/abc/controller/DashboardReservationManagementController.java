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

import com.abc.enums.ReservationStatus;
import com.abc.model.Branch;
import com.abc.model.Reservation;
import com.abc.model.User;
import com.abc.service.BranchService;
import com.abc.service.ReservationService;
import com.abc.service.UserService;

/**
 * Servlet implementation class DashboardReservationManagementController
 */
public class DashboardReservationManagementController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private ReservationService reservationService;
	private BranchService branchService;
    private UserService userService;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardReservationManagementController() {
        super();
        // TODO Auto-generated constructor stub
        branchService = BranchService.getInstance();
		reservationService = ReservationService.getInstance();
		userService = UserService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String action = request.getParameter("action");

		if ("updateReservationStatus".equals(action)) {
			handleUpdateReservationStatus(request, response);
		}

		Map<Integer, User> userMap = new HashMap<>();
		Map<Integer, Branch> branchMap = new HashMap<>();

		List<Reservation> reservations = reservationService.getAllReservations();
		Collections.reverse(reservations);
		
		for (Reservation reservation : reservations) {
		    int userId = reservation.getUserId();
		    User user = userService.getUserById(userId);
		    Branch branch = branchService.getBranchById(user.getNearestLocation());

		    userMap.put(userId, user);
		    branchMap.put(user.getNearestLocation(), branch);
		}		
		
		
		request.setAttribute("userMap", userMap);
		request.setAttribute("branchMap", branchMap);
		request.setAttribute("reservations", reservations);
		request.setAttribute("reservationStatuses", ReservationStatus.values());

		request.getRequestDispatcher("reservationmanagement.jsp").forward(request, response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void handleUpdateReservationStatus(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int reservationId = Integer.parseInt(request.getParameter("reservationId"));
			String statusParam  = request.getParameter("status");
			ReservationStatus status = ReservationStatus.valueOf(statusParam.toUpperCase());
			reservationService.updateReservationStatus(reservationId, status);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//response.sendRedirect("DashboardReservationManagement");
	}

}
