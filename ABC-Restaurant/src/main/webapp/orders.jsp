<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Orders - ABC Restaurant</title>
<!-- Bootstrap 5 CSS -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" rel="stylesheet">
<style>
body {
    background-color: #f8f9fa;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.container {
    margin-top: 80px;
}

.card {
    margin-bottom: 20px;
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

.btn-cancel {
    background-color: #dc3545;
    color: white;
    border: none;
}

.btn-cancel:hover {
    background-color: #c82333;
}

.btn-view {
    background-color: #007bff;
    color: white;
    border: none;
}

.btn-view:hover {
    background-color: #0056b3;
}
</style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="#">ABC Restaurant</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item"><a class="nav-link" href="#">Home</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Menu</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Gallery</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Reservations</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">About Us</a></li>
                    <li class="nav-item"><a class="nav-link" href="#">Contact</a></li>
                    <li class="nav-item"><a class="nav-link" href="login.jsp">Login</a></li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Orders Container -->
    <div class="container">
        <h2 class="my-4">Your Orders</h2>

        <c:if test="${not empty orders}">
            <c:forEach var="order" items="${orders}">
                <div class="card">
                    <div class="card-header">
                        Order #${order.id}
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">Order Summary</h5>
                        <p><strong>Ordered At:</strong> ${order.createdAt}</p>
                        <p><strong>Total Cost:</strong> Rs. ${order.total}</p>
                        <p><strong>Order Status:</strong> ${order.status}</p>
                        <p><strong>Order Branch:</strong> ${branchMap[userMap[order.userId].nearestLocation].location}</p>

                        <!-- Action Buttons -->
                        <c:if test="${order.status != 'CANCELED'}">
                            <form action="Orders?action=cancelOrder" method="post" class="mt-3 d-inline">
                                <input type="hidden" name="orderId" value="${order.id}">
                                <button type="submit" class="btn btn-cancel">
                                    <i class="fa fa-times"></i> Cancel Order
                                </button>
                            </form>
                        </c:if>
                        <a href="ViewOrder?id=${order.id}" class="btn btn-view" target="_blank">
                            <i class="fa fa-eye"></i> View Order
                        </a>
                    </div>
                </div>
            </c:forEach>
        </c:if>

        <c:if test="${empty orders}">
            <div class="alert alert-warning" role="alert">
                You have no orders to display.
            </div>
        </c:if>
    </div>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
