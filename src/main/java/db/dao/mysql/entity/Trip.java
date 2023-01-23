package db.dao.mysql.entity;

import java.io.InputStream;
import java.sql.Date;

public class Trip extends Entity {
    private long user_id;
    private long liner_id;
    private boolean is_paid;
    private double price;
    private Date date_start;
    private Date date_end;
    private InputStream passport;
    private int status;

    public static Trip createTrip(long user_id, long liner_id, boolean is_paid, double price, Date date_start, Date date_end, InputStream passport, int status) {
        Trip trip = new Trip();
        trip.setUser_id(user_id);
        trip.setLiner_id(liner_id);
        trip.setPrice(price);
        trip.setDate_start(date_start);
        trip.setDate_end(date_end);
        trip.setPassport(passport);
        trip.setStatus(status);
        return trip;
    }

    public Trip() {
        super();
    }

    public Trip(long id) {
        super(id);
    }

    public Trip(long user_id, long liner_id, boolean is_paid, double price, Date date_start, Date date_end, InputStream passport, int status) {
        this.user_id = user_id;
        this.liner_id = liner_id;
        this.is_paid = is_paid;
        this.price = price;
        this.date_start = date_start;
        this.date_end = date_end;
        this.passport = passport;
        this.status = status;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setLiner_id(long liner_id) {
        this.liner_id = liner_id;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getLiner_id() {
        return liner_id;
    }

    public double getPrice() {
        return price;
    }

    public Date getDate_start() {
        return date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public InputStream getPassport() {
        return passport;
    }

    public void setPassport(InputStream passport) {
        this.passport = passport;
    }

    public boolean getIs_paid() {
        return is_paid;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
    }

    public boolean isIs_paid() {
        return is_paid;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
