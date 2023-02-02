package controller.commands;

import controller.FrontCommand;
import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Objects;

public class BookTourCommand extends FrontCommand {
    @Override
    public void process() throws ServletException, IOException {
        if (request.getAttribute("method") == "GET") {
            doGet();
        } else if (request.getAttribute("method") == "POST") {
            doPost();
        }
    }

    private void doGet() throws ServletException, IOException {
        forward("cruisesCatalog/bookTour");
    }

    private void doPost() throws ServletException, IOException {
        if (Objects.equals(request.getParameter("action"), "book")) {
            setServletContext();
            doGet();
        } else {
            long user_id = (long) request.getSession().getAttribute("userId");
            long liner_id = (long) context.getAttribute("liner_id");
            boolean is_paid = false;
            double price = (double) context.getAttribute("price");
            Date date_start = (Date) context.getAttribute("date_start");
            Date date_end = (Date) context.getAttribute("date_end");
            int status = 0;

            InputStream inputStream = null;
            String message = null;
            Part filePart = request.getPart("passport");
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
            request.setAttribute("Message", message);

            sendRedirect("/cruises?command=SuccessBookTour");
        }
    }

    private void setServletContext() {
        context.setAttribute("action", request.getParameter("action"));
        context.setAttribute("liner_id", Long.parseLong(request.getParameter("liner_id")));
        context.setAttribute("price", Double.parseDouble(request.getParameter("price")));
        context.setAttribute("date_start", Date.valueOf(request.getParameter("date_start")));
        context.setAttribute("date_end", Date.valueOf(request.getParameter("date_end")));
    }
}
