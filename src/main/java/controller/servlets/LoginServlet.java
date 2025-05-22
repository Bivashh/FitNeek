package controller.servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import controller.database.DbController;
import model.UserLoginModel;
import util.StringUtils;



/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(asyncSupported = true, urlPatterns = { StringUtils.SERVLET_URL_LOGIN })
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final DbController dbController;

    public LoginServlet() {
        this.dbController = new DbController();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter(StringUtils.USERNAME);
        String password = request.getParameter(StringUtils.PASSWORD);
        String userType = request.getParameter(StringUtils.USER_TYPE); // "member" or "admin"

        UserLoginModel loginModel = new UserLoginModel(username, password);
        int loginResult = dbController.login(loginModel); // Assume: 1 = member, 2 = admin, 0 = wrong credentials

        if ("member".equals(userType) && loginResult == 1) {
            // Member Login Success
            createSessionAndCookie(request, response, "member", username);
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_MEMBER_DASHBOARD);

        } else if ("admin".equals(userType) && loginResult == 2) {
            // Admin Login Success
            createSessionAndCookie(request, response, "admin", username);
            response.sendRedirect(request.getContextPath() + StringUtils.SERVLET_URL_ADMIN_DASHBOARD);

        } else if (loginResult == 0) {
            // Wrong username or password
            request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_LOGIN);
            request.getRequestDispatcher(StringUtils.PAGE_URL_LOGIN).forward(request, response);
        } else {
            // Unknown error
            request.setAttribute(StringUtils.MESSAGE_ERROR, StringUtils.MESSAGE_ERROR_SERVER);
            request.getRequestDispatcher(StringUtils.PAGE_URL_LOGIN).forward(request, response);
        }
    }

    private void createSessionAndCookie(HttpServletRequest request, HttpServletResponse response, String type, String username) {
        HttpSession session = request.getSession();
        session.setAttribute("type", type);
        session.setAttribute("username", username);
        session.setMaxInactiveInterval(30 * 60); // 30 minutes

        Cookie cookie = new Cookie(type, username);
        cookie.setMaxAge(30 * 60);
        response.addCookie(cookie);

        request.setAttribute(StringUtils.MESSAGE_SUCCESS, StringUtils.MESSAGE_SUCCESS_LOGIN);
    }
}
