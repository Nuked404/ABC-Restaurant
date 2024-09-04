<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
				<a href="index.jsp" class="btn btn-dark">Home</a> <a
					href="Login" class="btn btn-dark">Login</a>
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