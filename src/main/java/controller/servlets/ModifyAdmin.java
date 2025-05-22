package controller.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import util.StringUtils;
import util.ValidationUtil;
import controller.database.DbController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Admin;


@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class ModifyAdmin extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DbController dbController = new DbController();
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String updateId = request.getParameter("update_admin_id");
        String deleteId = request.getParameter("delete_admin_id");

        // If update button clicked
        if (updateId != null && !updateId.isEmpty()) {
            doPut(request, response);
        }
        
        // If delete button clicked
        if (deleteId != null && !deleteId.isEmpty()) {
            doDelete(request, response);
        }
    }
    
    /**
     * Handles the update operation for admin profiles
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String adminUsername = (String) session.getAttribute("username");
        
        
        String fullName = request.getParameter("adminFullName");
        String email = request.getParameter("adminEmail");
        String phoneNumber = request.getParameter("adminPhoneNumber");
        
        // Handle image upload if any
        Part imagePart = request.getPart("image");
        String fileName = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            fileName = getFileName(imagePart);
        }
        
        // Validate inputs
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        // Validate full name
        if (!ValidationUtil.isNotEmpty(fullName)) {
            isValid = false;
            errorMessage.append("Full name is required. ");
        } else if (!ValidationUtil.isTextOnly(fullName)) {
            isValid = false;
            errorMessage.append("Full name must contain only letters and spaces. ");
        }
        
        // Validate email
        if (!ValidationUtil.isNotEmpty(email)) {
            isValid = false;
            errorMessage.append("Email is required. ");
        } else if (!ValidationUtil.isEmail(email)) {
            isValid = false;
            errorMessage.append("Invalid email format. ");
        } else {
            // Check if email is taken by another admin
            Admin existingAdmin = dbController.getAdminInfoFromId(adminUsername);
            if (!email.equals(existingAdmin.getAdmin_Email()) && ValidationUtil.isEmailTakenCoach(email)) {
                isValid = false;
                errorMessage.append("Email is already in use by another admin. ");
            }
        }
        
        
        
        
        // Validate phone number
        if (!ValidationUtil.isNotEmpty(phoneNumber)) {
            isValid = false;
            errorMessage.append("Phone number is required. ");
        } else if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
            isValid = false;
            errorMessage.append("Invalid phone number format (must be 10 digits). ");
        } else {
            // Check if phone number is taken by another admin
            Admin existingAdmin = dbController.getAdminInfoFromId(adminUsername);
            if (!phoneNumber.equals(existingAdmin.getAdmin_PhoneNumber()) && ValidationUtil.isPhoneNumberTakenCoach(phoneNumber)) {
                isValid = false;
                errorMessage.append("Phone number is already in use by another admin. ");
            }
        }
        
        // Validate image file if one was uploaded
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
        
        Admin admin = new Admin();
        if (imagePart != null && imagePart.getSize() > 0) {
            String imageUrl = processImageUpload(imagePart);
            admin.setImageUrlFromPart(imageUrl);
        } else {
            // Keep the existing image URL
            Admin existingAdmin = dbController.getAdminInfoFromId(adminUsername);
            admin.setImageUrlFromPart(existingAdmin.getImageUrlFromPart());
        }
        
        admin.setAdmin_Username(adminUsername);
        admin.setAdmin_FullName(fullName);
        admin.setAdmin_Email(email);
        admin.setAdmin_PhoneNumber(phoneNumber);
        
        int result = dbController.updateAdmin(admin);
        
        if (result > 0) {
            // Use consistent approach with session attributes
            session.setAttribute("successMessage", "Profile updated successfully!");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        } else {
            // Use consistent approach with session attributes
            session.setAttribute("errorMessage", "Failed to update profile. Please try again.");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminUsername = request.getParameter("delete_admin_id");
        HttpSession session = request.getSession();
        

        
        // Deleting the selected admin from database
        int result = dbController.deleteAdmin(adminUsername);
        
        if (result == 1) {
            // Use consistent approach with session attributes
            session.setAttribute("successMessage", "Account deleted successfully!");
            session.invalidate(); // Log out the user after account deletion
            response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_LOGIN);
        } else {
            // Use consistent approach with session attributes
            session.setAttribute("errorMessage", "Failed to delete account. Please try again.");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        }
    }
    
    /**
     * Helper method to process image uploads
     */
    private String processImageUpload(Part part) throws IOException {
        String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_ADMIN;
        String fileName = getFileName(part);
        
        if (fileName == null || fileName.isEmpty()) {
            return "default.png";
        }
        
        // Create directory if it doesn't exist
        Path dirPath = Paths.get(savePath);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        
        // Save the file
        try (InputStream input = part.getInputStream()) {
            Path filePath = dirPath.resolve(fileName);
            Files.copy(input, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        
        return fileName;
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