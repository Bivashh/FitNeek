package controller.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import util.StringUtils;
import controller.database.DbController;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.Plan;


@MultipartConfig(
    fileSizeThreshold = 1024 * 1024,      // 1 MB
    maxFileSize = 1024 * 1024 * 10,       // 10 MB
    maxRequestSize = 1024 * 1024 * 50     // 50 MB
)
public class ModifyPlanServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    DbController dbController = new DbController();
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch plan info to be updated
        Plan plan = dbController.getPlanInfoFromId(request.getParameter("update_plan_id"));
        request.setAttribute("updatePlan", plan);
        request.getRequestDispatcher(StringUtils.PAGE_URL_UPDATE_PLAN).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String updateId = request.getParameter("update_plan_id");
        String deleteId = request.getParameter("delete_plan_id");

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
     * Handles the update operation for plans
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String adminID = (String) session.getAttribute("username");
        
        String planId = request.getParameter("update_plan_id");
        String planName = request.getParameter("plan_name");
        String planDescription = request.getParameter("plan_description");
        String planDuration = request.getParameter("plan_duration");
        String planPrice = request.getParameter("plan_price");
        
        // Handle image upload if any
        Part imagePart = request.getPart("plan_image");
        
        Plan plan = new Plan();
        plan.setPlan_Name(planName);
        
        // Only process image if a new one was uploaded
        if (imagePart != null && imagePart.getSize() > 0) {
            String imageUrl = processImageUpload(imagePart);
            plan.setImageUrlFromPart(imageUrl);
        } else {
            // Keep the existing image URL
            Plan existingPlan = dbController.getPlanInfoFromId(planId);
            plan.setImageUrlFromPart(existingPlan.getImageUrlFromPart());
        }
        plan.setPlan_Duration(planDuration);
        plan.setPlan_Description(planDescription);
        plan.setPlan_Price(planPrice);
        plan.setAdmin_ID(adminID);
        plan.setPlan_ID(planId);
        
        int result = dbController.updatePlan(plan);
        
        if (result > 0) {
            // Use consistent approach with session attributes
            session.setAttribute("successMessage", "Plan updated successfully!");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        } else {
            // Use consistent approach with session attributes
            session.setAttribute("errorMessage", "Failed to update plan. Please try again.");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String planId = request.getParameter("delete_plan_id");
        HttpSession session = request.getSession();
        
        // Deleting the selected plan from database
        if (dbController.deletePlan(planId) == 1) {
            // Use consistent approach with session attributes
            session.setAttribute("successMessage", "Plan deleted successfully!");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        
        // If deletion failed
        } else {
            // Use consistent approach with session attributes
            session.setAttribute("errorMessage", "Failed to delete plan. Please try again.");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);
        }
    }
    
    /**
     * Helper method to process image uploads
     */
    private String processImageUpload(Part part) throws IOException {
        String savePath = StringUtils.IMAGE_DIR_SAVE_PATH_PLAN;
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