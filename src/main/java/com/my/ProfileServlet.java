package com.my;

import org.apache.struts2.ServletActionContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("userEmail") != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/successProfile.jsp").forward(req, resp);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/pages/registration/login.jsp").forward(req, resp);
        }
    }
}
