<%@page import="util.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%
    //Get the session and request objects
    HttpSession userSession = request.getSession();
    String currentUserType = (String) userSession.getAttribute("type");
    String currentUser = (String) userSession.getAttribute("username");
    String contextPath = request.getContextPath();
    
    // Get error message if any
    String errorMessage = (String) request.getAttribute(StringUtils.MESSAGE_ERROR);
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addPlan.css" />
<title>Add Gym Plan</title>
</head>
<body>
<jsp:include page="message-display.jsp" />
<div class="admin-plan-container">
    <span><a href="${pageContext.request.contextPath}<%=StringUtils.SERVLET_URL_ADMIN_DASHBOARD%>" class="link signup-link">Go back</a></span>
    <h1 class="admin-plan-title">Add New Gym Plan</h1>
    
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div class="error-message">
            <%= errorMessage %>
        </div>
    <% } %>
    
    <form action="${pageContext.request.contextPath}/AddPlanServlet" method="post" enctype="multipart/form-data" class="admin-plan-form">

        
        <div class="admin-plan-form-group">
            <label for="planName" class="admin-plan-label">Plan Name:</label>
            <input type="text" id="planName" name="planName" placeholder="Enter Plan Name" required class="admin-plan-input">
        </div>
        
        <div class="admin-plan-form-group">
            <label for="planDescription" class="admin-plan-label">Plan Description:</label>
            <textarea id="planDescription" name="planDescription" placeholder="Enter detailed description of the plan" required class="admin-plan-textarea"></textarea>
        </div>
        
        <div class="admin-plan-form-group">
            <label for="planDuration" class="admin-plan-label">Plan Duration:</label>
            <select id="planDuration" name="planDuration" required class="admin-plan-select">
                <option value="">Select Duration</option>
                <option value="1 Week">1 Week</option>
                <option value="2 Weeks">2 Weeks</option>
                <option value="1 Month">1 Month</option>
                <option value="3 Months">3 Months</option>
                <option value="6 Months">6 Months</option>
                <option value="1 Year">1 Year</option>
                <option value="Lifetime">Lifetime</option>
            </select>
        </div>
        
        <div class="admin-plan-form-group">
            <label for="planPrice" class="admin-plan-label">Plan Price:</label>
            <div class="admin-plan-input-group">
                <span class="admin-plan-currency-symbol">$</span>
                <input type="text" id="planPrice" name="planPrice" placeholder="Enter price" class="admin-plan-input">
            </div>
        </div>
        
        <div class="admin-plan-form-group">
            <label for="planImage" class="admin-plan-label">Plan Image:</label>
            <div class="admin-plan-file-input-container">
                <label class="admin-plan-file-input-label">
                    Choose Image
                    <input type="file" id="planImage" name="planImage" accept="image/*" onchange="adminPreviewPlanImage(this)" class="admin-plan-file-input">
                </label>
                <div class="admin-plan-file-name" id="adminPlanFileName">No file chosen</div>
            </div>
            <div class="admin-plan-preview-container" id="adminPlanImagePreview" style="display: none;">
                <img src="" alt="Preview" class="admin-plan-preview-image" id="adminPlanPreviewImg">
            </div>
        </div>
        
        <!-- Use the current user as admin ID if available -->
        <input type="hidden" id="adminId" name="adminId" value="<%= currentUser != null ? currentUser : "" %>" class="admin-plan-hidden">
        
        <div class="admin-plan-btn-container">
            <button type="submit" class="admin-plan-btn admin-plan-btn-primary">Add Plan</button>
            <button type="reset" class="admin-plan-btn admin-plan-btn-secondary" onclick="clearImagePreview()">Clear Form</button>
        </div>
    </form>
</div>

<script>
function adminPreviewPlanImage(input) {
    const fileNameElement = document.getElementById('adminPlanFileName');
    const previewContainer = document.getElementById('adminPlanImagePreview');
    const previewImg = document.getElementById('adminPlanPreviewImg');
    
    if (input.files && input.files[0]) {
        const fileName = input.files[0].name;
        fileNameElement.textContent = fileName;
        
        const reader = new FileReader();
        reader.onload = function(e) {
            previewImg.src = e.target.result;
            previewContainer.style.display = 'block';
        }
        reader.readAsDataURL(input.files[0]);
    } else {
        fileNameElement.textContent = 'No file chosen';
        previewContainer.style.display = 'none';
    }
}

function clearImagePreview() {
    document.getElementById('adminPlanFileName').textContent = 'No file chosen';
    document.getElementById('adminPlanImagePreview').style.display = 'none';
    document.getElementById('adminPlanPreviewImg').src = '';
}
</script>
</body>
</html>