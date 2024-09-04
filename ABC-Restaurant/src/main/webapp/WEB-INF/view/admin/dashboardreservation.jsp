<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Reservation Management - ABC Restaurant</title>
<%@ include file="/includes/admin/externstyles.jsp"%>
</head>
<body>
	<%@ include file="/includes/admin/navbar.jsp"%>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>User Reservations</h1>
			</div>
		</div>
	</div>

	<!-- Main Content -->
	<div class="container">
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
					<c:forEach var="reservation" items="${reservations}"
						varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${userMap[reservation.userId].name}
								(${userMap[reservation.userId].phone})</td>
							<td>${branchMap[reservation.branchId].location}(Seats:
								${branchMap[reservation.branchId].maxSeats})</td>
							<td>${reservation.reservationDate}</td>
							<td>${reservation.timeFrame}</td>
							<td>${reservation.seatCount}</td>
							<td>${reservation.status}</td>
							<td>${reservation.notes}</td>
							<td>
								<div class="btn-group" role="group" aria-label="Order actions">
									<c:forEach var="status" items="${reservationStatuses}">
										<a
											href="DashboardReservation?action=updateReservationStatus&reservationId=${reservation.id}&status=${status}"
											class="btn btn-sm ${reservation.status == status ? 'btn-dark' : 'btn-outline-secondary'}">
											${status} </a>
									</c:forEach>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
