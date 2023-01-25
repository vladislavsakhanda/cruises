package com.my;

import db.dao.mysql.MySqlTripDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;

@WebServlet("/requestsCatalog")
public class RequestsCatalog extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("requestsCatalog#doGet");



        req.getRequestDispatcher("/WEB-INF/pages/admin/requestsCatalog.jsp").forward(req, resp);
    }
}
