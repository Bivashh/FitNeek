package controller.servlets;
import java.io.IOException;
import java.io.File;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import util.StringUtils;
import util.ValidationUtil;
import controller.database.DbController;
import model.Member;

@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_REGISTER_MEMBER })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class MemberRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DbController dbController;
    
    public MemberRegistrationServlet() {
        this.dbController = new DbController();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String username = request.getParameter(StringUtils.MEMBER_USERNAME);
            String password = request.getParameter(StringUtils.MEMBER_PASSWORD);
            String confirmPassword = request.getParameter("confirmPassword"); // Assuming this field exists
            String firstName = request.getParameter(StringUtils.MEMBER_FIRST_NAME);
            String lastName = request.getParameter(StringUtils.MEMBER_LAST_NAME);
            String email = request.getParameter(StringUtils.MEMBER_EMAIL);
            String phoneNumber = request.getParameter(StringUtils.MEMBER_PHONE_NUM);
            String age = request.getParameter(StringUtils.MEMBER_AGE);
            Part imageUrlFromPart = request.getPart(StringUtils.MEMBER_IMAGE);
            
            String fileName = null;
            if (imageUrlFromPart != null && imageUrlFromPart.getSize() > 0) {
                fileName = getFileName(imageUrlFromPart);
            }
            
            // Validate all inputs
            boolean isValid = true;
            StringBuilder errorMessage = new StringBuilder();
            
            // Validate username
            if (!ValidationUtil.isNotEmpty(username)) {
                isValid = false;
                errorMessage.append("Username is required. ");
            } else if (!ValidationUtil.hasMinLength(username, 4)) {
                isValid = false;
                errorMessage.append("Username must be at least 4 characters long. ");
            } else if (!ValidationUtil.hasMaxLength(username, 20)) {
                isValid = false;
                errorMessage.append("Username must be less than 20 characters. ");
            } else if (!ValidationUtil.isAlphanumeric(username)) {
                isValid = false;
                errorMessage.append("Username must contain only letters, numbers, and spaces. ");
            } else if (ValidationUtil.isUsernameTaken(username)) {
                isValid = false;
                errorMessage.append("Username is already taken. ");
            }
            
            // Validate password
            if (!ValidationUtil.isNotEmpty(password)) {
                isValid = false;
                errorMessage.append("Password is required. ");
            } else if (!ValidationUtil.hasMinLength(password, 8)) {
                isValid = false;
                errorMessage.append("Password must be at least 8 characters long. ");
            } else if (!ValidationUtil.isValidPassword(password)) {
                isValid = false;
                errorMessage.append("Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character. ");
            } else if (!ValidationUtil.doPasswordsMatch(password, confirmPassword)) {
                isValid = false;
                errorMessage.append("Passwords do not match. ");
            }
            
            // Validate first name
            if (!ValidationUtil.isNotEmpty(firstName)) {
                isValid = false;
                errorMessage.append("First name is required. ");
            } else if (!ValidationUtil.isTextOnly(firstName)) {
                isValid = false;
                errorMessage.append("First name must contain only letters. ");
            }
            
            // Validate last name
            if (!ValidationUtil.isNotEmpty(lastName)) {
                isValid = false;
                errorMessage.append("Last name is required. ");
            } else if (!ValidationUtil.isTextOnly(lastName)) {
                isValid = false;
                errorMessage.append("Last name must contain only letters. ");
            }
            
            // Validate email
            if (!ValidationUtil.isNotEmpty(email)) {
                isValid = false;
                errorMessage.append("Email is required. ");
            } else if (!ValidationUtil.isEmail(email)) {
                isValid = false;
                errorMessage.append("Invalid email format. ");
            } else if (ValidationUtil.isEmailTaken(email)) {
                isValid = false;
                errorMessage.append("Email is already registered. ");
            }
            
            // Validate phone number
            if (!ValidationUtil.isNotEmpty(phoneNumber)) {
                isValid = false;
                errorMessage.append("Phone number is required. ");
            } else if (!ValidationUtil.isValidPhoneNumber(phoneNumber)) {
                isValid = false;
                errorMessage.append("Invalid phone number format (must be 10 digits). ");
            } else if (ValidationUtil.isPhoneNumberTaken(phoneNumber)) {
                isValid = false;
                errorMessage.append("Phone number is already registered. ");
            }
            
            // Validate age
            if (!ValidationUtil.isNotEmpty(age)) {
                isValid = false;
                errorMessage.append("Age is required. ");
            } else if (!ValidationUtil.isValidAge(age)) {
                isValid = false;
                errorMessage.append("Age must be a number between 18 and 100. ");
            }
            
            // Validate image file
            if (fileName != null && !ValidationUtil.isValidImageFile(fileName)) {
                isValid = false;
                errorMessage.append("Invalid image file format. Only JPG, JPEG, PNG, GIF, and BMP are allowed. ");
            }
            
            // If validation fails, redirect with error message
            if (!isValid) {
                request.setAttribute(StringUtils.MESSAGE_ERROR, errorMessage.toString());
                request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER_MEMBER).forward(request, response);
                return;
            }
            
            Member mem = new Member(username, password, firstName, lastName, email, phoneNumber, age, imageUrlFromPart);
            int registrationResult = dbController.registerMember(mem);
            
            if (registrationResult == 1) {
                String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_MEMBER;
                fileName = mem.getImageUrlFromPart();
                if (fileName != null && !fileName.isEmpty()) {
                    imageUrlFromPart.write(savePath + fileName);
                }
                request.setAttribute(StringUtils.MESSAGE_SUCCESS, StringUtils.MESSAGE_SUCCESS_REGISTER);
                request.getRequestDispatcher(StringUtils.PAGE_URL_LOGIN).forward(request, response);
            } else if (registrationResult == 0) {
                request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_REGISTER);
                request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER_MEMBER).forward(request, response);
            } else {
                request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
                request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER_MEMBER).forward(request, response);
            }
        } catch (Exception e) {
            // Handle any exceptions
            e.printStackTrace();
            request.setAttribute(StringUtils.MESSAGE_ERROR, "Error processing registration: " + e.getMessage());
            request.getRequestDispatcher(StringUtils.PAGE_URL_REGISTER_MEMBER).forward(request, response);
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