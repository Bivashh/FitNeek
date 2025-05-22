<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    
    <%
	//Get the session and request objects
	HttpSession userSession = request.getSession();
	String currentUserType = (String) userSession.getAttribute("type");
	String currentUser = (String) userSession.getAttribute("username");
    String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/home.css" />
<title>Insert title here</title>
</head>
<body>
<!-- Header -->
    <header class="header" id="header">
    <div class="container">
        <nav class="navbar">
            <div class="logo">
                <a href="#"><span>Fit</span>Neek</a>
            </div>
            <ul class="nav-menu">
                <li class="nav-item"><a href="#" class="nav-link active">Home</a></li>
                <li class="nav-item"><a href="#plans" class="nav-link">Our Plans</a></li>
                <li class="nav-item"><a href="#features" class="nav-link">Features</a></li>
                <li class="nav-item"><a href="#contact" class="nav-link">Contact Us</a></li>
            </ul>
            <div class="nav-buttons">
                <% 
                // if not logged in the display login and register buttons
                if (currentUser == null) {
                %>
                    <a href="<%=contextPath + StringUtils.PAGE_URL_LOGIN%>" class="btn btn-login">Login</a>
                    <a href="<%=contextPath + StringUtils.PAGE_URL_REGISTER_MEMBER%>" class="btn btn-register">Register</a>
                <%
                // else display profile and logout button
                } else {
                %>
                    
                    <a href="<%=contextPath + StringUtils.SERVLET_URL_LOGOUT%>" class="btn btn-logout">Logout</a>
                <% } %>
            </div>
            <div class="hamburger">
                <span class="bar"></span>
                <span class="bar"></span>
                <span class="bar"></span>
            </div>
        </nav>
    </div>
</header>


    <!-- Hero Section -->
    <section class="hero">
        <div class="container">
            <div class="hero-content">
                <h1>Transform Your Body, <span>Transform Your Life</span></h1>
                <p>Join FitNeek today and start your fitness journey. Our state-of-the-art facilities and expert trainers are here to help you achieve your goals.</p>
                <div class="hero-btns">
                    <a href="#plans" class="btn btn-primary">View Plans</a>
                    <a href="#" class="btn btn-secondary">Free Trial</a>
                </div>
            </div>
            <div class="hero-stats">
                <div class="stat-item">
                    <span class="stat-number">10+</span>
                    <span class="stat-text">Years Experience</span>
                </div>
                <div class="stat-item">
                    <span class="stat-number">20+</span>
                    <span class="stat-text">Expert Trainers</span>
                </div>
                <div class="stat-item">
                    <span class="stat-number">5000+</span>
                    <span class="stat-text">Happy Members</span>
                </div>
            </div>
        </div>
    </section>

    <!-- Features Section -->
    <section class="features" id="features">
        <div class="container">
            <div class="section-header">
                <h2>Why Choose <span>FitNeek</span></h2>
                <p>We offer premium facilities with state-of-the-art equipment to help you achieve your fitness goals</p>
            </div>
            <div class="features-grid">
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-dumbbell"></i>
                    </div>
                    <h3>Modern Equipment</h3>
                    <p>Access to the latest fitness equipment, designed for all experience levels.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-users"></i>
                    </div>
                    <h3>Group Classes</h3>
                    <p>Engaging classes led by certified instructors for every fitness interest.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-heartbeat"></i>
                    </div>
                    <h3>Health Tracking</h3>
                    <p>State-of-the-art tracking to monitor your progress and health metrics.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-user-friends"></i>
                    </div>
                    <h3>Personal Training</h3>
                    <p>One-on-one sessions with expert trainers tailored to your goals.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-spa"></i>
                    </div>
                    <h3>Wellness Areas</h3>
                    <p>Relax and recover in our sauna, steam room, and recovery facilities.</p>
                </div>
                <div class="feature-card">
                    <div class="feature-icon">
                        <i class="fas fa-clock"></i>
                    </div>
                    <h3>24/7 Access</h3>
                    <p>Workout on your schedule with around-the-clock facility access.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- Membership Plans Section -->
    <section class="plans" id="plans">
        <div class="container">
            <div class="section-header">
                <h2>Membership <span>Plans</span></h2>
                <p>Choose the perfect membership plan that suits your fitness goals and lifestyle</p>
            </div>
            <div class="plans-container">
                <div class="plan-card">
                    <div class="plan-header bronze">
                        <h3>Bronze</h3>
                        <div class="plan-price">
                            <span class="currency">$</span>
                            <span class="amount">39</span>
                            <span class="period">/month</span>
                        </div>
                    </div>
                    <div class="plan-features">
                        <ul>
                            <li><i class="fas fa-check"></i> Gym Access (6AM-10PM)</li>
                            <li><i class="fas fa-check"></i> Basic Group Classes</li>
                            <li><i class="fas fa-check"></i> Fitness Assessment</li>
                            <li><i class="fas fa-times"></i> Personal Training Sessions</li>
                            <li><i class="fas fa-times"></i> Pool & Sauna Access</li>
                            <li><i class="fas fa-times"></i> Nutrition Consultation</li>
                        </ul>
                    </div>
                    <div class="plan-footer">
                        <a href="#" class="btn btn-plan">Choose Plan</a>
                    </div>
                </div>
                <div class="plan-card">
                    <div class="plan-header silver">
                        <h3>Silver</h3>
                        <div class="plan-price">
                            <span class="currency">$</span>
                            <span class="amount">59</span>
                            <span class="period">/month</span>
                        </div>
                    </div>
                    <div class="plan-features">
                        <ul>
                            <li><i class="fas fa-check"></i> 24/7 Gym Access</li>
                            <li><i class="fas fa-check"></i> All Group Classes</li>
                            <li><i class="fas fa-check"></i> Fitness Assessment</li>
                            <li><i class="fas fa-check"></i> 1 PT Session/month</li>
                            <li><i class="fas fa-check"></i> Pool Access</li>
                            <li><i class="fas fa-times"></i> Nutrition Consultation</li>
                        </ul>
                    </div>
                    <div class="plan-footer">
                        <a href="#" class="btn btn-plan">Choose Plan</a>
                    </div>
                </div>
                <div class="plan-card featured">
                    <div class="popular-badge">Most Popular</div>
                    <div class="plan-header gold">
                        <h3>Gold</h3>
                        <div class="plan-price">
                            <span class="currency">$</span>
                            <span class="amount">89</span>
                            <span class="period">/month</span>
                        </div>
                    </div>
                    <div class="plan-features">
                        <ul>
                            <li><i class="fas fa-check"></i> 24/7 Gym Access</li>
                            <li><i class="fas fa-check"></i> All Group Classes</li>
                            <li><i class="fas fa-check"></i> Advanced Fitness Assessment</li>
                            <li><i class="fas fa-check"></i> 2 PT Sessions/month</li>
                            <li><i class="fas fa-check"></i> Pool & Sauna Access</li>
                            <li><i class="fas fa-check"></i> Nutrition Consultation</li>
                        </ul>
                    </div>
                    <div class="plan-footer">
                        <a href="#" class="btn btn-plan featured">Choose Plan</a>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <!-- Testimonials Section -->
    <section class="testimonials">
        <div class="container">
            <div class="section-header">
                <h2>What Our <span>Members Say</span></h2>
                <p>Read about the experiences of our valued members</p>
            </div>
            <div class="testimonial-slider">
                <div class="testimonial-card">
                    <div class="testimonial-content">
                        <p>"FitNeek has completely transformed my fitness journey. The trainers are knowledgeable and the equipment is top-notch. I've achieved results I never thought possible!"</p>
                    </div>
                    <div class="testimonial-author">
                        <img src="/api/placeholder/60/60" alt="Emily R.">
                        <div class="author-info">
                            <h4>Emily R.</h4>
                            <p>Gold Member 1 year</p>
                        </div>
                    </div>
                </div>
                <div class="testimonial-card">
                    <div class="testimonial-content">
                        <p>"I've been a member of several gyms before, but none compare to FitNeek. The community is supportive, and the 24/7 access fits perfectly with my busy schedule."</p>
                    </div>
                    <div class="testimonial-author">
                        <img src="/api/placeholder/60/60" alt="Michael T.">
                        <div class="author-info">
                            <h4>Michael T.</h4>
                            <p>Silver Member 6 months</p>
                        </div>
                    </div>
                </div>
                <div class="testimonial-card">
                    <div class="testimonial-content">
                        <p>"The personal training sessions at FitNeek have been a game-changer for me. My trainer developed a custom plan that helped me lose 20 pounds in just 3 months!"</p>
                    </div>
                    <div class="testimonial-author">
                        <img src="/api/placeholder/60/60" alt="Sarah K.">
                        <div class="author-info">
                            <h4>Sarah K.</h4>
                            <p>Gold Member 8 months</p>
                        </div>
                    </div>
                </div>
            </div>
            <div class="slider-dots">
                <span class="dot active"></span>
                <span class="dot"></span>
                <span class="dot"></span>
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="cta">
        <div class="container">
            <div class="cta-content">
                <h2>Ready to Start Your <span>Fitness Journey</span>?</h2>
                <p>Join FitNeek today and get your first week free. No contracts, no commitments.</p>
                <a href="#" class="btn btn-cta">Join Now</a>
            </div>
        </div>
    </section>

    <!-- Contact Section -->
    <section class="contact" id="contact">
        <div class="container">
            <div class="section-header">
                <h2>Get in <span>Touch</span></h2>
                <p>Have questions? We're here to help.</p>
            </div>
            <div class="contact-container">
                <div class="contact-info">
                    <div class="contact-item">
                        <div class="icon">
                            <i class="fas fa-map-marker-alt"></i>
                        </div>
                        <div>
                            <h3>Our Location</h3>
                            <p>456 Fitness Avenue, Gym City, GC 12345</p>
                        </div>
                    </div>
                    <div class="contact-item">
                        <div class="icon">
                            <i class="fas fa-phone"></i>
                        </div>
                        <div>
                            <h3>Call Us</h3>
                            <p>(555) 987-6543</p>
                        </div>
                    </div>
                    <div class="contact-item">
                        <div class="icon">
                            <i class="fas fa-envelope"></i>
                        </div>
                        <div>
                            <h3>Email Us</h3>
                            <p>info@fitneek.com</p>
                        </div>
                    </div>
                    <div class="contact-item">
                        <div class="icon">
                            <i class="fas fa-clock"></i>
                        </div>
                        <div>
                            <h3>Hours</h3>
                            <p>Mon-Fri: 5am-11pm</p>
                            <p>Sat-Sun: 7am-9pm</p>
                        </div>
                    </div>
                </div>
                <div class="contact-form">
                    <form>
                        <div class="form-group">
                            <input type="text" id="name" placeholder="Your Name">
                        </div>
                        <div class="form-group">
                            <input type="email" id="email" placeholder="Your Email">
                        </div>
                        <div class="form-group">
                            <input type="text" id="subject" placeholder="Subject">
                        </div>
                        <div class="form-group">
                            <textarea id="message" rows="5" placeholder="Your Message"></textarea>
                        </div>
                        <button type="submit" class="btn btn-submit">Send Message</button>
                    </form>
                </div>
            </div>
        </div>
    </section>

    <!-- Footer -->
    <footer class="footer">
        <div class="container">
            <div class="footer-content">
                <div class="footer-column">
                    <div class="footer-logo">
                        <a href="#"><span>Fit</span>Neek</a>
                    </div>
                    <p>FitNeek is dedicated to helping you achieve your fitness goals with top-notch facilities and expert guidance.</p>
                    <div class="social-links">
                        <a href="#"><i class="fab fa-facebook-f"></i></a>
                        <a href="#"><i class="fab fa-twitter"></i></a>
                        <a href="#"><i class="fab fa-instagram"></i></a>
                        <a href="#"><i class="fab fa-linkedin-in"></i></a>
                    </div>
                </div>
                <div class="footer-column">
                    <h3>Quick Links</h3>
                    <ul class="footer-links">
                        <li><a href="#">Home</a></li>
                        <li><a href="#plans">Our Plans</a></li>
                        <li><a href="#features">Features</a></li>
                        <li><a href="#contact">Contact Us</a></li>
                    </ul>
                </div>
                <div class="footer-column">
                    <h3>Services</h3>
                    <ul class="footer-links">
                        <li><a href="#">Group Classes</a></li>
                        <li><a href="#">Personal Training</a></li>
                        <li><a href="#">Nutrition Planning</a></li>
                        <li><a href="#">Fitness Assessment</a></li>
                    </ul>
                </div>
                <div class="footer-column">
                    <h3>Newsletter</h3>
                    <p>Subscribe to our newsletter to get the latest updates and offers.</p>
                    <form class="newsletter-form">
                        <input type="email" placeholder="Your Email">
                        <button type="submit" class="btn-subscribe">Subscribe</button>
                    </form>
                </div>
            </div>
            <div class="footer-bottom">
                <p>&copy; 2025 FitNeek. All Rights Reserved.</p>
                <div class="footer-bottom-links">
                    <a href="#">Privacy Policy</a>
                    <a href="#">Terms & Conditions</a>
                </div>
            </div>
        </div>
    </footer>

    <script>
        // Mobile Menu Toggle
        document.addEventListener('DOMContentLoaded', function() {
            const hamburger = document.querySelector('.hamburger');
            const navMenu = document.querySelector('.nav-menu');
            const navLink = document.querySelectorAll('.nav-link');
            
            function mobileMenu() {
                hamburger.classList.toggle('active');
                navMenu.classList.toggle('active');
            }
            
            hamburger.addEventListener('click', mobileMenu);
            
            navLink.forEach(n => n.addEventListener('click', closeMenu));
            
            function closeMenu() {
                hamburger.classList.remove('active');
                navMenu.classList.remove('active');
            }
            
            // Sticky Header
            window.addEventListener('scroll', function() {
                const header = document.getElementById('header');
                if (window.scrollY > 50) {
                    header.classList.add('scrolled');
                } else {
                    header.classList.remove('scrolled');
                }
            });
            
            // Testimonial Slider
            const dots = document.querySelectorAll('.dot');
            dots.forEach((dot, index) => {
                dot.addEventListener('click', () => {
                    document.querySelector('.dot.active').classList.remove('active');
                    dot.classList.add('active');
                    const slider = document.querySelector('.testimonial-slider');
                    slider.style.transform = `translateX(-${index * 100 / 3}%)`;
                });
            });
        });
    </script>
</body>
</html>