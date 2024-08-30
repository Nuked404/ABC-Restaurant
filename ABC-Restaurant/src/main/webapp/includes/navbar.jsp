<%@ page import="com.abc.model.Cart" %>
<!-- Navbar -->
<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
	<div class="container" style="margin-top:0px;margin-bottom:0px;">
		<a class="navbar-brand" href="#">ABC Restaurant</a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse"
			data-bs-target="#navbarNav" aria-controls="navbarNav"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarNav">
			<ul class="navbar-nav ms-auto">
				<li class="nav-item"><a class="nav-link active"
					aria-current="page" href="#">Home</a></li>
				<li class="nav-item"><a class="nav-link" href="Menu">Menu</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Gallery</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Reservations</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#">About Us</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="#">Contact</a></li>
				<li class="nav-item"><a class="nav-link" href="login.jsp">Login</a>
				</li>				
				<li class="nav-item"><a class="nav-link" href="Query">Queries</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="Reservation">Reservations</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="Orders">Orders</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="cart"> <i
						class="fas fa-shopping-cart"></i> <!-- JSP Scriptlet to Calculate Cart Item Count -->
						<%						
						Cart cart = (Cart) session.getAttribute("localCart");
						int cartItemCount = (cart != null) ? cart.getTotalQuantity() : 0;
						%> <!-- Badge for number of items --> <span
						class="badge bg-danger" id="cart-count"><%=cartItemCount%></span>
				</a></li>
			</ul>
		</div>
	</div>
</nav>