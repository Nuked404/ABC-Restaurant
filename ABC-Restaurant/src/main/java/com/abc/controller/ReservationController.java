package com.abc.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.enums.TimeFrame;
import com.abc.model.Branch;
import com.abc.model.Reservation;
import com.abc.service.BranchService;
import com.abc.service.ReservationService;

/**
 * Servlet implementation class ReservationController
 */
public class ReservationController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private ReservationService reservationService;
	private BranchService branchService;
	
	private String mainFile = "WEB-INF/view/reservation.jsp";
	private String controllerUrl = "Reservation";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReservationController() {
		super();
		// TODO Auto-generated constructor stub
		branchService = BranchService.getInstance();
		reservationService = ReservationService.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());

		String action = request.getParameter("action");

		if ("checkAvailability".equals(action)) {
			handleCheckAvailability(request, response);
			return;
		} else if ("editReservation".equals(action)) {
			String reservationIdStr = request.getParameter("reservationId");
			if (reservationIdStr != null) {
				int reservationId = Integer.parseInt(reservationIdStr);
				Reservation reservation = reservationService.getReservationById(reservationId);
				request.setAttribute("reservationToUpdate", reservation);
			}
		}

		List<Branch> branches;
		try {
			// Fetch all branches and time frames for dropdowns
			branches = branchService.getAllBranches();
			request.setAttribute("branches", branches);
			// Prepare branch map for lookup
			Map<Integer, Branch> branchMap = branches.stream().collect(Collectors.toMap(Branch::getId, b -> b));
			request.setAttribute("branchMap", branchMap);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<TimeFrame> timeFrames = Arrays.asList(TimeFrame.values());
		request.setAttribute("timeFrames", timeFrames);

		// Fetch reservations by user ID for listing
		int userId = 3;// Integer.parseInt(request.getSession().getAttribute("userId").toString());
		List<Reservation> reservations = reservationService.getReservationsByUserId(userId);
		Collections.reverse(reservations);
		request.setAttribute("reservations", reservations);

		request.getRequestDispatcher(mainFile).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// doGet(request, response);

		String action = request.getParameter("action");

		if ("makeReservation".equals(action)) {
			handleCreateReservation(request, response);
		} else if ("updateReservation".equals(action)) {
			handleUpdateReservation(request, response);
		} else if ("cancelReservation".equals(action)) {
			handleDeleteReservation(request, response);
		} else {
			response.sendRedirect(controllerUrl);
		}
	}

	private void handleCheckAvailability(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int branchId = Integer.parseInt(request.getParameter("branchId"));
			LocalDate date = LocalDate.parse(request.getParameter("reservationDate"));
			TimeFrame timeFrame = TimeFrame.valueOf(request.getParameter("timeFrame"));

			int availableSeats = reservationService.getAvailableSeats(branchId, date, timeFrame);

			// Respond with JSON
			response.setContentType("application/json");
			response.getWriter().write("{\"availableSeats\": " + availableSeats + "}");
		} catch (Exception e) {
			// Handle errors and send a proper error message in JSON format
			response.setContentType("application/json");
			response.getWriter().write("{\"error\": \"Invalid request\"}");
		}
	}

	private void handleCreateReservation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userId = 3;// Integer.parseInt(request.getSession().getAttribute("userId").toString());
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		LocalDate date = LocalDate.parse(request.getParameter("reservationDate"));
		TimeFrame timeFrame = TimeFrame.valueOf(request.getParameter("timeFrame"));
		int seatCount = Integer.parseInt(request.getParameter("seatCount"));
		String notes = request.getParameter("notes");

		Reservation reservation = new Reservation(0, userId, branchId, date, timeFrame, seatCount, notes);
		reservationService.addReservation(reservation);

		response.sendRedirect(controllerUrl);
	}

	private void handleUpdateReservation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int reservationId = Integer.parseInt(request.getParameter("reservationId"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		LocalDate date = LocalDate.parse(request.getParameter("reservationDate"));
		TimeFrame timeFrame = TimeFrame.valueOf(request.getParameter("timeFrame"));
		int seatCount = Integer.parseInt(request.getParameter("seatCount"));
		String notes = request.getParameter("notes");

		Reservation reservation = new Reservation(reservationId, 0, branchId, date, timeFrame, seatCount, notes);
		reservationService.updateReservation(reservation);

		response.sendRedirect(controllerUrl);
	}

	private void handleDeleteReservation(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int reservationId = Integer.parseInt(request.getParameter("reservationId"));
		reservationService.deleteReservation(reservationId);

		response.sendRedirect(controllerUrl);
	}

}
