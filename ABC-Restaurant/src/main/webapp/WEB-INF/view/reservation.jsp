<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Reservations - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp" %>
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

.form-label {
	font-weight: bold;
}
</style>
</head>
<body>

	<%@ include file="/includes/navbar.jsp" %>

	<!-- Main Content -->
	<div class="container main-content">
		<!-- Reservation Form -->
		<div class="form-container mb-4">
			<h2>${reservationToUpdate != null ? "Update Reservation" : "Make a Reservation"}</h2>
			<div class="cardcontainer">
				<form
					action="Reservation?action=${reservationToUpdate != null ? 'updateReservation' : 'makeReservation'}"
					method="post" class="row g-3">
					<input type="hidden" name="reservationId"
						value="${reservationToUpdate != null ? reservationToUpdate.id : ''}">
					<div class="col-md-6">
						<label for="branch" class="form-label">Branch</label> <select
							id="branch" name="branchId" class="form-select" required>
							<c:forEach var="branch" items="${branches}">
								<option value="${branch.id}"
									${reservationToUpdate != null && reservationToUpdate.branchId == branch.id ? 'selected' : ''}>
									${branch.location} (Seats: ${branch.maxSeats})</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-6">
						<label for="reservationDate" class="form-label">Reservation
							Date</label> <input type="date" id="reservationDate"
							name="reservationDate" class="form-control"
							value="${reservationToUpdate != null ? reservationToUpdate.reservationDate : ''}"
							required>
					</div>
					<div class="col-md-6">
						<label for="timeFrame" class="form-label">Time Frame</label> <select
							id="timeFrame" name="timeFrame" class="form-select" required>
							<c:forEach var="time" items="${timeFrames}">
								<option value="${time}"
									${reservationToUpdate != null && reservationToUpdate.timeFrame == time ? 'selected' : ''}>${time}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col-md-6">
						<label for="seatCount" class="form-label">Seat Count</label> <input
							type="number" id="seatCount" name="seatCount"
							class="form-control"
							value="${reservationToUpdate != null ? reservationToUpdate.seatCount : ''}"
							required min="1">
					</div>
					<div class="col-12">
						<label for="notes" class="form-label">Notes</label>
						<textarea id="notes" name="notes" class="form-control" rows="2">${reservationToUpdate != null ? reservationToUpdate.notes : ''}</textarea>
					</div>
					<div class="col-12">
						<button type="submit" class="btn btn-custom w-100">${reservationToUpdate != null ? "Update Reservation" : "Submit Reservation"}</button>
					</div>
				</form>
			</div>
		</div>

		<div class="form-container mb-4">
			<h2>Available Seats</h2>
			<div class="cardcontainer">
				<p id="availableSeats">Please select branch, date, and time
					frame to check available seats.</p>
			</div>
		</div>

		<!-- User Reservations Table -->
		<h2 class="my-4">Your Reservations</h2>
		<div class="table-responsive">
					<table class="table table-bordered table-hover">
						<thead class="table-dark">
							<tr>
								<th>#</th>
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
							<c:forEach var="reservation" items="${reservations}">
								<tr>
									<td>${reservation.id}</td>
									<td>${branchMap[reservation.branchId].location}</td>
									<td>${reservation.reservationDate}</td>
									<td>${reservation.timeFrame}</td>
									<td>${reservation.seatCount}</td>
									<td>${reservation.status}</td>
									<td>${reservation.notes}</td>
									<td>
										<!-- Edit Reservation Form -->
										<form action="Reservation" method="get" class="d-inline">
											<input type="hidden" name="action" value="editReservation">
											<input type="hidden" name="reservationId"
												value="${reservation.id}">
											<button type="submit" class="btn btn-dark btn-sm">
												<i class="fas fa-edit"></i> Edit
											</button>
										</form> <!-- Cancel Reservation Form -->
										<form action="Reservation" method="post" class="d-inline">
											<input type="hidden" name="action" value="cancelReservation">
											<input type="hidden" name="reservationId"
												value="${reservation.id}">
											<button type="submit" class="btn btn-dark btn-sm">
												<i class="fas fa-trash"></i> Cancel
											</button>
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
	</div>

	<script>
    document.addEventListener('DOMContentLoaded', function() {
        const branchSelect = document.getElementById('branch');
        const dateInput = document.getElementById('reservationDate');
        const timeFrameSelect = document.getElementById('timeFrame');
        const availableSeatsParagraph = document.getElementById('availableSeats');

        function updateAvailableSeats() {
            const branchId = branchSelect.value;
            const reservationDate = dateInput.value;
            const timeFrame = timeFrameSelect.value;

            if (branchId && reservationDate && timeFrame) {
                fetch('Reservation?action=checkAvailability&branchId=' + branchId + '&reservationDate=' + reservationDate + '&timeFrame=' + timeFrame)
                    .then(response => response.json())
                    .then(data => {
                        availableSeatsParagraph.textContent = 'Available seats: ' + data.availableSeats;
                    })
                    .catch(error => {
                        console.error('Error:', error);
                        availableSeatsParagraph.textContent = 'Error retrieving available seats.';
                    });
            } else {
                availableSeatsParagraph.textContent = 'Please select branch, date, and time frame.';
            }
        }

        branchSelect.addEventListener('change', updateAvailableSeats);
        dateInput.addEventListener('change', updateAvailableSeats);
        timeFrameSelect.addEventListener('change', updateAvailableSeats);
    });
	</script>


	<%@ include file="/includes/footer.jsp" %>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://kit.fontawesome.com/a076d05399.js"></script>
</body>
</html>
