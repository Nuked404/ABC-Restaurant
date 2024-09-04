<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Registration - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp"%>
<style>
.mt-5 {
	margin-top: 0.5rem !important;
}
body {
	padding-top: 0px;
}
.card:hover {
	transform: scale(1);
}
</style>
</head>
<body>
	<div class="container mt-5">
		<div class="row justify-content-center min-vh-100">
			<div class="col-lg-6 col-md-8">
				<div class="card card-register shadow">
					<div class="card-header text-center bg-dark text-white">
						<h4>Register</h4>
					</div>
					<div class="card-body">
						<form action="Register?action=addNewUser" method="POST">
							<!-- Name field -->
							<div class="mb-3">
								<label for="name" class="form-label">Name</label> <input
									type="text" class="form-control" id="name" name="name" required>
							</div>

							<!-- Email field -->
							<div class="mb-3">
								<label for="email" class="form-label">Email</label> <input
									type="email" class="form-control" id="email" name="email"
									required>
							</div>

							<!-- Phone field -->
							<div class="mb-3">
								<label for="phone" class="form-label">Phone</label> <input
									type="text" class="form-control" id="phone" name="phone"
									required>
							</div>

							<!-- Password field -->
							<div class="mb-3">
								<label for="password" class="form-label">Password</label> <input
									type="password" class="form-control" id="password"
									name="password" required>
							</div>

							<!-- Confirm Password field -->
							<div class="mb-3">
								<label for="confirmPassword" class="form-label">Confirm
									Password</label> <input type="password" class="form-control"
									id="confirmPassword" name="confirmPassword" required>
							</div>

							<div class="mb-3">
								<label for="addressLine1" class="form-label">Address
									Line 1</label> <input type="text" class="form-control"
									id="addressLine1" name="addressLine1" required>
							</div>

							<div class="mb-3">
								<label for="addressLine2" class="form-label">Address
									Line 2</label> <input type="text" class="form-control"
									id="addressLine2" name="addressLine2">
							</div>

							<div class="mb-3">
								<label for="city" class="form-label">City</label> <input
									type="text" class="form-control" id="city" name="city">
							</div>

							<!-- Nearest Location dropdown -->
							<div class="mb-3">
								<label for="nearestLocation" class="form-label">Nearest
									Branch Location</label> <select class="form-select"
									id="nearestLocation" name="nearestLocation" required>
									<!-- Dynamic options generated from the controller -->
									<c:forEach var="location" items="${branches}">
										<option value="${location.id}">${location.location}</option>
									</c:forEach>
								</select>
							</div>


							<!-- Submit button -->
							<div class="d-grid">
								<button type="submit" class="btn btn-dark">Register</button>
							</div>
						</form>
					</div>
					<div class="card-footer text-center">
						Already have an account? <a href="Login" class="text-primary">Login
							here</a>
							 <br> Go back <a href="index.jsp" class="text-primary">Home</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- Error Modal -->
	<div class="modal fade" id="errorModal" tabindex="-1"
		aria-labelledby="errorModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header bg-dark text-white">
					<h5 class="modal-title" id="errorModalLabel">Error</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal"
						aria-label="Close"></button>
				</div>
				<div class="modal-body">Please make sure password and confirmation password are same!</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
						data-bs-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS and Popper (for modal functionality) -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

	<!-- Show modal if there is an error -->
	<script>
		window.onload = function() {
			const urlParams = new URLSearchParams(window.location.search);
			const error = urlParams.get('error');
			if (error) {
				var errorModal = new bootstrap.Modal(document
						.getElementById('errorModal'));
				errorModal.show();
			}
		};
	</script>
</body>
</html>
