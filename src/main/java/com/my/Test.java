package com.my;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import db.dao.DAOFactory;
import db.dao.mysql.MySqlDAOFactory;
import db.dao.mysql.MySqlLinerDAO;
import db.dao.mysql.MySqlScheduleDAO;
import db.dao.mysql.MySqlUserDAO;
import db.dao.mysql.entity.Liner;
import db.dao.mysql.entity.Schedule;
import db.dao.mysql.entity.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/test")
public class Test extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAOFactory.connectPooledConnectionDataSource();
        MySqlDAOFactory daoFactory = new MySqlDAOFactory();

        MySqlLinerDAO mySqlLinerDAO = daoFactory.getLinerDAO();
        List<Liner> liners = mySqlLinerDAO.getAll();
        req.setAttribute("liners", liners);

        MySqlScheduleDAO mySqlScheduleDAO = daoFactory.getScheduleDAO();
        List<Schedule> schedules = mySqlScheduleDAO.getAll();
        req.setAttribute("schedules", schedules);

        getServletContext().getRequestDispatcher("/test.jsp").forward(req, resp);
    }
}
