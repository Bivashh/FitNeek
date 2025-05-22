package controller.servlets;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import util.StringUtils;
import util.ValidationUtil;
import controller.database.DbController;
import model.Enquiry;

/**
 * Servlet implementation class AddEnquiryServlet
 */
public class addEnquiry extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DbController dbController;
    
    public addEnquiry() {
        this.dbController = new DbController();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get the current session
            HttpSession session = request.getSession();
            
            // Get member ID from session
            String memberId = (String) session.getAttribute("username");
            
            // Check if user is logged in
            if (memberId == null) {
                // User not logged in, redirect to login page
                response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_LOGIN + 
                                     "?error=login_required");
                return;
            }
            
            // Get parameters from request
            String subject = request.getParameter("subject");
            String message = request.getParameter("message");
            
            // Validate the input data
            if (!ValidationUtil.isValidSubject(subject)) {
                session.setAttribute("errorMessage", "Subject is required and must be less than 100 characters.");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
                return;
            }
            
            if (!ValidationUtil.isValidMessage(message)) {
                session.setAttribute("errorMessage", "Message is required and must be less than 2000 characters.");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
                return;
            }
            

            
            // Get current date
            String enquiryDate = LocalDate.now().toString();
            
            // Create enquiry object
            Enquiry enquiry = new Enquiry();
            enquiry.setMember_ID(memberId);
            enquiry.setSubject(subject);
            enquiry.setMessage(message);
            enquiry.setEnquiry_Date(enquiryDate);
            
            // Add enquiry to database
            int result = dbController.addNewEnquiry(enquiry);
            
            if(result == 1) {
                // Success - using consistent session attributes
                session.setAttribute("successMessage", "Your enquiry has been submitted successfully!");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
            } 
            else {
                // Failure - using consistent session attributes
                session.setAttribute("errorMessage", "Failed to submit enquiry. Please try again.");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
            }
            
        } catch (Exception e) {
            // Handle any exceptions
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Error processing enquiry: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
        }
    }
}