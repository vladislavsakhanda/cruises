package db.dao.mysql.entity;

import exeptions.IllegalFieldException;

import java.io.InputStream;
import java.sql.Date;

public class Trip extends Entity {
    private long userId;
    private long linerId;
    private boolean isPaid;
    private double price;
    private Date dateStart;
    private Date dateEnd;
    private Status status;
    private InputStream passport;

    public enum Status {
        PENDING(0),
        REQUIRING_PAYMENT(1),
        REJECTED(2),
        CONFIRMED(3);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public static Status valueOf(int code) throws IllegalFieldException {
            switch (code) {
                case 0:
                    return PENDING;
                case 1:
                    return REQUIRING_PAYMENT;
                case 2:
                    return REJECTED;
                case 3:
                    return CONFIRMED;
                default:
                    throw new IllegalFieldException();
            }
        }

        public int getCode() {
            return code;
        }
    }


    public static boolean datesCheck(Date dateStart, Date dateEnd) throws IllegalFieldException {
        if (dateStart.compareTo(dateEnd) > 0) {
            throw new IllegalFieldException("dateStart cannot be later than dateEnd");
        }
        return true;
    }

    public static Trip createTrip(long user_id, long liner_id, boolean is_paid, double price,
                                  Date dateStart, Date dateEnd, Status status, InputStream passport) throws IllegalFieldException {
        Trip trip = new Trip();
        trip.setUserId(user_id);
        trip.setLinerId(liner_id);
        trip.setIsPaid(is_paid);
        trip.setPrice(price);
        trip.setDateStart(dateStart);
        trip.setDateEnd(dateEnd);
        trip.setStatus(status);
        trip.setPassport(passport);
        // throw IllegalFieldException if dateStart later than dateEnd
        datesCheck(dateStart, dateEnd);
        return trip;
    }

    public Trip() {
        super();
    }

    public Trip(long id) throws IllegalFieldException {
        super(id);
    }

    public Trip(long userId, long linerId, boolean isPaid, double price,
                Date dateStart, Date dateEnd, Status status, InputStream passport) throws IllegalFieldException {
        // We use the ready-made createTrip method, which has checks
        Trip trip = Trip.createTrip(userId, linerId, isPaid, price,
                dateStart, dateEnd, status, passport);

        this.userId = trip.getUserId();
        this.linerId = trip.getLinerId();
        this.isPaid = trip.getIsPaid();
        this.price = trip.getPrice();
        this.dateStart = trip.getDateStart();
        this.dateEnd = trip.getDateEnd();
        this.status = trip.getStatus();
        this.passport = trip.getPassport();
    }

    public void setUserId(long user_id) throws IllegalFieldException {
        if (user_id <= 0) {
            throw new IllegalFieldException("user_id must be greater than zero.");
        }
        this.userId = user_id;
    }

    public void setLinerId(long liner_id) throws IllegalFieldException {
        if (liner_id <= 0) {
            throw new IllegalFieldException("liner_id must be greater than zero.");
        }
        this.linerId = liner_id;
    }

    public void setPrice(double price) throws IllegalFieldException {
        if (price < 0) {
            throw new IllegalFieldException("price must be greater or equal than zero.");
        }
        this.price = price;
    }

    public void setDateStart(Date dateStart) throws IllegalFieldException {
        if (dateStart == null) {
            throw new IllegalFieldException("dateStart cannot be null");
        }
        this.dateStart = dateStart;
    }

    public void setDateEnd(Date dateEnd) throws IllegalFieldException {
        if (dateEnd == null) {
            throw new IllegalFieldException("dateEnd cannot be null");
        }
        this.dateEnd = dateEnd;
    }

    public void setPassport(InputStream passport) throws IllegalFieldException {
        this.passport = passport;
    }

    public void setIsPaid(boolean is_paid) {
        this.isPaid = is_paid;
    }

    public void setStatus(Status status) throws IllegalFieldException {
        if (status == null) {
            throw new IllegalFieldException("price must be greater or equal than zero.");
        }
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public long getLinerId() {
        return linerId;
    }

    public double getPrice() {
        return price;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public InputStream getPassport() {
        return passport;
    }

    public boolean getIsPaid() {
        return isPaid;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + getId() +
                ", user_id=" + userId +
                ", liner_id=" + linerId +
                ", is_paid=" + isPaid +
                ", price=" + price +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", status=" + status +
                '}';
    }
}
