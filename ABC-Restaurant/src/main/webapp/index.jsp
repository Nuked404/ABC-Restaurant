<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Restaurant</title>
    <%@ include file="/includes/externstyles.jsp" %>    
</head>
<body>

   <%@ include file="/includes/navbar.jsp" %>

    <!-- Hero Section / Carousel -->
    <div id="heroCarousel" class="carousel slide" data-bs-ride="carousel">
        <div class="carousel-inner">
            <div class="carousel-item active">
                <img src="images/dining1.jpg" class="d-block w-100" alt="Elegant Dining">
                <div class="carousel-caption d-none d-md-block">
                    <h1>Welcome to ABC Restaurant</h1>
                    <p>Experience exquisite dining in the heart of Sri Lanka</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="images/dining2.jpg" class="d-block w-100" alt="Gourmet Cuisine">
                <div class="carousel-caption d-none d-md-block">
                    <h1>Delight Your Taste Buds</h1>
                    <p>Discover our menu crafted by world-class chefs</p>
                </div>
            </div>
            <div class="carousel-item">
                <img src="images/dining3.jpg" class="d-block w-100" alt="Exclusive Ambiance">
                <div class="carousel-caption d-none d-md-block">
                    <h1>Exclusive Ambiance</h1>
                    <p>Enjoy our luxurious settings for any occasion</p>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#heroCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#heroCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>

    <!-- Services Section -->
    <section class="py-5">
        <div class="container text-center">
            <h2 class="mb-4">Our Services</h2>
            <div class="row g-4">
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <img src="images/service1.jpg" class="card-img-top" alt="Dine-In">
                        <div class="card-body">
                            <h5 class="card-title">Dine-In</h5>
                            <p class="card-text">Enjoy a luxurious dining experience with our carefully curated menu.</p>
                            <a href="#" class="btn btn-dark">Reserve Now</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <img src="images/service2.jpg" class="card-img-top" alt="Delivery">
                        <div class="card-body">
                            <h5 class="card-title">Delivery</h5>
                            <p class="card-text">Order your favorite dishes and get them delivered to your doorstep.</p>
                            <a href="#" class="btn btn-dark">Order Now</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <img src="images/service3.jpg" class="card-img-top" alt="Takeaway">
                        <div class="card-body">
                            <h5 class="card-title">Takeaway</h5>
                            <p class="card-text">Pick up your order on the go for a quick and convenient meal.</p>
                            <a href="#" class="btn btn-dark">Order Now</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- About Us Section -->
    <section class="py-5 bg-light">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-md-6">
                    <h2>About Us</h2>
                    <p>ABC Restaurant is a renowned restaurant chain in Sri Lanka, offering a blend of traditional and contemporary cuisine. We pride ourselves on providing top-notch service in a warm and welcoming atmosphere.</p>
                    <a href="#" class="btn btn-outline-dark">Learn More</a>
                </div>
                <div class="col-md-6">
                    <img src="images/about.jpg" class="img-fluid rounded" alt="About Us">
                </div>
            </div>
        </div>
    </section>

    <%@ include file="/includes/footer.jsp" %>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
