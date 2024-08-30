<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Manage Reservations - ABC Restaurant</title>
<!-- Bootstrap 5 CDN -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.container {
	margin-top: 50px;
	margin-bottom: 50px;
}

.card-custom {
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.btn-custom {
	background-color: #343a40;
	color: #ffffff;
}

.btn-custom:hover {
	background-color: #495057;
}

.table-custom {
	background-color: #ffffff;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
</style>
</head>
<body>

	<!-- Sticky Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">ABC Restaurant</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link" href="dashboard.jsp">Dashboard</a></li>
					<li class="nav-item"><a class="nav-link active" href="manage_reservations.jsp">Manage Reservations</a></li>
					<li class="nav-item"><a class="nav-link" href="manage_users.jsp">Manage Users</a></li>
					<li class="nav-item"><a class="nav-link" href="manage_locations.jsp">Manage Locations</a></li>
					<li class="nav-item"><a class="nav-link" href="reports.jsp">Reports</a></li>
				</ul>
				<form class="d-flex" role="search">
					<input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
					<button class="btn btn-outline-success" type="submit">Search</button>
				</form>
			</div>
		</div>
	</nav>

	<!-- Main Content -->
	<div class="container">
		<div class="card card-custom mb-4">
			<div class="card-header bg-dark text-white">
				<h4>Manage Reservations</h4>
			</div>
			<div class="card-body">
				<div class="table-responsive">
					<table class="table table-bordered table-hover table-custom">
						<thead class="table-dark">
							<tr>
								<th>#</th>
								<th>User</th>
								<th>Branch</th>
								<th>Date</th>
								<th>Time Frame</th>
								<th>Seats</th>
								<th>Status</th>
								<th>Notes</th>
								<th>Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="reservation" items="${reservations}" varStatus="status">
								<tr>
									<td>${status.index + 1}</td>
									<td>${userMap[reservation.userId].name} (${userMap[reservation.userId].phone})</td>
									<td>${branchMap[reservation.branchId].location} (Seats: ${branchMap[reservation.branchId].maxSeats})</td>
									<td>${reservation.reservationDate}</td>
									<td>${reservation.timeFrame}</td>
									<td>${reservation.seatCount}</td>
									<td>${reservation.status}</td>
									<td>${reservation.notes}</td>
									<td>
										<div class="btn-group" role="group" aria-label="Order actions">
											<c:forEach var="status" items="${reservationStatuses}">
												<a href="DashboardReservationManagement?action=updateReservationStatus&reservationId=${reservation.id}&status=${status}"
													class="btn btn-sm ${reservation.status == status ? 'btn-current-status' : 'btn-outline-secondary'}">
													${status}
												</a>
											</c:forEach>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS Bundle with Popper -->
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
