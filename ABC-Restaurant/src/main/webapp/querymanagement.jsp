<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>User Queries - ABC Restaurant</title>
<!-- Bootstrap 5 CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css"
	rel="stylesheet">
<style>
body {
	background-color: #f8f9fa;
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.container {
	margin-top: 60px;
}

.navbar {
	background-color: #343a40;
}

.navbar .nav-link:hover {
	color: #ffc107;
}

.navbar-brand, .navbar-nav .nav-link {
	color: #ffffff;
}

.btn-custom {
	background-color: #343a40;
	color: #ffffff;
}

.btn-custom:hover {
	background-color: #495057;
	color: #ffffff;
}
</style>
</head>
<body>
	<!-- Navigation Bar -->
	<nav class="navbar navbar-expand-lg navbar-dark">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">ABC Restaurant</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav ms-auto">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Queries</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">Logout</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<!-- Main Container -->
	<div class="container">
		<!-- Display User Queries in a Table -->
		<h2 class="my-4">User Queries</h2>
		<div class="table-responsive">
			<table class="table table-striped table-hover">
				<thead class="table-dark">
					<tr>
						<th>ID</th>
						<th>Name</th>
						<th>Email</th>
						<th>Phone</th>
						<th>Branch</th>
						<th>Query</th>
						<th>Response</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="query" items="${queries}" varStatus="status">
						<tr>
							<td>${status.index + 1}</td>
							<td>${userMap[query.userId].name}</td>
							<td>${userMap[query.userId].email}</td>
							<td>${userMap[query.userId].phone}</td>
							<td>${branchMap[userMap[query.userId].nearestLocation].location}</td>
							<td>${query.query}</td>
							<td>
								<form action="DashboardQueryManagement" method="post"
									style="display: inline;">
									<input type="hidden" name="action" value="updateResponse">
									<input type="hidden" name="queryId" value="${query.id}">
									<textarea class="form-control" name="response">${query.response}</textarea>
									<button type="submit" class="btn btn-success btn-sm mt-2">
										<i class="fas fa-save"></i> Save
									</button>
								</form>
							</td>
							<td>
								<!-- Delete Button -->
								<form action="DashboardQueryManagement" method="post"
									style="display: inline;">
									<input type="hidden" name="action" value="deleteQuery">
									<input type="hidden" name="queryId" value="${query.id}">
									<button type="submit" class="btn btn-danger btn-sm">
										<i class="fas fa-trash"></i> Delete
									</button>
								</form>
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
