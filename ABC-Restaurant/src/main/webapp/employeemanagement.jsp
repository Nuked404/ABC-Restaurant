<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee Management - ABC Restaurant</title>
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
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="AdminDashboard">Dashboard</a></li>
					<li class="nav-item"><a class="nav-link" href="DashboardMenu">Manage
							Menu</a></li>
					<li class="nav-item"><a class="nav-link active" href="#">Manage
							Employees</a></li>
					<li class="nav-item"><a class="nav-link"
						href="DashboardLocation">Manage Locations</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Reports</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Add Employee</h1>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row cust-container">
			<div class="form-container">
				<h4>${uemployee != null ? "Update Employee" : "Add Employee"}</h4>
				<form
					action="DashEmployeeManagement?action=${uemployee != null ? 'updateEmployee' : 'addEmployee'}"
					method="post">
					<div class="mb-3">
						<label for="fullName" class="form-label">Full Name</label> <input
							type="text" class="form-control" id="fullName" name="fullName"
							value="${uemployee != null ? uemployee.name : ''}" required>
						<input type="hidden" name="employeeId" value="${uemployee.id}" />
					</div>
					<div class="mb-3">
						<label for="email" class="form-label">Email Address</label> <input
							type="email" class="form-control" id="email" name="email"
							value="${uemployee != null ? uemployee.email : ''}" required>
					</div>
					<div class="mb-3">
						<label for="phoneNumber" class="form-label">Phone Number</label> <input
							type="tel" class="form-control" id="phoneNumber"
							name="phoneNumber"
							value="${uemployee != null ? uemployee.phone : ''}"
							required>
					</div>
					<div class="mb-3">
						<label for="branchLocation" class="form-label">Nearest
							Branch Location</label> <select class="form-select" id="branchLocation"
							name="branchLocation" required>
							<c:forEach var="branch" items="${branches}">
								<option value="${branch.id}"
									${uemployee != null && uemployee.nearestLocation == branch.id ? 'selected' : ''}>${branch.location}</option>
							</c:forEach>
						</select>
					</div>
					<div class="mb-3">
						<label for="password" class="form-label">Password</label> <input
							type="password" class="form-control" id="password"
							name="password" ${uemployee == null ? 'required' : ''}>
						<c:if test="${not empty uemployee}">
							<small class="form-text text-muted">Leave blank if you do
								not want to change the password.</small>
						</c:if>
					</div>
					<div class="mb-3">
						<label for="confirmPassword" class="form-label">Confirm
							Password</label> <input type="password" class="form-control"
							id="confirmPassword" name="confirmPassword"
							${uemployee == null ? 'required' : ''}>
					</div>
					<button type="submit" class="btn btn-custom w-100">${uemployee != null ? "Update Employee" : "Add Employee"}</button>
				</form>
			</div>
		</div>
	</div>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Manage Employees</h1>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row cust-container">
			<div class="table-container">
				<h4>Manage Employees</h4>
				<table class="table table-striped">
					<thead>
						<tr>
							<th scope="col" class="col-1">ID</th>
							<th scope="col" class="col-2">Name</th>
							<th scope="col" class="col-2">Email</th>
							<th scope="col" class="col-2">Phone</th>
							<th scope="col" class="col-2">Branch Location</th>
							<th scope="col" class="col-4">Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="employee" items="${employees}" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${employee.name}</td>
								<td>${employee.email}</td>
								<td>${employee.phone}</td>
								<td>${employee.nearestLocation}</td>
								<td>
									<form action="DashEmployeeManagement?action=editEmployee"
										method="post" style="display: inline;">
										<input type="hidden" name="employeeId" value="${employee.id}" />
										<button class="btn btn-update" type="submit">Edit</button>
									</form>
									<form action="DashEmployeeManagement?action=deleteEmployee"
										method="post" style="display: inline;">
										<input type="hidden" name="employeeId" value="${employee.id}" />
										<button class="btn btn-delete" type="submit"
											onclick="return confirm('Are you sure you want to delete this employee? This action cannot be undone.');">Delete</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
