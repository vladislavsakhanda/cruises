package com.my;

import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.entity.Liner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/createSchedule")
public class CreateSchedule extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MySqlLinerDAO mySqlLinerDAO = new MySqlDAOFactory().getLinerDAO();
            List<Liner> liners = mySqlLinerDAO.getAll();
            req.setAttribute("liners", liners);
        } catch (Exception e) {
            e.printStackTrace();
        }

        getServletContext().getRequestDispatcher("/createSchedule.jsp").forward(req, resp);
    }
}
