<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>ABC Restaurant</title>
    <!-- Bootstrap 5 CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f8f9fa;
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
        .carousel-item img {
            height: 75vh;
            object-fit: cover;
        }
        .card {
            transition: transform 0.3s;
        }
        .card:hover {
            transform: scale(1.05);
        }
        .footer {
            background-color: #343a40;
            color: #ffffff;
            padding: 30px 0;
        }
        .footer a {
            color: #ffffff;
            text-decoration: none;
        }
        .footer a:hover {
            color: #ffc107;
        }
    </style>
</head>
<body>

    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-dark fixed-top">
        <div class="container">
            <a class="navbar-brand" href="#">ABC Restaurant</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ms-auto">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Menu</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Gallery</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Reservations</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">About Us</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">Contact</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="login.jsp">Login</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

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
                            <a href="#" class="btn btn-primary">Reserve Now</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <img src="images/service2.jpg" class="card-img-top" alt="Delivery">
                        <div class="card-body">
                            <h5 class="card-title">Delivery</h5>
                            <p class="card-text">Order your favorite dishes and get them delivered to your doorstep.</p>
                            <a href="#" class="btn btn-primary">Order Now</a>
                        </div>
                    </div>
                </div>
                <div class="col-md-4">
                    <div class="card shadow-sm">
                        <img src="images/service3.jpg" class="card-img-top" alt="Takeaway">
                        <div class="card-body">
                            <h5 class="card-title">Takeaway</h5>
                            <p class="card-text">Pick up your order on the go for a quick and convenient meal.</p>
                            <a href="#" class="btn btn-primary">Order Now</a>
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

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <div class="row">
                <div class="col-md-4">
                    <h5>ABC Restaurant</h5>
                    <p>Bringing the best culinary experiences to you since 1990.</p>
                </div>
                <div class="col-md-4">
                    <h5>Quick Links</h5>
                    <ul class="list-unstyled">
                        <li><a href="#">Menu</a></li>
                        <li><a href="#">Reservations</a></li>
                        <li><a href="#">About Us</a></li>
                        <li><a href="#">Contact</a></li>
                    </ul>
                </div>
                <div class="col-md-4">
                    <h5>Contact Us</h5>
                    <p>
                        123 Main Street, Colombo, Sri Lanka<br>
                        Email: info@abcrestaurant.lk<br>
                        Phone: +94 11 234 5678
                    </p>
                </div>
            </div>
            <div class="text-center mt-3">
                <p>&copy; 2024 ABC Restaurant. All rights reserved.</p>
            </div>
        </div>
    </footer>

    <!-- Bootstrap JS Bundle with Popper -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
