<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Order Success - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp"%>
</head>
<body>
	<%@ include file="/includes/navbar.jsp"%>
	<div class="container main-content mt-5">
		<div class="alert alert-secondary text-center" role="alert">
			<h4 class="alert-heading">Your Order is on the Way!</h4>
			<p>Please prepare to receive your order. Have your cash ready,
				and expect a call from our delivery driver shortly.</p>
			<hr>
			<p class="mb-0">Thank you for choosing our service. We hope you
				enjoy your meal!</p>
		</div>
		<div class="text-center mt-4">
			<a href="Menu" class="btn btn-dark">Go Back To Menu</a>
			<a href="Orders" class="btn btn-dark">View Your Orders</a>
		</div>
	</div>

	<%@ include file="/includes/footer.jsp"%>

	<!-- Bootstrap JS (optional for interactive components) -->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
