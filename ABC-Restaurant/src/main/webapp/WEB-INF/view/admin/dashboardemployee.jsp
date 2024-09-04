<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Employee Management - ABC Restaurant</title>
<%@ include file="/includes/admin/externstyles.jsp"%>
</head>
<body>
	<%@ include file="/includes/admin/navbar.jsp"%>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Add Employee</h1>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row">
			<div class="form-container">
				<h4>${uemployee != null ? "Update Employee" : "Add Employee"}</h4>
				<form
					action="DashboardEmployee?action=${uemployee != null ? 'updateEmployee' : 'addEmployee'}"
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
					<button type="submit" class="btn btn-dark w-100">${uemployee != null ? "Update Employee" : "Add Employee"}</button>
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
								<td>${branchMap[employee.nearestLocation].location}</td>
								<td>
									<form action="DashboardEmployee?action=editEmployee"
										method="post" style="display: inline;">
										<input type="hidden" name="employeeId" value="${employee.id}" />
										<button class="btn btn-dark" type="submit"><i class="fas fa-edit"></i> Edit</button>
									</form>
									<form action="DashboardEmployee?action=deleteEmployee"
										method="post" style="display: inline;">
										<input type="hidden" name="employeeId" value="${employee.id}" />
										<button class="btn btn-dark" type="submit"
											onclick="return confirm('Are you sure you want to delete this employee? This action cannot be undone.');"><i class="fas fa-trash"></i> Delete</button>
									</form>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
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
