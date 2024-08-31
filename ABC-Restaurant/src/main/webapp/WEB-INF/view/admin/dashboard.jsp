<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard - ABC Restaurant</title>
<%@ include file="/includes/admin/externstyles.jsp"%>

</head>
<body>

	<%@ include file="/includes/admin/navbar.jsp"%>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Manage Admin Account</h1>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row form-container">
			<h4>Update Password</h4>
			<form action="addLocation.jsp" method="post">
				<div class="mb-3">
					<label for="password" class="form-label">Password</label> <input
						type="password" class="form-control" id="password" name="password"
						required>
				</div>

				<div class="mb-3">
					<label for="confirmPassword" class="form-label">Confirm
						Password</label> <input type="password" class="form-control"
						id="confirmPassword" name="confirmPassword" required>
				</div>
				<button type="submit" class="btn btn-dark w-100">Update
					Password</button>
			</form>
		</div>
	</div>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
