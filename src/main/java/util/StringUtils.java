package util;

import java.io.File;

public class StringUtils {
    // Database connection constants
    public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String LOCALHOST_URL = "jdbc:mysql://localhost:3306/fitneek";
    public static final String LOCALHOST_USERNAME = "root";
    public static final String LOCALHOST_PASSWORD = "";
    
    // Uploaded image paths
    public static final String IMAGE_DIR_MEMBER = "C:\\Users\\dhaka\\OneDrive\\Documents\\Desktop\\FitNeeks\\FitNeeks\\src\\main\\webapp\\resources\\images\\Member\\";
    public static final String IMAGE_DIR_SAVE_PATH_MEMBER = "C:" + File.separator + IMAGE_DIR_MEMBER;
    
    public static final String IMAGE_DIR_ADMIN = "C:\\Users\\dhaka\\OneDrive\\Documents\\Desktop\\FitNeeks\\FitNeeks\\src\\main\\webapp\\resources\\images\\Admin\\";
    public static final String IMAGE_DIR_SAVE_PATH_ADMIN = "C:" + File.separator + IMAGE_DIR_ADMIN;
    
    public static final String IMAGE_DIR_PLAN = "C:\\Users\\dhaka\\OneDrive\\Documents\\Desktop\\FitNeeks\\FitNeeks\\src\\main\\webapp\\resources\\images\\plan\\";
    public static final String IMAGE_DIR_SAVE_PATH_PLAN = "C:" + File.separator + IMAGE_DIR_PLAN;
    
    // Query constants - Member registration and login
    public static final String QUERY_REGISTER_USER = "INSERT INTO member ("+
            "username, password, first_name, last_name, email, phone_number, age, profile_image) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    
    public static final String QUERY_LOGIN_USER_CHECK = "SELECT * FROM member WHERE username = ?";
    public static final String QUERY_GET_USER_IMAGE = "SELECT profile_image FROM member WHERE username = ?";
    
    // Query constants - Admin 
    public static final String QUERY_LOGIN_ADMIN_CHECK = "SELECT * FROM admin WHERE username = ?";
    public static final String QUERY_GET_ADMIN_IMAGE = "SELECT image FROM admin WHERE username = ?";
    
    // Query constants - Membership and Plans
    public static final String QUERY_ADD_PLANS = "INSERT INTO plan ("+
            "plan_name, description, duration, price, admin_id, image) "
            + "VALUES (?, ?, ?, ?, ?, ?)";
    
    public static final String QUERY_GET_PLAN_INFO_FROM_PLAN_ID =  "SELECT * FROM plan WHERE plan_id = ?";
    public static final String QUERY_GET_ALL_PLANS = "SELECT * FROM plan";
    public static final String QUERY_GET_ALL_MEM_INFO = "SELECT * FROM member";
    public static final String QUERY_GET_ALL_MEMBERSHIP_INFO = "SELECT * FROM membership";
    public static final String QUERY_GET_ALL_ENQUIRY_INFO = "SELECT * FROM enquiry";
    
    public static final String QUERY_GET_ADMIN_INFO_FROM_ADMIN_ID = "SELECT * FROM admin WHERE username = ?";
    public static final String QUERY_GET_MEM_INFO_FROM_MEM_ID = "SELECT * FROM member WHERE username = ?";
    
    
    public static final String QUERY_GET_PLAN_BY_ID = "SELECT * FROM plan WHERE plan_id = ?";
    public static final String QUERY_GET_MEMBER_MEMBERSHIPS = "SELECT * FROM membership WHERE member_id = ?";
    
    // Query constants - Enquiries
    public static final String QUERY_ADD_ENQUIRY = "INSERT INTO enquiry (subject, message, member_id, enquiry_date) VALUES (?, ?, ?, ?)";
    public static final String QUERY_GET_ALL_ENQUIRIES = "SELECT * FROM enquiry";
    
    
    public static final String QUERY_ADD_MEMBERSHIP = "INSERT INTO membership (member_id, plan_id, start_date) VALUES (?, ?, ?)";
    
    // QUERY UPDATE PLAN
    public static final String QUERY_UPDATE_PLAN = "UPDATE plan SET plan_name = ?, image = ?, duration = ?, description = ?, price = ?, admin_id = ? WHERE plan_id = ?";
    
    public static final String QUERY_UPDATE_MEMBER = "UPDATE member SET first_name = ?, last_name = ?, email = ?, phone_number = ?, age = ?, profile_image = ? WHERE username = ?";
    
    public static final String QUERY_UPDATE_ADMIN = 
    	    "UPDATE admin SET full_name = ?, email = ?, phone_number = ?, image = ? WHERE username = ?";
    
    // Member form field constants
    public static final String MEMBER_ID = "memberId";
    public static final String MEMBER_USERNAME = "member_Username";
    public static final String MEMBER_PASSWORD = "member_Password";
    public static final String MEMBER_FIRST_NAME = "member_FirstName";
    public static final String MEMBER_LAST_NAME = "member_LastName";
    public static final String MEMBER_EMAIL = "member_Email";
    public static final String MEMBER_PHONE_NUM = "member_PhoneNumber";
    public static final String MEMBER_AGE = "member_Age";
    public static final String MEMBER_IMAGE = "member_Image";
    
    // Admin form field constants
    public static final String ADMIN_ID = "adminId";
    public static final String ADMIN_USERNAME = "admin_Username";
    public static final String ADMIN_PASSWORD = "admin_Password";
    public static final String ADMIN_FIRST_NAME = "admin_FirstName";
    public static final String ADMIN_LAST_NAME = "admin_LastName";
    public static final String ADMIN_IMAGE = "admin_Image";
    
    // Login Parameter names
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String USER_TYPE = "userType";
    
    // Message constants
    public static final String MESSAGE_SUCCESS_REGISTER = "Successfully Registered!";
    public static final String MESSAGE_ERROR_REGISTER = "Registration failed. Please check your details.";
    public static final String MESSAGE_SUCCESS_LOGIN = "Successfully Logged In!";
    public static final String MESSAGE_ERROR_LOGIN = "Invalid username or password.";
    public static final String MESSAGE_ERROR_SERVER = "An unexpected server error occurred.";
    
    public static final String MESSAGE_SUCCESS = "successMessage";
    public static final String MESSAGE_SUCCESS_DELETE = "Deleted Successfully";
    public static final String MESSAGE_ERROR = "errorMessage";
    
    // JSP route constants
    public static final String PAGE_URL_LOGIN = "/pages/login.jsp";
    public static final String PAGE_URL_REGISTER_MEMBER = "/pages/register.jsp";
    public static final String PAGE_URL_MEMBER_DASHBOARD = "/pages/memDashboard.jsp";
    public static final String PAGE_URL_ADMIN_DASHBOARD = "/pages/adminDashboard.jsp";
    public static final String PAGE_URL_PLANS = "/pages/addPlan.jsp";
    public static final String PAGE_URL_PROFILE = "/pages/profile.jsp";
    public static final String PAGE_URL_HOME = "/Home.jsp";
    public static final String PAGE_URL_ENQUIRIES = "/pages/enquiries.jsp";
    public static final String PAGE_URL_UPDATE_PLAN = "/pages/updatePlan.jsp";
    
    // Servlet URL constants
    public static final String SERVLET_URL_REGISTER_MEMBER = "/MemberRegistrationServlet";
    public static final String SERVLET_URL_LOGIN = "/LoginServlet";
    public static final String SERVLET_URL_LOGOUT = "/LogoutServlet";
    public static final String SERVLET_URL_MEMBER_DASHBOARD = "/MemberDashboardServlet";
    public static final String SERVLET_URL_ADMIN_DASHBOARD = "/AdminDashboardServlet";
    public static final String SERVLET_URL_UPDATE_PROFILE = "/UpdateProfileServlet";
    public static final String SERVLET_URL_ADD_ENQUIRY = "/AddEnquiryServlet";
    public static final String SERVLET_URL_ADD_PLAN = "/AddPlanServlet";
    public static final String SERVLET_UPDATE_PLAN = "/ModifyPlanServlet";
    
    // Session and cookie attribute names
    public static final String SESSION_MEMBER = "member";
    public static final String SESSION_ADMIN = "admin";
    public static final String SESSION_USERNAME = "username";
    public static final String SESSION_TYPE = "type";
    public static final String COOKIE_MEMBER = "member";
    public static final String COOKIE_ADMIN = "admin";
    
    // List attribute names
    public static final String PLANS_LIST = "plansList";
    public static final String MEMBERSHIPS_LIST = "membershipsList";
    public static final String ENQUIRIES_LIST = "enquiriesList";
    public static final String MEMBERS_LIST = "membersList";
    
    
    public static final String SUCCESS_MESSAGE = "successMessage";
    public static final String ERROR_MESSAGE = "errorMessage";
    
    // Common success messages
    public static final String MESSAGE_SUCCESS_UPDATE = "Updated successfully!";
    public static final String MESSAGE_SUCCESS_ADD = "Added successfully!";
    public static final String MESSAGE_SUCCESS_ENQUIRY = "Enquiry submitted successfully!";
    public static final String MESSAGE_SUCCESS_MEMBERSHIP = "Membership activated successfully!";
    
    // Common error messages

    public static final String MESSAGE_ERROR_UPDATE = "Failed to update. Please try again.";
    public static final String MESSAGE_ERROR_DELETE = "Failed to delete. Please try again.";
    public static final String MESSAGE_ERROR_ADD = "Failed to add. Please try again.";
}