package controller.servlets;
import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import util.StringUtils;
import util.ValidationUtil;
import controller.database.DbController;
import model.Plan;
/**
 * Servlet implementation class AddPlanServlet
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class AddPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DbController dbController;
    
    public AddPlanServlet() {
        this.dbController = new DbController();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            // Get current session
            HttpSession session = request.getSession();
            
            // Get parameters from request
            String planName = request.getParameter("planName");
            String planDescription = request.getParameter("planDescription");
            String planDuration = request.getParameter("planDuration");
            String planPrice = request.getParameter("planPrice");
            
            // Get admin ID from session or request parameter
            String adminId = (String) session.getAttribute("username");
            
            // If admin ID is not in session, try to get it from request parameter
            if (adminId == null || adminId.isEmpty()) {
                adminId = request.getParameter("adminId");
            }
            
            // Get uploaded image file
            Part imagePart = request.getPart("planImage");
            String fileName = null;
            if (imagePart != null && imagePart.getSize() > 0) {
                fileName = getFileName(imagePart);
            }
            
            // Validate inputs
            boolean isValid = true;
            StringBuilder errorMessage = new StringBuilder();
            
            // Validate plan name
            if (!ValidationUtil.isNotEmpty(planName)) {
                isValid = false;
                errorMessage.append("Plan name is required. ");
            } else if (!ValidationUtil.hasMaxLength(planName, 100)) {
                isValid = false;
                errorMessage.append("Plan name must be less than 100 characters. ");
            } else if (!ValidationUtil.isAlphanumeric(planName)) {
                isValid = false;
                errorMessage.append("Plan name must contain only letters, numbers, and spaces. ");
            }
            
            // Validate plan description
            if (!ValidationUtil.isNotEmpty(planDescription)) {
                isValid = false;
                errorMessage.append("Plan description is required. ");
            } else if (!ValidationUtil.hasMaxLength(planDescription, 500)) {
                isValid = false;
                errorMessage.append("Plan description must be less than 500 characters. ");
                
            }

            
            if (!ValidationUtil.isValidPlanName(planName)) {
                isValid = false;
                errorMessage.append("Plan Name cannot contain numbers in them ");
            } 
            
            
            // Validate plan duration
            if (!ValidationUtil.isNotEmpty(planDuration)) {
                isValid = false;
                errorMessage.append("Plan duration is required. ");
            } 
            
            // Validate plan price
            if (!ValidationUtil.isNotEmpty(planPrice)) {
                isValid = false;
                errorMessage.append("Plan price is required. ");
            } else if (!ValidationUtil.isValidPrice(planPrice)) {
                isValid = false;
                errorMessage.append("Plan price must be a positive number. ");
            }
            
            // Validate image file
            if (fileName != null && !ValidationUtil.isValidImageFile(fileName)) {
                isValid = false;
                errorMessage.append("Invalid image file format. Only JPG, JPEG, PNG, GIF, and BMP are allowed. ");
            }
            
            // If validation fails, redirect with error message
            if (!isValid) {
                session.setAttribute("errorMessage", errorMessage.toString());
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
                return;
            }
            
            // Create plan model
            Plan plan = new Plan(planName, planDescription, planDuration, planPrice, adminId, imagePart);
            
            // Save plan to database
            int result = dbController.addNewPlan(plan);
            
            if (result == 1) {
                // Handle image saving
                String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_PLAN;
                fileName = plan.getImageUrlFromPart();
                if (fileName != null && !fileName.isEmpty()) {
                    imagePart.write(savePath + fileName);
                }
                    
                // Success - using consistent session attributes
                session.setAttribute("successMessage", "Plan added successfully!");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
            } else {
                // Failure - using consistent session attributes
                session.setAttribute("errorMessage", "Failed to add plan. Please try again.");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
            }
            
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        }
    }
    
    /**
     * Helper method to extract filename from Part
     */
    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }
}