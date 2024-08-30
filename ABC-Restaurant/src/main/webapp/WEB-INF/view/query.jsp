<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Queries - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp" %>
    <style>     
        .btn-custom {
            background-color: #343a40;
            color: #ffffff;
        }
        .btn-custom:hover {
            background-color: #495057;
            color: #ffffff;
        }
    </style>
</head>
<body>
    <%@ include file="/includes/navbar.jsp" %>

    <!-- Main Container -->
    <div class="container main-content">
        <!-- Form for Adding or Updating a Query -->
        <div class="card mt-5">
            <div class="card-header">
                <h4>${queryToUpdate != null ? "Update Query" : "Add Query"}</h4>
            </div>
            <div class="card-body">
                <form action="Query?action=${queryToUpdate != null ? 'updateQuery' : 'addQuery'}" method="post">
                    <div class="mb-3">
                        <label for="query" class="form-label">Query</label>
                        <input type="text" class="form-control" id="query" name="query" value="${queryToUpdate != null ? queryToUpdate.query : ''}" required>
                        <input type="hidden" name="queryId" value="${queryToUpdate != null ? queryToUpdate.id : ''}" />
                    </div>
                    <button type="submit" class="btn btn-custom w-100">${queryToUpdate != null ? "Update Query" : "Add Query"}</button>
                </form>
            </div>
        </div>

        <!-- Display User Queries in a Table -->
        <h2 class="my-4">User Queries</h2>
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Query</th>
                        <th>Response</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="query" items="${queries}" varStatus="status">
                        <tr>
                            <td>${status.index + 1}</td>
                            <td>${query.query}</td>
                            <td>${query.response}</td>
                            <td>
                                <!-- Edit Button -->
                                <form action="Query" method="get" style="display: inline;">
                                    <input type="hidden" name="action" value="editQuery">
                                    <input type="hidden" name="queryId" value="${query.id}">
                                    <button type="submit" class="btn btn-warning btn-sm"><i class="fas fa-edit"></i> Edit</button>
                                </form>
                                
                                <!-- Delete Button -->
                                <form action="Query" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="deleteQuery">
                                    <input type="hidden" name="queryId" value="${query.id}">
                                    <button type="submit" class="btn btn-danger btn-sm"><i class="fas fa-trash"></i> Delete</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

	<%@ include file="/includes/footer.jsp" %>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
