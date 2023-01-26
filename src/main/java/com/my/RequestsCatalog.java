package com.my;

import db.dao.mysql.MySqlTripDAO;
import db.dao.mysql.entity.Trip;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;

@WebServlet("/requestsCatalog")
public class RequestsCatalog extends HttpServlet {

    public RequestsCatalog() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("requestsCatalog#doGet");
        Trip trip = null;
        try {
            trip = new MySqlTripDAO().readByUserIdAndLinerId(86, 6);

            if (trip == null) {
                resp.sendRedirect(req.getContextPath() + "/requestsCatalog/noimage.jpg");
                return;
            }

//            File image = new File(getServletContext().getRealPath("/") + "\\images\\1.jpg");
//            FileOutputStream fos = new FileOutputStream(image);
//            byte[] buffer = new byte[1];
//            InputStream in = trip.getPassport();
//            while (in.read(buffer) > 0) {
//                fos.write(buffer);
//            }
//            fos.close();
//
//            req.getRequestDispatcher("/WEB-INF/pages/admin/requestsCatalog.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        File image = new File(getServletContext().getRealPath("/") + "\\images\\1.jpg");
        FileOutputStream fos = new FileOutputStream(image);
        byte[] buffer = new byte[1];
        InputStream in = trip.getPassport();
        while (in.read(buffer) > 0) {
            fos.write(buffer);
        }
        fos.close();

        req.getRequestDispatcher("/WEB-INF/pages/admin/requestsCatalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
