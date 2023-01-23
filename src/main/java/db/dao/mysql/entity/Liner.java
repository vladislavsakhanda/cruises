package db.dao.mysql.entity;

import java.sql.Date;

public class Liner extends Entity {
    private String name;
    private String description;
    private int capacity;
    private String route;
    private double price_coefficient;
    private Date date_start;
    private Date date_end;

    public static Liner createLiner(String name, String description, int capacity, String route, int price_coefficient) {
        Liner liner = new Liner();
        liner.setName(name);
        liner.setDescription(description);
        liner.setCapacity(capacity);
        liner.setRoute(route);
        liner.setPrice_coefficient(price_coefficient);
        return liner;
    }

    public Liner() {
        super();
    }

    public Liner(long id) {
        super(id);
    }

    public Liner(String name, String description, int capacity, String route,
                 int price_coefficient, Date date_start, Date date_end) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.route = route;
        this.price_coefficient = price_coefficient;
        this.date_start = date_start;
        this.date_end = date_end;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public void setPrice_coefficient(double price_coefficient) {
        this.price_coefficient = price_coefficient;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getRoute() {
        return route;
    }

    public double getPrice_coefficient() {
        return price_coefficient;
    }

    public Date getDate_start() {
        return date_start;
    }

    public void setDate_start(Date date_start) {
        this.date_start = date_start;
    }

    public Date getDate_end() {
        return date_end;
    }

    public void setDate_end(Date date_end) {
        this.date_end = date_end;
    }

    @Override
    public String toString() {
        return "Liner{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", route='" + route + '\'' +
                ", price_coefficient=" + price_coefficient +
                ", date_start=" + date_start +
                ", date_end=" + date_end +
                '}';
    }
}
