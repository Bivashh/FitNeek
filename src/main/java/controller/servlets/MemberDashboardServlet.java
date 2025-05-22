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

/**
 * Servlet implementation class MemberDashboardServlet
 */

public class MemberDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DbController controller = new DbController();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {
	    HttpSession userSession = request.getSession(false);
	    
	    // Authentication check
	    if (userSession == null || userSession.getAttribute("username") == null || 
	        !"member".equals(userSession.getAttribute("type"))) {
	        response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_LOGIN);
	        return;
	    }
	    
	    // Check for messages in session and transfer to request
	    if (userSession.getAttribute("successMessage") != null) {
	        request.setAttribute("successMessage", userSession.getAttribute("successMessage"));
	        userSession.removeAttribute("successMessage"); // Clear after using
	    }
	    
	    if (userSession.getAttribute("errorMessage") != null) {
	        request.setAttribute("errorMessage", userSession.getAttribute("errorMessage"));
	        userSession.removeAttribute("errorMessage"); // Clear after using
	    }
	    
	    // Get data
	    String currentUser = (String) userSession.getAttribute("username");
	    ArrayList<Plan> plans = controller.getAllPlanInfo();
	    Member member = controller.getMemberInfoFromId(currentUser);
	    
	    // Set attributes
	    request.setAttribute("plansList", plans);
	    request.setAttribute("updateProfile", member);
	    
	    // Forward to JSP
	    request.getRequestDispatcher(StringUtils.PAGE_URL_MEMBER_DASHBOARD).forward(request, response);
	}
}