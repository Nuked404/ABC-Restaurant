<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Query Management - ABC Restaurant</title>
<%@ include file="/includes/admin/externstyles.jsp"%>
</head>
<body>
	<%@ include file="/includes/admin/navbar.jsp"%>
	
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>User Queries</h1>
			</div>
		</div>
	</div>

	<!-- Main Container -->
	<div class="container">
		<!-- Display User Queries in a Table -->
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
									<button type="submit" class="btn btn-dark btn-sm mt-2">
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
									<button type="submit" class="btn btn-dark btn-sm" onclick="return confirm('Are you sure you want to delete this employee? This action cannot be undone.');">
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
