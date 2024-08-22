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
</style>
</head>
<body>

	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container">
				<h1>Add Menu Item</h1>
			</div>
		</div>
	</div>

	<div class="container">
		<div class="row cust-container">
			<div class="form-container">
				<h4>${menuItem != null ? 'Update Menu Item' : 'Add Menu Item'}</h4>
				<form
					action="DashboardMenu?action=${menuItem != null ? 'updateMenuItem' : 'addMenuItem'}"
					method="post" enctype="multipart/form-data">
					<input type="hidden" name="UpdateMenuid"
						value="${menuItem != null ? menuItem.id : ''}">

					<div class="mb-3">
						<label for="name" class="form-label">Name</label> <input
							type="text" class="form-control" id="name" name="AddMenuItemname"
							value="${menuItem != null ? menuItem.name : ''}" required>
					</div>

					<div class="mb-3">
						<label for="category" class="form-label">Category</label> <select
							class="form-select" id="category" name="AddMenuItemcategory"
							required>
							<option value="Appetizer"
								${menuItem != null && menuItem.category == 'Appetizer' ? 'selected' : ''}>Appetizer</option>
							<option value="Main Course"
								${menuItem != null && menuItem.category == 'Main Course' ? 'selected' : ''}>Main
								Course</option>
							<option value="Dessert"
								${menuItem != null && menuItem.category == 'Dessert' ? 'selected' : ''}>Dessert</option>
							<option value="Beverage"
								${menuItem != null && menuItem.category == 'Beverage' ? 'selected' : ''}>Beverage</option>
						</select>
					</div>

					<div class="mb-3">
						<label for="price" class="form-label">Price</label> <input
							type="number" step="0.01" class="form-control" id="price"
							name="AddMenuItemprice"
							value="${menuItem != null ? menuItem.price : ''}" required>
					</div>

					<div class="mb-3">
						<label for="description" class="form-label">Description</label>
						<textarea class="form-control" id="description"
							name="AddMenuItemdescription" rows="3" required>${menuItem != null ? menuItem.description : ''}</textarea>
					</div>

					<div class="mb-3">
						<label for="image" class="form-label">Image</label> <input
							type="file" class="form-control" id="image"
							name="AddMenuItemimage" accept="image/*">
						<c:if
							test="${menuItem != null && menuItem.imagePath != null && !menuItem.imagePath.isEmpty()}">
							<div class="mt-3">
								<img
									src="${pageContext.request.contextPath}/${menuItem.imagePath}"
									alt="Menu Item Image" class="img-thumbnail"
									style="max-width: 200px;">
							</div>
						</c:if>
					</div>

					<button type="submit" class="btn btn-custom w-100">${menuItem != null ? 'Update Menu Item' : 'Add Menu Item'}</button>
				</form>
			</div>
		</div>
	</div>

	<div class="container" style="margin-top: 50px;">
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
											<form action="DashboardMenu?action=PrepupdateMenuItem"
												method="post" style="display: inline;">
												<input type="hidden" name="menuItemId" value="${item.id}" />
												<button class="btn btn-update" type="submit">Update</button>
											</form>
											<!-- Delete Button with Menu Item ID -->
											<form action="DashboardMenu?action=deleteMenuItem"
												method="post" style="display: inline;">
												<input type="hidden" name="menuItemId" value="${item.id}" />
												<button class="btn btn-delete" type="submit"
													onclick="return confirm('Are you sure you want to delete this entry? This action cannot be undone.');">Delete</button>
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
