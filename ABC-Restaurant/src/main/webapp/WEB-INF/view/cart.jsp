<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Your Cart - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp" %>
<style>
.table {
	margin-bottom: 30px;
}

td {
	align-content: center;
}
</style>
</head>
<body>
	
	<%@ include file="/includes/navbar.jsp" %>

	<!-- Cart Container -->
	<div class="container main-content">
		<h2 class="my-4">Your Cart</h2>
		<c:if test="${not empty orderItems}">
			<table class="table table-striped table-hover">
				<thead>
					<tr>
						<th>Image</th>
						<!-- New column for the image -->
						<th>Item</th>
						<th>Quantity</th>
						<th>Price</th>
						<th>Subtotal</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="orderItem" items="${orderItems}">
						<c:forEach var="menuItem" items="${menuItems}">
							<c:if test="${menuItem.id == orderItem.menuItemId}">
								<tr>
									<!-- Image column -->
									<td><img src="${menuItem.imagePath}"
										alt="${menuItem.name}"
										style="width: 200px; height: 100px; object-fit: cover;">
									</td>
									<td>${menuItem.name}</td>
									<td>${orderItem.qty}</td>
									<td>Rs. ${menuItem.price}</td>
									<td>Rs. ${menuItem.price.multiply(orderItem.qty)}</td>
									<td>
										<form action="Cart" method="post"
											style="display: inline;">
											<input type="hidden" name="action" value="remove"> <input
												type="hidden" name="menuItemId"
												value="${orderItem.menuItemId}">
											<button type="submit" class="btn btn-dark btn-sm"> <i class="fa fa-times"></i> Delete</button>
										</form>
									</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
				</tbody>
			</table>


			<!-- Total and Checkout -->
			<div class="d-flex justify-content-between align-items-center">
				<h3>Total: Rs. ${total}</h3>
				<form action="Cart?action=checkout" method="post">
					<button type="submit" class="btn btn-dark"><i class="fa-regular fa-credit-card"></i> Proceed to
						Checkout</button>
				</form>
			</div>
		</c:if>

		<c:if test="${empty orderItems}">
			<p>Your cart is empty.</p>
		</c:if>
	</div>

	<%@ include file="/includes/footer.jsp" %>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
