<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%
String contextPath = request.getContextPath();
String errMsg = (String) request.getAttribute(StringUtils.MESSAGE_ERROR);
String successMsg = (String) request.getAttribute(StringUtils.MESSAGE_SUCCESS);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css" />
    <title>Member Registration</title>
</head>

<body>
    <div class="page-container">
        <div class="form-wrapper">
            <div class="form-container">
                <div class="form-header">
                    <h1>Create Member Account</h1>
                    <p>Please fill in your details to register</p>
                </div>
                
                <% if (errMsg != null) { %>
                    <div class="message error-message">
                        <%= errMsg %>
                    </div>
                <% } %>
                
                <% if (successMsg != null) { %>
                    <div class="message success-message">
                        <%= successMsg %>
                    </div>
                <% } %>
                
                <form action="<%=contextPath%>/MemberRegistrationServlet" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <div class="form-row">
                            <div class="input-group">
                                <label for="username">Username</label>
                                <input type="text" id="username" name="member_Username" placeholder="johnsmith" >
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="input-group">
                                <label for="firstName">First Name</label>
                                <input type="text" id="firstName" name="member_FirstName" placeholder="John" >
                            </div>
                            <div class="input-group">
                                <label for="lastName">Last Name</label>
                                <input type="text" id="lastName" name="member_LastName" placeholder="Smith" >
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="input-group">
                                <label for="email">Email</label>
                                <input type="email" id="email" name="member_Email" placeholder="john.smith@example.com" >
                            </div>
                            <div class="input-group">
                                <label for="phoneNumber">Phone Number</label>
                                <input type="text" id="phoneNumber" name="member_PhoneNumber" placeholder="9877211233" >
                            </div>
                        </div>
                        
                        <div class="form-row">
                            <div class="input-group">
                                <label for="password">Password</label>
                                <input type="password" id="password" name="member_Password" >
                            </div>
                            <div class="input-group">
                                <label for="password">Confirm Password</label>
                                <input type="password" id="password" name="confirmPassword" >
                            </div>
                            <div class="input-group">
                                <label for="age">Age</label>
                                <input type="number" id="age" name="member_Age" placeholder="25" min="1" >
                            </div>
                        </div>
                        
                        <div class="form-row file-upload">
                            <div class="input-group">
                                <label for="profileImage">Profile Image</label>
                                <input type="file" id="profileImage" name="member_Image" accept="image/*">
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-actions">
                        <button type="submit" class="btn-register">Create Account</button>
                        <div class="login-link">
                            Already have an account? <a href="${pageContext.request.contextPath}<%=StringUtils.PAGE_URL_LOGIN%>">Sign In</a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        
        <div class="image-container">
            <div class="overlay-text">
                <h2>Welcome to Our Community</h2>
                <p>Join us today and unlock exclusive member benefits</p>
            </div>
        </div>
    </div>
</body>
</html>