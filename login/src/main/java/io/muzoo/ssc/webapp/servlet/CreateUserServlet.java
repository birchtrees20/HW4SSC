package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateUserServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/create";
    }

    @Override
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);

        if (authorized) {
            // do MVC in here
            //String username = (String) request.getSession().getAttribute("username");
            //UserService userService = UserService.getInstance();



            //request.setAttribute("user", userService.findByUsername(username));

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/create.jsp");
            rd.include(request, response);

            request.getSession().removeAttribute("hasError");
            request.getSession().removeAttribute("message");

        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean authorized = securityService.isAuthorized(request);

        if (authorized) {
            // do MVC in here

            String username = StringUtils.trim((String) request.getParameter("username"));
            String displayName = StringUtils.trim((String) request.getParameter("displayName"));
            String password = (String) request.getParameter("password");
            String cpassword = (String) request.getParameter("cpassword");

            UserService userService = UserService.getInstance();
            String errorMessage = null;
            if (userService.findByUsername(username) != null) {
                errorMessage = "Username already exists";
            }
            else if (StringUtils.isBlank(displayName)) {
                errorMessage = "Display Name cannot be blank";
            }
            else if (!StringUtils.equals(password, cpassword)) {
                errorMessage = "Password mismatch";
            }

            if (errorMessage != null) {
                request.getSession().setAttribute("hasError", true);
                request.getSession().setAttribute("message", errorMessage);
            } else {
                try {
                    userService.createUser(username, password, displayName);
                    request.getSession().setAttribute("hasError", false);
                    request.getSession().setAttribute("message", "User created");
                    response.sendRedirect("/");
                    request.getSession().removeAttribute("hasError");
                    request.getSession().removeAttribute("message");
                    return;
                } catch (Exception e) {
                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", e.getMessage());
                }
            }
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/create.jsp");
            rd.include(request, response);

            request.getSession().removeAttribute("hasError");
            request.getSession().removeAttribute("message");

        } else {
            request.removeAttribute("hasError");
            request.removeAttribute("message");
            response.sendRedirect("/login");
        }    }
}