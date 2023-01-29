package servlets;

import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;
import db.dao.mysql.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/cruisesCatalog/bookTour")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10,  // 10 MB
        maxFileSize = 1024 * 1024 * 50,       // 50 MB
        maxRequestSize = 1024 * 1024 * 100)    // 100 MB
public class BookTourServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private void setServletContext (HttpServletRequest req, HttpServletResponse resp) {
        getServletContext().setAttribute("liner_id", Long.parseLong(req.getParameter("liner_id")));
        getServletContext().setAttribute("price", Double.parseDouble(req.getParameter("price")));
        getServletContext().setAttribute("date_start", Date.valueOf(req.getParameter("date_start")));
        getServletContext().setAttribute("date_end", Date.valueOf(req.getParameter("date_end")));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (getServletContext().getAttribute("liner_id") == null) {
            setServletContext(req, resp);
        }

        req.getRequestDispatcher("/WEB-INF/pages/cruisesCatalog/bookTour.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long user_id = (long) req.getSession().getAttribute("userId");
        long liner_id = Long.parseLong(req.getParameter("liner_id"));
        boolean is_paid = false;
        double price = Double.parseDouble(req.getParameter("price"));
        Date date_start = Date.valueOf(req.getParameter("date_start"));
        Date date_end = Date.valueOf(req.getParameter("date_end"));
        int status = 0;

        InputStream inputStream = null;
        String message = null;
        Part filePart = req.getPart("passport");
        System.out.println("\n\n " + filePart.getSize());
        if (filePart != null) {
            inputStream = filePart.getInputStream();
        }

        try {
            new MySqlTripDAO().create(Trip.createTrip(user_id, liner_id, is_paid,
                    price, date_start, date_end, inputStream, status));
            message = "Заявка успішно прийнята! Ви можете оплатити її у своєму профілі, після того як адміністратор перевірить дані.";
        } catch (Exception e) {
            message = "Заявка не була прийнята.";
            e.printStackTrace();
        }
        req.setAttribute("Message", message);

        getServletContext().getRequestDispatcher("/WEB-INF/pages/cruisesCatalog/successBookTour.jsp")
                .forward(req, resp);
    }
}
