<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>View Order - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp" %>
<style>
body {
    background-color: #f8f9fa;
    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.container {
    margin-top: 80px;
}

.table {
    margin-bottom: 30px;
}
</style>
</head>
<body>

    <!-- Order Details Container -->
    <div class="container">
        <h2 class="my-4">Order Details</h2>

        <div class="card mb-4">
            <div class="card-header">
                Order #${order.id}
            </div>
            <div class="card-body">
                <h5 class="card-title">Order Summary</h5>
                <p><strong>User Name:</strong> ${userMap[order.userId].name}</p>
                <p><strong>User Phone Number:</strong> ${userMap[order.userId].phone}</p>
                <p><strong>Ordered At:</strong> ${order.createdAt}</p>
                <p><strong>Total Cost:</strong> Rs. ${order.total}</p>
                <p><strong>Order Status:</strong> ${order.status}</p>
                <p><strong>Order Branch:</strong> ${branchMap[userMap[order.userId].nearestLocation].location}</p>

                <h5 class="mt-4">Order Items</h5>
                <div class="table-responsive">
                    <table class="table table-striped table-hover align-middle">
                        <thead class="table-dark">
                            <tr>
                                <th>#</th>
                                <th>Menu Item</th>
                                <th>Price</th>
                                <th>Quantity</th>                                
                                <th>Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="item" items="${orderItems}" varStatus="status">
                                <tr>
                                    <td>${status.index + 1}</td>
                                    <td>${menuItems[item.menuItemId].name}</td>
                                    <td>Rs. ${menuItems[item.menuItemId].price}</td>
                                    <td>${item.qty}</td>                                    
                                    <td>Rs. ${item.qty * menuItems[item.menuItemId].price}</td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
