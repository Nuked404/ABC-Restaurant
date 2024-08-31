<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Management - ABC Restaurant</title>
<%@ include file="/includes/admin/externstyles.jsp"%>
</head>
<body>
	<%@ include file="/includes/admin/navbar.jsp"%>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Order Management</h1>
			</div>
		</div>
	</div>

	<!-- Order Management Container -->
	<div class="container">
		<c:if test="${not empty orders}">
			<div class="table-responsive">
				<table class="table table-striped table-hover align-middle">
					<thead class="table-dark">
						<tr>
							<th>#</th>
							<th>User Name</th>
							<th>User Phone Number</th>
							<th>Branch</th>
							<th>Ordered at</th>
							<th>Total Cost</th>
							<th>View Order</th>
							<th>Actions</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="order" items="${orders}" varStatus="status">
							<tr>
								<td>${status.index + 1}</td>
								<td>${userMap[order.userId].name}</td>
								<td>${userMap[order.userId].phone}</td>
								<td>${branchMap[userMap[order.userId].nearestLocation].location}</td>
								<td>${order.createdAt}</td>
								<td>${order.total}</td>
								<td><a href="ViewOrder?id=${order.id}" class="btn btn-dark"
									target="_blank"> <i class="fa fa-eye"></i> View Order
								</a></td>
								<td>
									<div class="btn-group" role="group" aria-label="Order actions">
										<c:forEach var="status" items="${orderStatuses}">
											<a
												href="DashboardOrder?id=${order.id}&status=${status}&action=updateOrderStatus"
												class="btn btn-sm 
                                                ${order.status == status ? 'btn-dark' : 'btn-outline-secondary'}">
												${status} </a>
										</c:forEach>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>

		<c:if test="${empty orders}">
			<div class="alert alert-warning" role="alert">No orders
				available for management.</div>
		</c:if>
	</div>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
