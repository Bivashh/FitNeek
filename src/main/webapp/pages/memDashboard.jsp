<%@page import="java.util.ArrayList"%>
<%@page import="model.Plan"%>
<%@page import="model.Member"%>
<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // Get the session and request objects
    HttpSession userSession = request.getSession();
    String currentUserType = (String) userSession.getAttribute("type");
    String currentUser = (String) userSession.getAttribute("username");
    String contextPath = request.getContextPath();
    
    // Get plans list from request attribute
    ArrayList<Plan> plansList = (ArrayList<Plan>) request.getAttribute(StringUtils.PLANS_LIST);
    
    // Get member information from request attribute
    Member member = (Member) request.getAttribute("updateProfile");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FitNeek - Member Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/memDash.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
</head>
<body>
<jsp:include page="message-display.jsp" />

    <div class="app-container">
        <!-- Sidebar Navigation -->
        <aside class="sidebar" id="sidebar">
            <div class="sidebar-header">
                <div class="logo">
                    <h1>FitNeek</h1>
                </div>
                <button id="sidebar-toggle" class="sidebar-toggle">
                    <i class="fas fa-bars"></i>
                </button>
            </div>
            
            <div class="user-profile-sidebar">
                <div class="profile-image">
                    <img src="${pageContext.request.contextPath}/resources/images/Member/${requestScope.updateProfile.imageUrlFromPart}" alt="Profile">
                </div>
                <div class="profile-info">
                    <h3>${requestScope.updateProfile.member_FullName}</h3>
                    <p>Member</p>
                </div>
            </div>
            
            <nav class="sidebar-nav">
                <ul>
                    <li class="active" data-section="dashboard-section">
                        <a href="#"><i class="fas fa-th-large"></i> <span>Dashboard</span></a>
                    </li>
                    <li data-section="plans-section">
                        <a href="#"><i class="fas fa-dumbbell"></i> <span>Membership</span></a>
                    </li>
                    <li data-section="profile-section">
                        <a href="#"><i class="fas fa-user"></i> <span>Profile</span></a>
                    </li>
                    <li data-section="enquiry-section">
                        <a href="#"><i class="fas fa-envelope"></i> <span>Enquiry</span></a>
                    </li>
                </ul>
            </nav>
            
            <div class="sidebar-footer">
                <a href="<%=contextPath + StringUtils.SERVLET_URL_LOGOUT%>" class="logout-btn">
                    <i class="fas fa-sign-out-alt"></i> <span>Logout</span>
                </a>
            </div>
        </aside>

        <!-- Main Content Area -->
        <main class="main-content">
            <!-- Top Header -->
            <header class="header">
                <div class="header-left">
                    <h2 id="page-title">Dashboard</h2>
                </div>
                <div class="header-right">
                    <div class="notification">
                        <i class="fas fa-bell"></i>
                        <span class="badge">3</span>
                    </div>
                    <div class="user-profile-header">
                       <img src="${pageContext.request.contextPath}/resources/images/Member/${requestScope.updateProfile.imageUrlFromPart}" alt="Profile">
                        <span>${requestScope.updateProfile.member_FullName}</span>
                    </div>
                </div>
            </header>
            
            <!-- Dashboard Section -->
            <section id="dashboard-section" class="content-section active">
                <div class="dashboard-stats">
                    <div class="stat-card">
                        <div class="stat-icon">
                            <i class="fas fa-calendar-check"></i>
                        </div>
                        <div class="stat-details">
                            <h3>Membership Status</h3>
                            <h4>Active</h4>
                            <p>Valid until: May 30, 2025</p>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon">
                            <i class="fas fa-fire-alt"></i>
                        </div>
                        <div class="stat-details">
                            <h3>Gym Visits</h3>
                            <h4>15</h4>
                            <p>This month</p>
                        </div>
                    </div>
                    
                    <div class="stat-card">
                        <div class="stat-icon">
                            <i class="fas fa-medal"></i>
                        </div>
                        <div class="stat-details">
                            <h3>Current Plan</h3>
                            <h4>Gold Membership</h4>
                            <p>Unlimited access + 2 PT sessions</p>
                        </div>
                    </div>
                </div>
                
                <div class="activity-container">
                    <div class="container-header">
                        <h3><i class="fas fa-chart-line"></i> Recent Activity</h3>
                        <a href="#" class="view-all">View All</a>
                    </div>
                    
                    <div class="activity-list">
                        <div class="activity-item">
                            <div class="activity-icon">
                                <i class="fas fa-running"></i>
                            </div>
                            <div class="activity-details">
                                <h4>Gym Session</h4>
                                <p>Yesterday at 6:30 PM</p>
                            </div>
                            <div class="activity-meta">
                                <span class="activity-duration">1h 15m</span>
                            </div>
                        </div>
                        
                        <div class="activity-item">
                            <div class="activity-icon">
                                <i class="fas fa-heartbeat"></i>
                            </div>
                            <div class="activity-details">
                                <h4>Cardio Class</h4>
                                <p>May 6, 2025 at 5:00 PM</p>
                            </div>
                            <div class="activity-meta">
                                <span class="activity-duration">45m</span>
                            </div>
                        </div>
                        
                        <div class="activity-item">
                            <div class="activity-icon">
                                <i class="fas fa-dumbbell"></i>
                            </div>
                            <div class="activity-details">
                                <h4>Personal Training</h4>
                                <p>May 4, 2025 at 7:00 PM</p>
                            </div>
                            <div class="activity-meta">
                                <span class="activity-duration">1h</span>
                            </div>
                        </div>
                    </div>
                </div>
                
                <div class="upcoming-classes">
                    <div class="container-header">
                        <h3><i class="far fa-calendar-alt"></i> Upcoming Classes</h3>
                        <a href="#" class="view-all">View Schedule</a>
                    </div>
                    
                    <div class="classes-grid">
                        <div class="class-card">
                            <div class="class-time">
                                <span class="day">Today</span>
                                <span class="hour">18:00</span>
                            </div>
                            <div class="class-details">
                                <h4>HIIT Workout</h4>
                                <p>Instructor: John Smith</p>
                            </div>
                            <div class="class-action">
                                <button class="book-btn">Book</button>
                            </div>
                        </div>
                        
                        <div class="class-card">
                            <div class="class-time">
                                <span class="day">Tomorrow</span>
                                <span class="hour">10:00</span>
                            </div>
                            <div class="class-details">
                                <h4>Yoga Flow</h4>
                                <p>Instructor: Sarah Lee</p>
                            </div>
                            <div class="class-action">
                                <button class="book-btn">Book</button>
                            </div>
                        </div>
                        
                        <div class="class-card">
                            <div class="class-time">
                                <span class="day">May 11</span>
                                <span class="hour">19:30</span>
                            </div>
                            <div class="class-details">
                                <h4>Spin Class</h4>
                                <p>Instructor: Mike Johnson</p>
                            </div>
                            <div class="class-action">
                                <button class="book-btn">Book</button>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            
            <section id="plans-section" class="content-section">
    <div class="plans-header">
        <h3>Available Membership Plans</h3>
        <p>Choose the right plan for your fitness journey</p>
    </div>
    
    <c:if test="${empty plansList}">
        <div class="no-data-message">
            <i class="fas fa-info-circle"></i>
            <p>No membership plans are currently available.</p>
        </div>
    </c:if>
    
    <div class="plans-grid">
        <c:forEach var="plan" items="${plansList}">
            <div class="plan-card">
                <!-- Added plan image section -->
                <div class="plan-image">
                    <c:choose>
                        <c:when test="${not empty plan.imageUrlFromPart && plan.imageUrlFromPart != 'default.png'}">
                            <img src="${pageContext.request.contextPath}/resources/images/plan/${plan.imageUrlFromPart}" alt="${plan.plan_Name}">
                        </c:when>
                        <c:otherwise>
                            <img src="${pageContext.request.contextPath}/resources/images/plan/default.png" alt="Default Plan Image">
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="plan-header">
                    <h3>${plan.plan_Name}</h3>
                    <div class="plan-price">
                        <span class="currency">NPR</span>
                        <span class="amount">${plan.plan_Price}</span>
                        <span class="duration">/${plan.plan_Duration}</span>
                    </div>
                </div>
                
                <div class="plan-features">
                    <p>${plan.plan_Description}</p>
                </div>
                
                <form action="${pageContext.request.contextPath}/AddMemberShip" method="post">
    <input type="hidden" name="planId" value="${plan.plan_ID}">
<button type="submit" class="select-plan-btn">Add membership</button>
</form>
            </div>
        </c:forEach>
    </div>
</section>
            
            <!-- Profile Section -->
            <!-- Profile Section -->
 <!-- Profile Section -->

```
        <section id="profile-section" class="content-section">
    <c:if test="${not empty requestScope.updateProfile}">
        <div class="profile-header">
            <div class="profile-cover"></div>
            <div class="profile-avatar">
                <img src="${pageContext.request.contextPath}/resources/images/Member/${requestScope.updateProfile.imageUrlFromPart}" alt="Profile">
                <button class="change-avatar" type="button">
                    <i class="fas fa-camera"></i>
                </button>
            </div>
            <div class="profile-info-header">
                <h3>${requestScope.updateProfile.member_FullName}</h3>
            </div>
        </div>
        
        <div class="profile-content">
            <div class="profile-nav">
                <ul>
                    <li class="active"><a href="#personal-info">Personal Information</a></li>
                </ul>
            </div>
            
            <div class="profile-data" id="personal-info">
                <h3>Personal Information</h3>
                <form action="${pageContext.request.contextPath}/ModifyMember" method="post" enctype="multipart/form-data">
                    <div class="form-row">
                        <div class="form-group">
                            <label for="firstName">First Name</label>
                            <input type="text" id="firstName" name="firstName" value="${requestScope.updateProfile.member_FirstName}">
                        </div>
                        <div class="form-group">
                            <label for="lastName">Last Name</label>
                            <input type="text" id="lastName" name="lastName" value="${requestScope.updateProfile.member_LastName}">
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="email">Email Address</label>
                            <input type="email" id="email" name="email" value="${requestScope.updateProfile.member_Email}">
                        </div>
                        <div class="form-group">
                            <label for="phone">Phone Number</label>
                            <input type="tel" id="phone" name="phone" value="${requestScope.updateProfile.member_PhoneNumber}">
                        </div>
                    </div>
                    
                    <div class="form-row">
                        <div class="form-group">
                            <label for="age">Age</label>
                            <input type="text" id="age" name="age" value="${requestScope.updateProfile.member_Age}">
                        </div>
                        <div class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" value="${requestScope.updateProfile.member_Username}" readonly>
                        </div>
                    </div>
                    
                    <!-- Add file input for image upload -->
                    <div class="form-row">
                        <div class="form-group">
                            <label for="image">Profile Image</label>
                            <input type="file" id="image" name="image" accept="image/*">
                            <small>Current image: ${requestScope.updateProfile.imageUrlFromPart}</small>
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="save-btn">
                            <i class="fas fa-edit"></i> Save Changes
                        </button>
                        <input type="hidden" name="update_member_id" value="${requestScope.updateProfile.member_Username}">
                    </div>
                </form>
                <br>
                <form id="deleteForm" action="${pageContext.request.contextPath}/ModifyMember" method="post">
				    <button type="submit"  class="save-btn" onclick="return confirmDelete('${requestScope.updateProfile.member_FirstName}', '${requestScope.updateProfile.member_Username}')">
				        <i class="fas fa-trash"></i> Delete
				    </button>
				    <input type="hidden" name="delete_member_id" value="${requestScope.updateProfile.member_Username}">
				</form>
            </div>
        </div>
    </c:if>
</section>


            
            <!-- Enquiry Section -->
            <section id="enquiry-section" class="content-section">
                <div class="enquiry-grid">
                    <div class="enquiry-form-container">
                        <div class="container-header">
                            <h3><i class="fas fa-paper-plane"></i> Send a New Enquiry</h3>
                        </div>
                        
                        <form   class="enquiry-form" action="${pageContext.request.contextPath}/addEnquiry" method="post">
                            <div class="form-group">
                                <label for="subject">Subject</label>
                                <input type="text" id="subject" name="subject" placeholder="Enter the subject of your enquiry">
                            </div>
                            

                            
                            <div class="form-group">
                                <label for="message">Message</label>
                                <textarea id="message" name="message" rows="6" placeholder="Please describe your enquiry in detail..."></textarea>
                            </div>
                            
                            <div class="form-actions">
                                <button type="submit" class="send-btn">Send Message</button>
                            </div>
                        </form>
                    </div>
                    
                    
                </div>
                
                <div class="contact-info-container">
                    <div class="container-header">
                        <h3><i class="fas fa-info-circle"></i> Gym Contact Information</h3>
                    </div>
                    
                    <div class="contact-info-grid">
                        <div class="contact-item">
                            <div class="contact-icon">
                                <i class="fas fa-map-marker-alt"></i>
                            </div>
                            <div class="contact-text">
                                <h4>Address</h4>
                                <p>456 Gym Avenue, Fitness City, FC 12345</p>
                            </div>
                        </div>
                        
                        <div class="contact-item">
                            <div class="contact-icon">
                                <i class="fas fa-phone"></i>
                            </div>
                            <div class="contact-text">
                                <h4>Phone</h4>
                                <p>(555) 987-6543</p>
                            </div>
                        </div>
                        
                        <div class="contact-item">
                            <div class="contact-icon">
                                <i class="fas fa-envelope"></i>
                            </div>
                            <div class="contact-text">
                                <h4>Email</h4>
                                <p>support@fitneek.com</p>
                            </div>
                        </div>
                        
                        <div class="contact-item">
                            <div class="contact-icon">
                                <i class="fas fa-clock"></i>
                            </div>
                            <div class="contact-text">
                                <h4>Opening Hours</h4>
                                <p>Mon-Fri: 5am-11pm</p>
                                <p>Sat-Sun: 7am-9pm</p>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
            
            <!-- Footer -->
            <footer class="footer">
                <p>&copy; 2025 FitNeek Fitness Center. All rights reserved.</p>
            </footer>
        </main>
    </div>

   <script>
   document.addEventListener('DOMContentLoaded', function() {
	    // Elements
	    const sidebar = document.getElementById('sidebar');
	    const sidebarToggle = document.getElementById('sidebar-toggle');
	    const navItems = document.querySelectorAll('.sidebar-nav ul li');
	    const contentSections = document.querySelectorAll('.content-section');
	    const pageTitle = document.getElementById('page-title');
	    
	    // Toggle sidebar expand/collapse
	    sidebarToggle.addEventListener('click', function() {
	        sidebar.classList.toggle('expanded');
	    });
	    
	    // Handle tab switching
	    navItems.forEach(item => {
	        item.addEventListener('click', function(e) {
	            e.preventDefault();
	            
	            // Get the section ID from data attribute
	            const sectionId = this.getAttribute('data-section');
	            
	            // Update active class on nav items
	            navItems.forEach(navItem => navItem.classList.remove('active'));
	            this.classList.add('active');
	            
	            // Show the correct content section
	            contentSections.forEach(section => {
	                section.classList.remove('active');
	                if (section.id === sectionId) {
	                    section.classList.add('active');
	                    
	                    // Update page title based on active section
	                    switch(sectionId) {
	                        case 'dashboard-section':
	                            pageTitle.textContent = 'Dashboard';
	                            break;
	                        case 'plans-section':
	                            pageTitle.textContent = 'Membership Plans';
	                            break;
	                        case 'profile-section':
	                            pageTitle.textContent = 'My Profile';
	                            break;
	                        case 'enquiry-section':
	                            pageTitle.textContent = 'Enquiries';
	                            break;
	                        default:
	                            pageTitle.textContent = 'Dashboard';
	                    }
	                }
	            });
	            
	            // On mobile, collapse sidebar after selection
	            if (window.innerWidth <= 768) {
	                sidebar.classList.remove('expanded');
	            }
	        });
	    });
	    
	    // Handle responsive sidebar behavior
	    function handleResponsiveSidebar() {
	        if (window.innerWidth <= 768) {
	            sidebar.classList.remove('expanded');
	        } else if (window.innerWidth <= 992) {
	            // For tablets, default to collapsed but not hidden
	            sidebar.classList.remove('expanded');
	        } else {
	            // For desktops, expand the sidebar
	            sidebar.classList.add('expanded');
	        }
	    }
	    
	    // Initialize responsive behavior
	    handleResponsiveSidebar();
	    
	    // Listen for window resize events
	    window.addEventListener('resize', handleResponsiveSidebar);
	});
   
   function confirmDelete(name, id) {
	    if(confirm("Are you sure you want to delete your profile " + name + "?")) {
	        return true;
	    } else{
	    	return false;
	    }
	   
	}
   </script>
</body>
</html>