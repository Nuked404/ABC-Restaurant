<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Menu Management - ABC Restaurant</title>
<!-- Bootstrap 5 CDN -->
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

.cust-container {
	padding: 20px;
	background-color: #ffffff;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
	margin-bottom: 30px;
}

.card-img-top {
	width: 100%;
	height: 200px; /* Set a fixed height for all images */
	object-fit: cover; /* Ensures the image covers the entire container */
}

.form-container, .table-container {
	height: 100%;
}

.btn-custom {
	background-color: #343a40;
	color: #ffffff;
}

.btn-custom:hover {
	background-color: #495057;
}

.btn-update, .btn-delete {
	width: 100px;
}

.btn-update {
	background-color: #ffc107;
	color: #212529;
}

.btn-update:hover {
	background-color: #e0a800;
	color: #212529;
}

.btn-delete {
	background-color: #dc3545;
	color: #ffffff;
}

.btn-delete:hover {
	background-color: #c82333;
	color: #ffffff;
}

table th, table td {
	vertical-align: middle;
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
</style>
</head>
<body>

	<!-- Navbar -->
	<nav class="navbar navbar-expand-lg navbar-dark fixed-top">
		<div class="container">
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
					<li class="nav-item"><a class="nav-link" href="#">Menu</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Gallery</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">Reservations</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">About Us</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="#">Contact</a>
					</li>
					<li class="nav-item"><a class="nav-link" href="login.jsp">Login</a>
					</li>
					<li class="nav-item">
						<!-- Cart Icon with Number Badge --> <a class="nav-link" href="#">
							<i class="fas fa-shopping-cart"></i> <!-- Badge for number of items -->
							<span class="badge bg-danger" id="cart-count">${cartItemCount}</span>
					</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>


	<div class="container" style="margin-top: 80px;">
		<div class="row">
			<div class="cust-container">
				<h1>Menu Items</h1>
			</div>
		</div>
	</div>


	<div class="container">
		<div class="row">
			<!-- Initialize a set to keep track of the displayed categories -->
			<c:set var="displayedCategories" value="" />

			<c:forEach var="menuItem" items="${menuItems}" varStatus="status">
				<!-- Check if the category has already been displayed -->
				<c:if
					test="${not fn:contains(displayedCategories, menuItem.category)}">

					<!-- Add the current category to the displayedCategories -->
					<c:set var="displayedCategories"
						value="${displayedCategories},${menuItem.category}" />

					<!-- Display the category title -->
					<c:set var="itemCounter" value="0" />

					<div class="cust-container">
						<h3>${menuItem.category}s</h3>
					</div>
					<div class="row">
						<!-- Iterate again to display items under this category -->
						<c:forEach var="item" items="${menuItems}">
							<!-- Check if the item belongs to the current category -->
							<c:if test="${item.category == menuItem.category}">
								<!-- Increment the counter for each item displayed -->
								<c:set var="itemCounter" value="${itemCounter + 1}" />
								<div class="col-md-4">
									<div class="card mb-4 shadow-sm">
										<img src="${item.imagePath}" class="card-img-top"
											alt="${item.name}">
										<div class="card-body">
											<h5 class="card-title">${item.name}</h5>
											<p class="card-text">${item.description}</p>
											<p class="card-text">
												<strong>Rs.</strong>${item.price}/=</p>
											<form action="Menu?action=addToCart" method="post"
												style="display: inline;">
												<!-- Hidden input to pass the Menu Item ID -->
												<input type="hidden" name="menuItemId" value="${item.id}" />

												<!-- Quantity Input -->
												<input type="number" name="quantity" value="1" min="1"
													class="form-control"
													style="width: 70px; display: inline-block;" />

												<!-- Add to Cart Button -->
												<button class="btn btn-primary" type="submit">Add
													to Cart</button>
											</form>
										</div>
									</div>
								</div>

								<!-- Break row after every 3 cards within the same category -->
								<c:if test="${itemCounter % 3 == 0 && itemCounter != 0}">
					</div>
					<div class="row">
				</c:if>
				</c:if>
			</c:forEach>
		</div>
		</c:if>
		</c:forEach>
	</div>
	</div>

	<!-- Bootstrap JS Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
