/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.muzoo.ssc.webapp.servlet;

import io.muzoo.ssc.webapp.Routable;
import io.muzoo.ssc.webapp.model.User;
import io.muzoo.ssc.webapp.service.SecurityService;
import io.muzoo.ssc.webapp.service.UserService;
import org.apache.commons.lang.StringUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author gigadot
 */
public class DeleteUserServlet extends HttpServlet implements Routable {

    private SecurityService securityService;

    @Override
    public String getMapping() {
        return "/user/delete";
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
            String username = (String) request.getSession().getAttribute("username");
            UserService userService = UserService.getInstance();
            request.getSession().setAttribute("flashSessionRead", false);
            try {
                User currentUser = userService.findByUsername(username);

                User deletingUser = userService.findByUsername(request.getParameter("username"));

                if (StringUtils.equals(currentUser.getUsername(), deletingUser.getUsername())) {
                    // go to user list page

                    request.getSession().setAttribute("hasError", true);
                    request.getSession().setAttribute("message", "You cannot delete own account");
                } else {
                    if (userService.deleteUserByUsername(deletingUser.getUsername())) {
                        // go to user list page
                        request.getSession().setAttribute("hasError", false);
                        request.getSession().setAttribute("message", "User is deleted");
                    } else {
                        //err
                        request.getSession().setAttribute("hasError", true);
                        request.getSession().setAttribute("message", "Unable to delete User");
                    }
                }

            } catch (Exception e) {
                //err
                request.getSession().setAttribute("hasError", true);
                request.getSession().setAttribute("message", "Unable to delete User");
            }


            response.sendRedirect("/");
            //request.getSession().removeAttribute("hasError");
            //request.getSession().removeAttribute("message");
        } else {
            response.sendRedirect("/login");
        }
    }
}
