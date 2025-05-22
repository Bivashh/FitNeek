<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
// Get the session and request objects
HttpSession userSession = request.getSession();
String currentUserType = (String) userSession.getAttribute("type");
String currentUser = (String) userSession.getAttribute("username");
String contextPath = request.getContextPath();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Fitness Plan</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/updatePlan.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
<jsp:include page="message-display.jsp" />
    <div class="page-container">
        <div class="sidebar">
            <div class="logo">
                <i class="fas fa-dumbbell"></i>
                <h2>FitPro</h2>
            </div>
            <div class="sidebar-content">
                <div class="welcome-text">
                    <h3>Welcome, Admin!</h3>
                    <p>Update your fitness plan details below</p>
                </div>
                <div class="action-buttons">
                    <button class="back-btn" onclick="location.href='${pageContext.request.contextPath}/AdminDashboardServlet'">
                        <i class="fas fa-arrow-left"></i> Back to Plans
                    </button>
                </div>
            </div>
        </div>

        <div class="main-content">
            <div class="header">
                <h1>Update Fitness Plan</h1>
                <p>Make changes to your existing fitness plan</p>
            </div>

            <div class="form-container">
                <form action="${pageContext.request.contextPath}/ModifyPlanServlet" method="post" enctype="multipart/form-data">
                    <div class="form-grid">
                        <div class="form-column">
                            <div class="form-group">
                                <label for="plan_name">Plan Name</label>
                                <input type="text" id="plan_name" name="plan_name" value="${updatePlan.plan_Name}" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="plan_duration">Duration</label>
                                <input type="text" id="plan_duration" name="plan_duration" value="${updatePlan.plan_Duration}" required>
                            </div>
                            
                            <div class="form-group">
                                <label for="plan_price">Price ($)</label>
                                <input type="text" id="plan_price" name="plan_price" value="${updatePlan.plan_Price}" required>
                            </div>
                        </div>
                        
                        <div class="form-column">
                            <div class="form-group image-upload-container">
                                <label>Current Image</label>
                                <div class="image-preview">
                                    <img id="currentImage" src="${pageContext.request.contextPath}/resources/images/plan/${updatePlan.imageUrlFromPart}" alt="${updatePlan.plan_Name}">
                                </div>
                                
                                <label for="plan_image" class="custom-file-upload">
                                    <i class="fas fa-cloud-upload-alt"></i> Choose New Image
                                </label>
                                <input type="file" id="plan_image" name="plan_image" accept="image/*">
                                <p class="file-name">No file chosen</p>
                            </div>
                        </div>
                    </div>
                    
                    <div class="form-group description-group">
                        <label for="plan_description">Plan Description</label>
                        <textarea id="plan_description" name="plan_description" rows="5" required>${updatePlan.plan_Description}</textarea>
                    </div>

                    <!-- Hidden fields -->
                    <input type="hidden" name="admin_id" value="${updatePlan.admin_ID}">
                    <input type="hidden" name="update_plan_id" value="${updatePlan.plan_ID}">

                    <div class="form-actions">
                        <button type="button" class="cancel-btn" onclick="location.href='${pageContext.request.contextPath}/AdminDashboardServlet'">Cancel</button>
                        <button type="submit" class="update-btn">Update Plan</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Display image preview when a new file is selected
        document.getElementById('plan_image').addEventListener('change', function() {
            if (this.files && this.files[0]) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    document.getElementById('currentImage').src = e.target.result;
                }
                reader.readAsDataURL(this.files[0]);
                
                // Update file name display
                const fileName = this.files[0].name;
                document.querySelector('.file-name').textContent = fileName;
            }
        });
        
        // Show file name on hover
        const fileInput = document.getElementById('plan_image');
        const fileNameDisplay = document.querySelector('.file-name');
        
        document.querySelector('.custom-file-upload').addEventListener('mouseenter', function() {
            if (fileInput.files.length > 0) {
                fileNameDisplay.style.opacity = '1';
            }
        });
        
        document.querySelector('.custom-file-upload').addEventListener('mouseleave', function() {
            fileNameDisplay.style.opacity = '0';
        });
    </script>
</body>
</html>