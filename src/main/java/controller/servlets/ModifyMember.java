package controller.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;

import util.StringUtils;
import util.ValidationUtil;
import controller.database.DbController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Member;


@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class ModifyMember extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DbController dbController = new DbController();
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String updateId = request.getParameter("update_member_id");
        String deleteId = request.getParameter("delete_member_id");

        // if update button clicked
        if (updateId != null && !updateId.isEmpty()) {
            doPut(request, response);
        }
        
        // if delete button clicked
        if (deleteId != null && !deleteId.isEmpty()) {
            doDelete(request, response);
        }
    }
    
    /**
     * Handles the update operation for members
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String member_username = (String) session.getAttribute("username");
        
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phone");
        String age = request.getParameter("age");
        
        // Handle image upload if any
        Part imagePart = request.getPart("image");
        String fileName = null;
        if (imagePart != null && imagePart.getSize() > 0) {
            fileName = getFileName(imagePart);
        }
        
        // Validate inputs
        boolean isValid = true;
        StringBuilder errorMessage = new StringBuilder();
        
        // Validate first name
        if (!ValidationUtil.isNotEmpty(firstName)) {
            isValid = false;
            errorMessage.append("First name is required. ");
        } else if (!ValidationUtil.isTextOnly(firstName)) {
            isValid = false;
            errorMessage.append("First name must contain only letters and spaces. ");
        }
        
        // Validate last name
        if (!ValidationUtil.isNotEmpty(lastName)) {
            isValid = false;
            errorMessage.append("Last name is required. ");
        } else if (!ValidationUtil.isTextOnly(lastName)) {
            isValid = false;
            errorMessage.append("Last name must contain only letters and spaces. ");
        }
        
        // Validate email
        if (!ValidationUtil.isNotEmpty(email)) {
            isValid = false;
            errorMessage.append("Email is required. ");
        } else if (!ValidationUtil.isEmail(email)) {
            isValid = false;
            errorMessage.append("Invalid email format. ");
        } else {
            // Check if email is taken by another member
            Member existingMember = dbController.getMemberInfoFromId(member_username);
            if (!email.equals(existingMember.getMember_Email()) && ValidationUtil.isEmailTaken(email)) {
                isValid = false;
                errorMessage.append("Email is already in use by another member. ");
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
            // Check if phone number is taken by another member
            Member existingMember = dbController.getMemberInfoFromId(member_username);
            if (!phoneNumber.equals(existingMember.getMember_PhoneNumber()) && ValidationUtil.isPhoneNumberTaken(phoneNumber)) {
                isValid = false;
                errorMessage.append("Phone number is already in use by another member. ");
            }
        }
        
        // Validate age
        if (!ValidationUtil.isNotEmpty(age)) {
            isValid = false;
            errorMessage.append("Age is required. ");
        } else if (!ValidationUtil.isValidAge(age)) {
            isValid = false;
            errorMessage.append("Invalid age. Age must be between 18 and 100. ");
        }
        
        // Validate image file if one was uploaded
        if (fileName != null && !ValidationUtil.isValidImageFile(fileName)) {
            isValid = false;
            errorMessage.append("Invalid image file format. Only JPG, JPEG, PNG, GIF, and BMP are allowed. ");
        }
        
        // If validation fails, redirect with error message
        if (!isValid) {
            session.setAttribute("errorMessage", errorMessage.toString());
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
            return;
        }
        
        Member member = new Member();
        if (imagePart != null && imagePart.getSize() > 0) {
            String imageUrl = processImageUpload(imagePart);
            member.setImageUrlFromPart(imageUrl);
        } else {
            // Keep the existing image URL
            Member existingMember = dbController.getMemberInfoFromId(member_username);
            member.setImageUrlFromPart(existingMember.getImageUrlFromPart());
        }
        
        member.setMember_Username(member_username);
        member.setMember_FirstName(firstName);
        member.setMember_LastName(lastName);
        member.setMember_Email(email);
        member.setMember_PhoneNumber(phoneNumber);
        member.setMember_Age(age);
        
        int result = dbController.updateMember(member);
        
        if (result > 0) {
            // Use consistent approach with session attributes
            session.setAttribute("successMessage", "Profile updated successfully!");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
        } else {
            // Use consistent approach with session attributes
            session.setAttribute("errorMessage", "Failed to update profile. Please try again.");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String memberUsername = request.getParameter("delete_member_id");
        HttpSession session = request.getSession();
        
        // Deleting the selected member from database
        int result = dbController.deleteMember(memberUsername);
        
        if (result == 1) {
            // Use consistent approach with session attributes
            session.setAttribute("successMessage", "Account deleted successfully!");
            session.invalidate(); // Log out the user after account deletion
            response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_LOGIN);
        } else {
            // Use consistent approach with session attributes
            session.setAttribute("errorMessage", "Failed to delete account. Please try again.");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
        }
    }
    
    /**
     * Helper method to process image uploads
     */
    private String processImageUpload(Part part) throws IOException {
        String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_MEMBER;
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