package db.dao.mysql.entity;

public class Liner extends Entity {
    private String name;
    private String description;
    private int capacity;
    private String route;
    private double price_coefficient;

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

    public Liner(String name, String description, int capacity, String route, int price_coefficient) {
        this.name = name;
        this.description = description;
        this.capacity = capacity;
        this.route = route;
        this.price_coefficient = price_coefficient;
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

    @Override
    public String toString() {
        return "Liner{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", capacity=" + capacity +
                ", route='" + route + '\'' +
                ", price_coefficient=" + price_coefficient +
                '}';
    }
}