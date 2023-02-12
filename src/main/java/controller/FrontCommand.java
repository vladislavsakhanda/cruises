package controller;

import exeptions.DBException;
import exeptions.IllegalFieldException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

public abstract class FrontCommand {
    protected ServletContext context;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    public void init(
            ServletContext servletContext,
            HttpServletRequest servletRequest,
            HttpServletResponse servletResponse) {
        this.context = servletContext;
        this.request = servletRequest;
        this.response = servletResponse;
    }

    public abstract void process() throws ServletException, DBException, IOException, IllegalFieldException, SQLException, NoSuchAlgorithmException, InvalidKeySpecException;

    protected void forward(String target) throws ServletException, IOException {
        target = String.format("/WEB-INF/pages/%s.jsp", target);
        RequestDispatcher dispatcher = context.getRequestDispatcher(target);
        dispatcher.forward(request, response);
    }

    protected void sendRedirect(String target) throws ServletException, IOException {
        response.sendRedirect(target);
    }
}