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
<%@ include file="/includes/admin/externstyles.jsp"%>

</head>
<body>
	<%@ include file="/includes/admin/navbar.jsp"%>

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
				<div class="formx">
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
						<button type="submit" class="btn btn-dark w-100">${ubranch != null ? "Update Location" : "Add Location"}</button>
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
											<button class="btn btn-dark" type="submit"><i class="fas fa-edit"></i> Edit</button>
										</form> <!-- Delete Button with Branch ID -->
										<form action="DashboardLocation?action=deleteBranch"
											method="post" style="display: inline;">
											<input type="hidden" name="branchId" value="${branch.id}" />
											<button class="btn btn-dark" type="submit"
												onclick="return confirm('Are you sure you want to delete this entry? This action cannot be undone.');"><i class="fas fa-trash"></i> Delete</button>
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
