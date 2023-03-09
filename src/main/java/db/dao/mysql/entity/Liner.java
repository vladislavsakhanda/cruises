package db.dao.mysql.entity;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import exeptions.IllegalFieldException;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Liner extends Entity {
    private String name;
    private String description;
    private int capacity;
    private String route;
    private double priceCoefficient;
    private Date dateStart;
    private Date dateEnd;

    public static boolean datesCheck(Date dateStart, Date dateEnd) throws IllegalFieldException {
        if (dateStart.compareTo(dateEnd) > 0) {
            throw new IllegalFieldException("dateStart cannot be later than dateEnd");
        }
        return true;
    }

    public static Liner createLiner(String name, String description, int capacity, List<String> route,
                                    int priceCoefficient, Date dateStart, Date dateEnd) throws IllegalFieldException {
        Liner liner = new Liner();
        liner.setName(name);
        liner.setDescription(description);
        liner.setCapacity(capacity);
        liner.setRoute(route);
        liner.setPriceCoefficient(priceCoefficient);
        liner.setDateStart(dateStart);
        liner.setDateEnd(dateEnd);
        // throw IllegalFieldException if dateStart later than dateEnd
        datesCheck(dateStart, dateEnd);
        return liner;
    }

    public Liner() {
        super();
    }

    public Liner(long id) throws IllegalFieldException {
        super(id);
    }

    public Liner(String name, String description, int capacity, List<String> route,
                 int priceCoefficient, Date dateStart, Date dateEnd) throws IllegalFieldException {
        // We use the ready-made createLiner method, which has checks
        Liner liner = Liner.createLiner(
                name, description, capacity, route,
                priceCoefficient, dateStart, dateEnd);

        this.name = liner.getName();
        this.description = liner.getDescription();
        this.capacity = liner.getCapacity();
        this.route = liner.getRoute();
        this.priceCoefficient = liner.getPriceCoefficient();
        this.dateStart = liner.getDateStart();
        this.dateEnd = liner.getDateEnd();
    }

    public int getNumberDays() {
        if (getDateStart() == null || getDateEnd() == null) {
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(LocalDate.parse(getDateStart().toString()),
                LocalDate.parse(getDateEnd().toString()));
    }

    public void setName(String name) throws IllegalFieldException {
        if (name == null) {
            throw new IllegalFieldException("Name is null.");
        } else if (name.length() == 0) {
            throw new IllegalFieldException("Name must contain at least 1 character.");
        }

        this.name = name;
    }

    public void setDescription(String description) throws IllegalFieldException {
        if (description == null) {
            throw new IllegalFieldException("description is null.");
        }

        this.description = description;
    }

    public void setCapacity(int capacity) throws IllegalFieldException {
        if (capacity < 0) {
            throw new IllegalFieldException("capacity must be greater than or equal to zero.");
        }
        this.capacity = capacity;
    }

    public void setRoute(List<String> route) throws IllegalFieldException {
        if (route == null) {
            throw new IllegalFieldException("route is null.");
        }

        Map<Integer, String> newRoute = new HashMap<>();
        int number = 0;
        for (String port : route) {
            newRoute.put(++number, port);
        }

//        if (this.dateStart != null && this.dateEnd != null)
        while (newRoute.size() < getNumberDays()) {
            newRoute.put(++number, "Port" + number);
        }

        JsonObject jsonObject = new JsonParser().parse(newRoute.toString()).getAsJsonObject();
        this.route = jsonObject.toString();
    }

    public void setPriceCoefficient(double priceCoefficient) throws IllegalFieldException {
        if (priceCoefficient <= 0) {
            throw new IllegalFieldException("priceCoefficient must be greater than zero.");
        }

        this.priceCoefficient = priceCoefficient;
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
        /*
        JSON Format
        '{"1":"Лез-Абім / Франція",
        "2":"День в море / Море",
        "3":"Скарборо / Трінідад і Тобаго",
        "4":"Гренада / Гренада",
        "5":"Бриджтаун / Барбадос",
        "6":"Сент-Люсія / Сент-Люсія",
        "7":"For-de-France, o. Мартініка / Мартініка",
        "8":"Лез-Абім / Франція"}'
         */
        return route;
    }

    public double getPriceCoefficient() {
        return priceCoefficient;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) throws IllegalFieldException {
        if (dateStart == null) {
            throw new IllegalFieldException("dateStart cannot be null");
        }
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) throws IllegalFieldException {
        if (dateEnd == null) {
            throw new IllegalFieldException("dateEnd cannot be null");
        }
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Liner{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", route='" + route + '\'' +
                ", priceCoefficient=" + priceCoefficient +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
