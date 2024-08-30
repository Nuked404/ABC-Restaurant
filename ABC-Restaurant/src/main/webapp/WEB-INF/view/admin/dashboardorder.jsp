<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Management - ABC Restaurant</title>
<!-- Bootstrap 5 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.container {
	margin-top: 80px;
}

.table {
	margin-bottom: 30px;
}

.table-responsive {
	margin-bottom: 30px;
}

.navbar {
	background-color: #343a40;
}

.navbar .nav-link:hover {
	color: #ffc107;
}

.navbar-brand, .navbar-nav .nav-link {
	color: #ffffff;
}

.btn-current-status {
	font-weight: bold;
	border: 2px solid #007bff;
	color: #007bff;
	background-color: #ffffff;
}

.btn-current-status:hover {
	color: #ffffff;
	background-color: #007bff;
	border-color: #007bff;
}

.btn-view {
    background-color: #007bff;
    color: white;
    border: none;
}

.btn-view:hover {
    background-color: #0056b3;
}

</style>
</head>
<body>

	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="#">ABC Restaurant</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Menu</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Gallery</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Reservations</a></li>
					<li class="nav-item"><a class="nav-link" href="#">About Us</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Contact</a></li>
					<li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Order Management Container -->
	<div class="container">
		<h2 class="my-4">Order Management</h2>
		<c:if test="${not empty orders}">
			<div class="table-responsive">
				<table class="table table-striped table-hover align-middle">
					<thead class="table-dark">
						<tr>
							<th>#</th>
							<th>User Name</th>
							<th>User Phone Number</th>
							<th>Branch</th>
							<th>Ordered at</th>
							<th>Total Cost</th>
							<th>View Order</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="order" items="${orders}" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${userMap[order.userId].name}</td>
								<td>${userMap[order.userId].phone}</td>
								<td>${branchMap[userMap[order.userId].nearestLocation].location}</td>
								<td>${order.createdAt}</td>
								<td>${order.total}</td>
								<td><a href="ViewOrder?id=${order.id}" class="btn btn-view"
									target="_blank"> <i class="fa fa-eye"></i> View Order
								</a></td>
								<td>
									<div class="btn-group" role="group" aria-label="Order actions">
										<c:forEach var="status" items="${orderStatuses}">
											<a href="DashboardOrder?id=${order.id}&status=${status}&action=updateOrderStatus"
												class="btn btn-sm 
                                                ${order.status == status ? 'btn-current-status' : 'btn-outline-secondary'}">
												${status} </a>
										</c:forEach>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

		<c:if test="${empty orders}">
			<div class="alert alert-warning" role="alert">No orders
				available for management.</div>
		</c:if>
	</div>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
