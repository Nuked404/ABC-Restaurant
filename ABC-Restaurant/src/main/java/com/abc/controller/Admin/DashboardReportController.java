package com.abc.controller.Admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.service.OrderService;
import com.google.gson.Gson;

/**
 * Servlet implementation class DashboardReportController
 */
public class DashboardReportController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private OrderService orderService;
	private String mainFile = "WEB-INF/view/admin/dashboardreport.jsp";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DashboardReportController() {
        super();
        // TODO Auto-generated constructor stub
        orderService = OrderService.getInstance();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			Map<LocalDate, BigDecimal> incomeData = orderService.getIncomeForPast7Days();
			Gson gson = new Gson();
			String incomeDataJson = gson.toJson(incomeData);
			request.setAttribute("incomeDataJson", incomeDataJson);
			Map<String, Map<String, Object>> soldItemsData = orderService.getSoldItemsForPast7Days();
			request.setAttribute("soldItemsData", soldItemsData);
			Map<Integer, Map<String, Object>> branchData = orderService.getBranchProfitAndCanceledCountForPast7Days();
			request.setAttribute("branchData", branchData);
			List<Map<String, Object>> topCustomers = orderService.getTop10CustomersForPast7Days();
			request.setAttribute("topCustomers", topCustomers);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.getRequestDispatcher(mainFile).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
