<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Gallery - ABC Restaurant</title>
<%@ include file="/includes/externstyles.jsp"%>
<style>
.gallery-item {
	position: relative;
	overflow: hidden;
	height: 400px; /* Adjust this height as needed */
}

.gallery-item img {
	width: 100%;
	height: 100%;
	object-fit: cover; /* Ensures image covers the container */
	transition: transform 0.3s ease;
}

.gallery-item:hover img {
	transform: scale(1.1);
}

.text {
	color: white;
	font-size: 20px;
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	text-align: center;
}
</style>
</head>
<body>
	<%@ include file="/includes/navbar.jsp"%>
	
	<div class="container" style="margin-top: 50px;">
		<div class="row">
			<div class="cust-container text-center text-white" style="background-color: #000000;">
				<h1>Gallery</h1>
			</div>
		</div>
	</div>
	
	<div class="container mt-5 main-content" style="margin-bottom:50px;">		
		<div class="row g-3">
			<!-- Gallery Item 1 -->
			<div class="col-md-4 col-sm-6 gallery-item">
				<img src="images/gallery1.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 1">				
			</div>
			<!-- Gallery Item 2 -->
			<div class="col-md-8 col-sm-6 gallery-item">
				<img src="images/gallery2.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 2">				
			</div>
			
			<!-- Gallery Item 3 -->
			<div class="col-md-6 col-sm-6 gallery-item">
				<img src="images/gallery3.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 3">				
			</div>
			<!-- Gallery Item 4 -->
			<div class="col-md-6 col-sm-6 gallery-item">
				<img src="images/gallery4.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 4">				
			</div>
			
			<!-- Gallery Item 5 -->
			<div class="col-md-8 col-sm-6 gallery-item">
				<img src="images/gallery5.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 5">				
			</div>
			<!-- Gallery Item 6 -->
			<div class="col-md-4 col-sm-6 gallery-item">
				<img src="images/gallery6.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 6">				
			</div>
			
			
			<!-- Gallery Item 7 -->
			<div class="col-md-4 col-sm-6 gallery-item">
				<img src="images/gallery7.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 7">				
			</div>
			<!-- Gallery Item 8 -->
			<div class="col-md-4 col-sm-6 gallery-item">
				<img src="images/gallery8.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 8">				
			</div>
			<!-- Gallery Item 9 -->
			<div class="col-md-4 col-sm-6 gallery-item">
				<img src="images/gallery9.jpg" class="img-fluid rounded shadow-sm"
					alt="Gallery Image 9">				
			</div>
			
		</div>
	</div>


	<%@ include file="/includes/footer.jsp"%>

	<!-- Bootstrap JS (optional for interactive components) -->
	<script
		src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.min.js"></script>
</body>
</html>
