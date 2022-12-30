package entity;

import java.util.Date;

public class Trip {
    private long id;
    private long user_id;
    private long liner_id;
    private String from;
    private String to;
    private boolean is_paid;
    private double price;
    private Date date_start;
    private Date date_end;

    public static Trip createTrip (long user_id, long liner_id, String from, String to, boolean is_paid, double price, Date date_start, Date date_end) {
        Trip trip = new Trip();
        trip.setUser_id(user_id);
        trip.setLiner_id(liner_id);
        trip.setFrom(from);
        trip.setTo(to);
        trip.setPrice(price);
        trip.setDate_start(date_start);
        trip.setDate_end(date_end);
        return trip;
    }

    public Trip() {

    }

    public Trip(long user_id, long liner_id, String from, String to, boolean is_paid, double price, Date date_start, Date date_end) {
        this.user_id = user_id;
        this.liner_id = liner_id;
        this.from = from;
        this.to = to;
        this.is_paid = is_paid;
        this.price = price;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public Trip(long id, long user_id, long liner_id, String from, String to, boolean is_paid, double price, Date date_start, Date date_end) {
        this.id = id;
        this.user_id = user_id;
        this.liner_id = liner_id;
        this.from = from;
        this.to = to;
        this.is_paid = is_paid;
        this.price = price;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setLiner_id(long liner_id) {
        this.liner_id = liner_id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setIs_paid(boolean is_paid) {
        this.is_paid = is_paid;
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

    public long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getLiner_id() {
        return liner_id;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean isIs_paid() {
        return is_paid;
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
}
