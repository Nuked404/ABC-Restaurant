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
	<!-- Sticky Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark sticky-top">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">ABC Restaurant</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link"
						aria-current="page" href="AdminDashboard">Dashboard</a></li>
					<li class="nav-item"><a class="nav-link" href="DashboardMenu">Manage
							Menu</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Manage
							Users</a></li>
					<li class="nav-item"><a class="nav-link active"
						href="DashboardLocation">Manage Locations</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Reports</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>

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
						action="DashboardLocation?action=${ubranch != null ? 'updateBranch' : 'addBranch'}"
						method="post">
						<div class="mb-3">
							<label for="location" class="form-label">Location</label> <input
								type="text" class="form-control" id="location" name="location"
								value="${ubranch != null ? ubranch.location : ''}" required>
							<input type="hidden" name="branchId" value="${ubranch.id}" />
						</div>
						<div class="mb-3">
							<label for="maxSeats" class="form-label">Max Seats</label> <input
								type="number" class="form-control" id="maxSeats" name="maxSeats"
								value="${ubranch != null ? ubranch.maxSeats : ''}" required>
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
								<th scope="col" class="col-3">Location</th>
								<th scope="col" class="col-1">Max Seats</th>
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
									<!-- Display the branch max seats -->
									<td>${branch.maxSeats}</td>
									<td>
										<!-- Update Button with Branch ID -->
										<form action="DashboardLocation?action=editBranch"
											method="post" style="display: inline;">
											<input type="hidden" name="branchId" value="${branch.id}" />
											<button class="btn btn-update" type="submit">Edit</button>
										</form> <!-- Delete Button with Branch ID -->
										<form action="DashboardLocation?action=deleteBranch"
											method="post" style="display: inline;">
											<input type="hidden" name="branchId" value="${branch.id}" />
											<button class="btn btn-delete" type="submit"
												onclick="return confirm('Are you sure you want to delete this entry? This action cannot be undone.');">Delete</button>
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
