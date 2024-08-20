<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard - ABC Restaurant</title>
<!-- Bootstrap 5 CDN -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.cust-container {
	padding: 20px;
	background-color: #ffffff;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin-bottom: 30px;
}

.form-container, .table-container {
	height: 100%;
}

.btn-custom {
	background-color: #343a40;
	color: #ffffff;
}

.btn-custom:hover {
	background-color: #495057;
}

.btn-update, .btn-delete {
	width: 100px;
}

.btn-update {
	background-color: #ffc107;
	color: #212529;
}

.btn-update:hover {
	background-color: #e0a800;
	color: #212529;
}

.btn-delete {
	background-color: #dc3545;
	color: #ffffff;
}

.btn-delete:hover {
	background-color: #c82333;
	color: #ffffff;
}

table th, table td {
	vertical-align: middle;
}
</style>
</head>
<body>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Manage Admin Account</h1>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row cust-container">
			<div class="form-container">
				<h4>Update Password</h4>
				<form action="addLocation.jsp" method="post">
					<div class="mb-3">
						<label for="password" class="form-label">Password</label> <input
							type="password" class="form-control" id="password"
							name="password" required>
					</div>

					<div class="mb-3">
						<label for="confirmPassword" class="form-label">Confirm
							Password</label> <input type="password" class="form-control"
							id="confirmPassword" name="confirmPassword" required>
					</div>
					<button type="submit" class="btn btn-custom w-100">Update
						Password</button>
				</form>
			</div>
		</div>
	</div>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Manage Locations</h1>
			</div>
		</div>
	</div>


	<div class="container">
		<div class="row cust-container">
			<div class="col-md-4">
				<div class="form-container">
					<h4>${ubranch != null ? "Update Location" : "Add Location"}</h4>
					<form
						action="AdminDashboard?action=${ubranch != null ? 'updateBranchConf' : 'addBranch'}"
						method="post">
						<div class="mb-3">
							<label for="location" class="form-label">Location</label> <input
								type="text" class="form-control" id="location" name="location"
								value="${ubranch != null ? ubranch.location : ''}" required>
								<input type="hidden" name="branchId" value="${ubranch.id}" />
						</div>
						<button type="submit" class="btn btn-custom w-100">${ubranch != null ? "Update Location" : "Add Location"}</button>
					</form>
				</div>				
			</div>

			<div class="col-md-8">
				<div class="table-container">
					<h4>Manage Locations</h4>
					<table class="table table-striped">
						<thead>
							<tr>
								<th scope="col" class="col-1">ID</th>
								<th scope="col" class="col-4">Location</th>
								<th scope="col" class="col-2">Actions</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="branch" items="${branches}" varStatus="status">
								<tr>
									<!-- Display the row number -->
									<td>${status.index + 1}</td>
									<!-- Display the branch name -->
									<td>${branch.location}</td>
									<td>
										<!-- Update Button with Branch ID -->
										<form action="AdminDashboard?action=updateBranch"
											method="post" style="display: inline;">
											<input type="hidden" name="branchId" value="${branch.id}" />
											<button class="btn btn-update" type="submit">Update</button>
										</form> <!-- Delete Button with Branch ID -->
										<form action="AdminDashboard?action=deleteBranch"
											method="post" style="display: inline;">
											<input type="hidden" name="branchId" value="${branch.id}" />
											<button class="btn btn-delete" type="submit">Delete</button>
										</form>
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
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
