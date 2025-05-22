package controller.servlets;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Admin;
import model.Enquiry;
import model.Member;
import model.Membership;
import model.Plan;
import util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import controller.database.DbController;


public class AdminDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDashboardServlet() {
        super();
    }
    
    private DbController controller = new DbController();
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get current session
        HttpSession userSession = request.getSession(false);
        
        // Check if session exists and user is authenticated as admin
        if (userSession == null || userSession.getAttribute("username") == null || 
            userSession.getAttribute("type") == null || 
            !"admin".equals(userSession.getAttribute("type"))) {
            
            // Not authenticated as admin, redirect to login
            response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_LOGIN);
            return;
        }
        
        // If we get here, the user is authenticated as admin
        String currentUser = (String) userSession.getAttribute("username");
        
        // Check for messages in session and transfer to request
        if (userSession.getAttribute("successMessage") != null) {
            request.setAttribute("successMessage", userSession.getAttribute("successMessage"));
            userSession.removeAttribute("successMessage"); // Clear after using
        }
        
        if (userSession.getAttribute("errorMessage") != null) {
            request.setAttribute("errorMessage", userSession.getAttribute("errorMessage"));
            userSession.removeAttribute("errorMessage"); // Clear after using
        }
        
        // Fetch all required data
        ArrayList<Member> mem = controller.getAllMemberInfo();
        ArrayList<Membership> membership = controller.getAllMembershipInfo();
        ArrayList<Plan> plan = controller.getAllPlanInfo();
        ArrayList<Enquiry> enq = controller.getAllEnqInfo();
        
        // Set attributes for JSP
        request.setAttribute(StringUtils.MEMBERS_LIST, mem);
        request.setAttribute(StringUtils.MEMBERSHIPS_LIST, membership);
        request.setAttribute(StringUtils.PLANS_LIST, plan);
        request.setAttribute(StringUtils.ENQUIRIES_LIST, enq);
        
        // Get admin info
        Admin admin = controller.getAdminInfoFromId(currentUser);
        request.setAttribute("updateProfile", admin);
        request.setAttribute("isAdmin", true);
        
        // Forward to JSP
        request.getRequestDispatcher(StringUtils.PAGE_URL_ADMIN_DASHBOARD).forward(request, response);
    }
}