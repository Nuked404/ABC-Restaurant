<%@ page import="com.abc.model.Cart"%>
<%@ page import="com.abc.service.UserService"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
	<div class="container" style="margin-top: 0px; margin-bottom: 0px;">
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
				<li class="nav-item"><a class="nav-link" aria-current="page"
					href="index.jsp">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="Menu">Menu</a></li>
				<li class="nav-item"><a class="nav-link" href="gallery.jsp">Gallery</a></li>
				<c:if test="${sessionScope.loggedInUser == null}">
					<li class="nav-item"><a class="nav-link" href="Login">Login</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Register">Register</a>
					</li>
				</c:if>
				<c:if test="${sessionScope.loggedInUser != null}">
					<li class="nav-item"><a class="nav-link" href="Query">Queries</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Reservation">Reservations</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Orders">Orders</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="ManageAccount">${sessionScope.loggedInUser.name}</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Login?Logout"><i class="fa-solid fa-arrow-right-from-bracket"></i></a>
					</li>
					<li class="nav-item"><a class="nav-link" href="Cart"> <i
							class="fas fa-shopping-cart"></i> <!-- JSP Scriptlet to Calculate Cart Item Count -->
							<%
							Cart cart = (Cart) session.getAttribute("localCart");
							int cartItemCount = (cart != null) ? cart.getTotalQuantity() : 0;
							%> <!-- Badge for number of items --> <span
							class="badge bg-danger" id="cart-count"><%=cartItemCount%></span>
					</a></li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>