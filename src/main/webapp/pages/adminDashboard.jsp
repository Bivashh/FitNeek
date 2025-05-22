<%@page import="util.StringUtils"%>
<%@page import="model.Member"%>
<%@page import="model.Membership"%>
<%@page import="model.Plan"%>
<%@page import="model.Enquiry"%>
<%@page import="model.Admin"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<%
    // Get the session and request objects
    HttpSession userSession = request.getSession();
    String currentUserType = (String) userSession.getAttribute("type");
    String currentUser = (String) userSession.getAttribute("username");
    String contextPath = request.getContextPath();
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/adminDash.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" />
<title>FitNeek Admin Dashboard</title>
<style>
    /* Additional CSS for plan images */
    .plan-card {
        position: relative;
        display: flex;
        flex-direction: column;
    }
    
    .plan-image {
        width: 100%;
        height: 180px;
        overflow: hidden;
        border-radius: 8px 8px 0 0;
        margin-bottom: 15px;
    }
    
    .plan-image img {
        width: 100%;
        height: 100%;
        object-fit: cover;
    }
</style>
</head>
<body>
<jsp:include page="message-display.jsp" />

 <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <div class="logo">
                <h2>FitNeek</h2>
            </div>
            <ul class="nav-links">
                <li class="active"><a href="#dashboard"><i class="fas fa-home"></i> Dashboard</a></li>
                <li><a href="#users"><i class="fas fa-users"></i> Manage Users</a></li>
                <li><a href="#plans"><i class="fas fa-dumbbell"></i> Gym Plans</a></li>
                <li><a href="#requests"><i class="fas fa-user-plus"></i> Membership Requests</a></li>
                <li><a href="#enquiries"><i class="fas fa-envelope"></i> Enquiries</a></li>
                <li><a href="#profile"><i class="fas fa-user-circle"></i> My Profile</a></li>
            </ul>
        </div>

        <!-- Main Content -->
        <div class="main-content">
            <!-- Top Bar -->
            <div class="top-bar">
                <div class="search-box">
                    <i class="fas fa-search"></i>
                    <input type="text" placeholder="Search...">
                </div>
                <div class="user-info">
                    <div class="notifications">
                        <i class="fas fa-bell"></i>
                        <span class="badge">3</span>
                    </div>
                    <div class="admin-profile">
                        <img src="${pageContext.request.contextPath}/resources/images/Admin/${requestScope.updateProfile.imageUrlFromPart}" alt="Profile">
                        <span>${requestScope.updateProfile.admin_FullName}</span>
                    </div>
                    <a href="${pageContext.request.contextPath}${StringUtils.SERVLET_URL_LOGOUT}" class="logout-btn"><i class="fas fa-sign-out-alt"></i> Logout</a>
                </div>
            </div>

            <!-- Dashboard Section -->
            <div class="section" id="dashboard">
                <h2>Dashboard Overview</h2>
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-value">
                            <c:choose>
                                <c:when test="${not empty requestScope.membersList}">
                                    ${requestScope.membersList.size()}
                                </c:when>
                                <c:otherwise>0</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="stat-label">Total Users</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-value">
                            <c:choose>
                                <c:when test="${not empty requestScope.plansList}">
                                    ${requestScope.plansList.size()}
                                </c:when>
                                <c:otherwise>0</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="stat-label">Active Plans</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-value">
                            <c:choose>
                                <c:when test="${not empty requestScope.membershipsList}">
                                    ${requestScope.membershipsList.size()}
                                </c:when>
                                <c:otherwise>0</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="stat-label">Memberships</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-value">
                            <c:choose>
                                <c:when test="${not empty requestScope.enquiriesList}">
                                    ${requestScope.enquiriesList.size()}
                                </c:when>
                                <c:otherwise>0</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="stat-label">Enquiries</div>
                    </div>
                </div>
            </div>

            <!-- Manage Users Section -->
            <div class="section" id="users">
                <h2>Manage Users</h2>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Username</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Phone Number</th>
                                <th>Age</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty requestScope.membersList}">
                                    <c:forEach var="member" items="${requestScope.membersList}">
                                        <tr>
                                            <td>${member.member_Username}</td>
                                            <td>${member.member_FirstName} ${member.member_LastName}</td>
                                            <td>${member.member_Email}</td>
                                            <td>${member.member_PhoneNumber}</td>
                                            <td>${member.member_Age}</td>
                                            <td>
                                                
                                                
                                                <form id="deleteForm-${member.member_Username}" action="${pageContext.request.contextPath}/ModifyMember" method="post">
												    <button type="button" class="btn delete-btn" onclick="if(confirmDelete('${member.member_FirstName}', '${member.member_Username}')) this.form.submit();">
												        <i class="fas fa-trash"></i> Delete
												    </button>
												    <input type="hidden" name="delete_member_id" value="${member.member_Username}">
												</form>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6">No members found</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Gym Plans Section -->
            <div class="section" id="plans">
                <h2>Gym Plans</h2>
                <a href="${pageContext.request.contextPath}${StringUtils.PAGE_URL_PLANS}" class="add-plan-btn"><i class="fas fa-plus"></i> Add new Plan</a>
                <div class="plans-container">
                    <c:choose>
                        <c:when test="${not empty requestScope.plansList}">
                            <c:forEach var="plan" items="${requestScope.plansList}">
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
                                    <h3>${plan.plan_Name}</h3>
                                    <p class="price">$${plan.plan_Price}</p>
                                    <p>${plan.plan_Description}</p>
                                    <p>Duration: ${plan.plan_Duration}</p>
                                    <div class="plan-actions">
								    <form action="${pageContext.request.contextPath}/ModifyPlanServlet" method="get">
								        <button class = "logout-btn" ><i class="fas fa-edit"></i> Edit</button>
								        <input type="hidden" name="update_plan_id" value="${plan.plan_ID}">
								    </form>
								    <form id="deleteForm-${plan.plan_ID}" action="${pageContext.request.contextPath}/ModifyPlanServlet" method="post">
									    <button type="button" class="logout-btn" onclick="if(confirmDelete('${plan.plan_Name}', '${plan.plan_ID}')) this.form.submit();">
									        <i class="fas fa-trash"></i> Delete
									    </button>
									    <input type="hidden" name="delete_plan_id" value="${plan.plan_ID}">
									</form>
								</div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="plan-card">
                                <h3>No plans available</h3>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Membership Requests Section -->
            <div class="section" id="requests">
                <h2>Membership Requests</h2>
                <div class="table-container">
                    <table>
                        <thead>
                            <tr>
                                <th>Membership ID</th>
                                <th>Member</th>
                                <th>Plan</th>
                                <th>Start Date</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:choose>
                                <c:when test="${not empty requestScope.membershipsList}">
                                    <c:forEach var="membership" items="${requestScope.membershipsList}">
                                        <tr>
                                            <td>${membership.membership_ID}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty membership.member}">
                                                        ${membership.member.member_FirstName} ${membership.member.member_LastName}
                                                    </c:when>
                                                    <c:otherwise>
                                                        Member ID: ${membership.member_ID}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>${membership.plan_ID}</td>
                                            <td>${membership.start_Date}</td>
                                            <td>
                                                <button class="btn approve-btn" data-id="${membership.membership_ID}"><i class="fas fa-check"></i> Approve</button>
                                                <button class="btn reject-btn" data-id="${membership.membership_ID}"><i class="fas fa-times"></i> Reject</button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td colspan="6">No membership requests found</td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- Enquiries Section -->
            <div class="section" id="enquiries">
                <h2>User Enquiries</h2>
                <div class="enquiries-container">
                    <c:choose>
                        <c:when test="${not empty requestScope.enquiriesList}">
                            <c:forEach var="enquiry" items="${requestScope.enquiriesList}">
                                <div class="enquiry-card">
                                    <div class="enquiry-header">
                                        <h3>${enquiry.subject}</h3>
                                        <span class="timestamp">${enquiry.enquiry_Date}</span>
                                    </div>
                                    <p class="enquiry-sender">
                                        From: 
                                        <c:choose>
                                            <c:when test="${not empty enquiry.member}">
                                                ${enquiry.member.member_FirstName} ${enquiry.member.member_LastName} 
                                                (${enquiry.member.member_Email})
                                            </c:when>
                                            <c:otherwise>
                                                Member ID: ${enquiry.member_ID}
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p class="enquiry-message">
                                        ${enquiry.message}
                                    </p>
                                    <div class="enquiry-actions">
                                        <button class="btn reply-btn" data-id="${enquiry.enquiry_ID}"><i class="fas fa-reply"></i> Reply</button>
                                        <button class="btn mark-btn" data-id="${enquiry.enquiry_ID}"><i class="fas fa-check"></i> Mark as Resolved</button>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="enquiry-card">
                                <div class="enquiry-header">
                                    <h3>No enquiries found</h3>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <!-- Profile Section -->
            <div class="section" id="profile">
    <h2>My Profile</h2>
    <div class="profile-container">
        <div class="profile-image">
            <img src="${pageContext.request.contextPath}/resources/images/Admin/${requestScope.updateProfile.imageUrlFromPart}" alt="Profile">
            <button class="change-photo-btn" type="button" onclick="document.getElementById('image').click();">Change Photo</button>
        </div>
        <div class="profile-details">
            <c:if test="${not empty requestScope.updateProfile}">
                <form action="${pageContext.request.contextPath}/ModifyAdmin" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="adminID" value="${requestScope.updateProfile.admin_ID}">
                    <div class="form-group">
                        <label for="adminName">Full Name</label>
                        <input type="text" id="adminName" name="adminFullName" value="${requestScope.updateProfile.admin_FullName}">
                    </div>
                    <div class="form-group">
                        <label for="adminUsername">Username</label>
                        <input type="text" id="adminUsername" name="adminUsername" value="${requestScope.updateProfile.admin_Username}" readonly>
                    </div>
                    <div class="form-group">
                        <label for="adminEmail">Email</label>
                        <input type="email" id="adminEmail" name="adminEmail" value="${requestScope.updateProfile.admin_Email}">
                    </div>
                    <div class="form-group">
                        <label for="adminPhone">Phone</label>
                        <input type="text" id="adminPhone" name="adminPhoneNumber" value="${requestScope.updateProfile.admin_PhoneNumber}">
                    </div>
                    
                    <!-- Hidden file input for image upload -->
                    <div class="form-group" style="display: none;">
                        <input type="file" id="image" name="image" accept="image/*">
                        <small>Current image: ${requestScope.updateProfile.imageUrlFromPart}</small>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="save-btn">
                            <i class="fas fa-edit"></i> Save Changes
                        </button>
                        <input type="hidden" name="update_admin_id" value="${requestScope.updateProfile.admin_Username}">
                    </div>
                </form>
                <br>
                <form id="deleteForm" action="${pageContext.request.contextPath}/ModifyAdmin" method="post">
                    <button type="submit" class="delete-btn" onclick="return confirmDelete('${requestScope.updateProfile.admin_FullName}', '${requestScope.updateProfile.admin_Username}')">
                        <i class="fas fa-trash"></i> Delete Account
                    </button>
                    <input type="hidden" name="delete_admin_id" value="${requestScope.updateProfile.admin_Username}">
                </form>
            </c:if>
        </div>
    </div>
</div>
        </div>
    </div>

<script>
    // JavaScript to handle the tab switching
    document.addEventListener('DOMContentLoaded', function() {
        // Get all navigation links
        document.querySelectorAll('.nav-links a').forEach(link => {
            link.addEventListener('click', function(e) {
                e.preventDefault();
                
                // Hide all sections
                document.querySelectorAll('.section').forEach(section => {
                    section.style.display = 'none';
                });
                
                // Remove active class from all links
                document.querySelectorAll('.nav-links li').forEach(item => {
                    item.classList.remove('active');
                });
                
                // Add active class to clicked link
                this.parentElement.classList.add('active');
                
                // Show the corresponding section
                const targetId = this.getAttribute('href').substring(1);
                document.getElementById(targetId).style.display = 'block';
            });
        });
        
        // Show dashboard by default
        document.querySelectorAll('.section').forEach(section => {
            if(section.id !== 'dashboard') {
                section.style.display = 'none';
            }
        });
    });
    
    function confirmDelete(name, id) {
        if(confirm("Are you sure you want to delete " + name + "?")) {
            return true; // Proceed with form submission
        } else {
            return false; // Cancel form submission
        }
    }
</script>
</body>
</html>