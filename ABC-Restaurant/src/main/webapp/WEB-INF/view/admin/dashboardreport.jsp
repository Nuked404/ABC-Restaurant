<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Analytics Dashboard - ABC Restaurant</title>
<%@ include file="/includes/admin/externstyles.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>

	<%@ include file="/includes/admin/navbar.jsp"%>

	<div class="container mt-5">
		<h1>Admin Analytics</h1>


		<!-- Graph Display -->
		<h3 class="mt-4">Income (Last 7 Days)</h3>
		<canvas id="incomeChart"></canvas>

		<script>
			// Extract JSON data from server-side
			const incomeData = JSON.parse('${incomeDataJson}');

			// Convert data to arrays for Chart.js
			const labels = Object.keys(incomeData);
			const data = Object.values(incomeData);

			// Chart.js configuration
			const ctx = document.getElementById('incomeChart').getContext('2d');
			new Chart(ctx, {
				type : 'line',
				data : {
					labels : labels,
					datasets : [ {
						label : 'Total Income',
						data : data,
						backgroundColor : 'rgba(75, 192, 192, 0.2)',
						borderColor : 'rgba(75, 192, 192, 1)',
						borderWidth : 1
					} ]
				},
				options : {
					responsive : true,
					scales : {
						x : {
							title : {
								display : true,
								text : 'Date'
							}
						},
						y : {
							title : {
								display : true,
								text : 'Total Income'
							},
							beginAtZero : true
						}
					}
				}
			});
		</script>


		<div class="container mt-5">
			<h4 class="mt-4">Most and Least Sold Items (Last 7 Days)</h4>
			<div class="table-responsive">
				<table class="table table-bordered table-striped mt-3">
					<thead class="table-dark">
						<tr>
							<th>Item Name</th>
							<th>Total Quantity Sold</th>
							<th>Total Price</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="entry" items="${soldItemsData}">
							<tr>
								<td>${entry.key}</td>
								<td>${entry.value.totalQuantity}</td>
								<td>${entry.value.totalPrice}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<div class="container mt-5">
			<h4 class="mt-4">Branch Profitability and Canceled Orders (Last
				7 Days)</h4>
			<table class="table table-bordered table-striped mt-3">
				<thead class="table-dark">
					<tr>
						<th>Branch Location</th>
						<th>Total Profit</th>
						<th>Canceled Orders</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="branch" items="${branchData}">
						<tr>
							<td>${branch.value['location']}</td>
							<td>${branch.value['total_profit']}</td>
							<td>${branch.value['canceled_orders']}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<div class="container mt-5">
			<h4 class="mt-4">Top 10 Customers (Last 7 Days)</h4>
			<table class="table table-bordered table-striped mt-3">
				<thead class="table-dark">
					<tr>
						<th scope="col">#</th>
						<th scope="col">Customer Name</th>
						<th scope="col">Branch Name</th>
						<th scope="col">Total Purchases</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="customer" items="${topCustomers}"
						varStatus="status">
						<tr>
							<th scope="row">${status.index + 1}</th>
							<td>${customer.customerName}</td>
							<td>${customer.branchName}</td>
							<td>${customer.totalPurchases}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<script
			src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
