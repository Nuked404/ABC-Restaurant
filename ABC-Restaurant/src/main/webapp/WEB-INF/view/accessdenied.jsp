<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.abc.service.UserService"%>
<%@ page import="com.abc.enums.UserRole"%>
<!DOCTYPE html>
<html>
<head>
<title>Access Denied</title>
<%@ include file="/includes/externstyles.jsp"%>
<style>
.container {
	margin-top: 100px;
	max-width: 600px;
}

.wrapper {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 60vh;
}

.alert {
	padding: 30px;
}

.btn {
	margin-top: 20px;
}
</style>
</head>
<body>
	<div class="wrapper">
		<div class="container text-center">
			<div class="alert alert-dark">
				<h2>Access Denied</h2>
				<p>You do not have permission to access this page.</p>
				<c:if test="${sessionScope.loggedInUser.role == UserRole.ADMIN}">
					<a href="DashboardReport" class="btn btn-dark">Home</a>
				</c:if>
				<c:if test="${sessionScope.loggedInUser.role == UserRole.STAFF}">
					<a href="DashboardMenu" class="btn btn-dark">Home</a>
				</c:if>
				<c:if test="${sessionScope.loggedInUser.role == UserRole.CUSTOMER}">
					<a href="index.jsp" class="btn btn-dark">Home</a>
				</c:if>
				<c:if test="${sessionScope.loggedInUser == null}">
					<a href="Login" class="btn btn-dark">Login</a>
				</c:if>
				<c:if test="${sessionScope.loggedInUser != null}">
					<a href="javascript:window.history.back();" class="btn btn-dark">Go
						Back</a>
				</c:if>
			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>