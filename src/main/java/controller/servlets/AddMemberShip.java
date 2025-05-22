package controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Membership;
import util.StringUtils;

import java.io.IOException;
import java.time.LocalDate;

import controller.database.DbController;

/**
 * Servlet implementation class AddMemberShip
 */
public class AddMemberShip extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final DbController dbController;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMemberShip() {
    	this.dbController = new DbController();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            String memberId = (String) session.getAttribute("username");
            
            if (memberId == null) {
                response.sendRedirect(request.getContextPath() + StringUtils.PAGE_URL_LOGIN);
                return;
            }
            
            String planId = request.getParameter("planId");
            if (planId == null || planId.isEmpty()) {
                // Set error using session
                session.setAttribute("errorMessage", "No plan selected");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
                return;
            }
            
            // Create and add membership
            Membership membership = new Membership();
            membership.setMember_ID(memberId);
            membership.setPlan_ID(planId);
            membership.setStart_Date(LocalDate.now().toString());
            
            int result = dbController.addNewMembership(membership);
            
            if(result == 1) {
                // Success - using consistent session attributes
                session.setAttribute("successMessage", "Membership added successfully!");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
            } else {
                // Failure - using consistent session attributes
                session.setAttribute("errorMessage", "Failed to add membership. Please try again.");
                response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
            }
        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("errorMessage", "A server error occurred. Please try again later.");
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);
        }
    }
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		doGet(request, response);
	}

}
