<%@ page import="com.abc.service.UserService"%>
<%@ page import="com.abc.enums.UserRole"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Sticky Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark sticky-top">
	<div class="container-fluid">
		<a class="navbar-brand" href="index.jsp"> <img
			src="images/Logo.png" alt="ABC Restaurant" width=auto height="50">
		</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ms-auto">
				<c:if test="${sessionScope.loggedInUser.role == UserRole.ADMIN}">
					<li class="nav-item"><a class="nav-link"
						href="DashboardReport">Reports</a></li>
					<li class="nav-item"><a class="nav-link"
						href="DashboardEmployee">Manage Employees</a></li>
				</c:if>
				<li class="nav-item"><a class="nav-link" href="DashboardMenu">Manage
						Menu</a></li>
				<li class="nav-item"><a class="nav-link"
					href="DashboardReservation">Manage Reservations</a></li>
				<li class="nav-item"><a class="nav-link" href="DashboardOrder">Manage
						Orders</a></li>
				<li class="nav-item"><a class="nav-link" href="DashboardQuery">Manage
						Queries</a></li>
				<li class="nav-item"><a class="nav-link"
					href="DashboardLocation">Manage Locations</a></li>
				<li class="nav-item"><a class="nav-link"
					href="DashboardAccount">${sessionScope.loggedInUser.name}</a></li>
				<li class="nav-item"><a class="nav-link" href="Login?Logout"><i
						class="fa-solid fa-arrow-right-from-bracket"></i></a></li>
			</ul>
		</div>
	</div>
</nav>