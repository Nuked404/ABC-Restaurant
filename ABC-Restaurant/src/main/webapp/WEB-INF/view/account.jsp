<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Account Management - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp"%>
<style>
.form-container {
	padding: 20px;
	background-color: #ffffff;
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
</style>
</head>
<body>

	<%@ include file="/includes/navbar.jsp"%>

	<!-- Main Content -->
	<div class="container main-content">
		<div class="form-container">
			<h2>Account Management</h2>
			<form action="ManageAccount?action=updateUser" method="post">
				<input type="hidden" name="userID" value="${user.id}" />

				<!-- Name Field -->
				<div class="mb-3">
					<label for="name" class="form-label">Full Name</label> <input
						type="text" class="form-control" id="name" name="name"
						value="${user.name}" required>
				</div>

				<!-- Email Field -->
				<div class="mb-3">
					<label for="email" class="form-label">Email Address</label> <input
						type="email" class="form-control" id="email" name="email"
						value="${user.email}" required>
				</div>

				<!-- Phone Number Field -->
				<div class="mb-3">
					<label for="phone" class="form-label">Phone Number</label> <input
						type="tel" class="form-control" id="phone" name="phone"
						value="${user.phone}">
				</div>

				<!-- Address fields -->
				<div class="mb-3">
					<label for="addressLine1" class="form-label">Address Line 1</label>
					<input type="text" class="form-control" id="addressLine1"
						name="addressLine1" value="${user.addressLine1}">
				</div>

				<div class="mb-3">
					<label for="addressLine2" class="form-label">Address Line 2</label>
					<input type="text" class="form-control" id="addressLine2"
						name="addressLine2" value="${user.addressLine2}">
				</div>

				<div class="mb-3">
					<label for="city" class="form-label">City</label> <input
						type="text" class="form-control" id="city" name="city"
						value="${user.city}">
				</div>

				<!-- Nearest Location dropdown -->
				<div class="mb-3">
					<label for="nearestLocation" class="form-label">Nearest
						Branch Location</label> <select class="form-select" id="nearestLocation"
						name="nearestLocation" required>
						<!-- Dynamic options generated from the controller -->
						<c:forEach var="location" items="${branches}">
							<option value="${location.id}"
								<c:if test="${location.id == user.nearestLocation}">selected</c:if>>
								${location.location}</option>
						</c:forEach>
					</select>
				</div>

				<!-- Submit Button -->
				<button type="submit" class="btn btn-custom w-100">Update
					Account</button>
			</form>
		</div>
	</div>

	<!-- Main Content -->
	<div class="container">
		<div class="form-container">
			<h2>Update Password</h2>
			<form action="ManageAccount?action=updateUserPassword" method="post">
				<input type="hidden" name="userID" value="${user.id}" />
				<!-- Password Field -->
				<div class="mb-3">
					<label for="password" class="form-label">New Password</label> <input
						type="password" class="form-control" id="password" name="password">
				</div>

				<!-- Confirm Password Field -->
				<div class="mb-3">
					<label for="confirmPassword" class="form-label">Confirm New
						Password</label> <input type="password" class="form-control"
						id="confirmPassword" name="confirmPassword">
				</div>
				<!-- Submit Button -->
				<button type="submit" class="btn btn-custom w-100">Update
					Password</button>
			</form>
		</div>
	</div>

	<%@ include file="/includes/footer.jsp"%>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
