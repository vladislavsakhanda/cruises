package entity;

public class Schedule {
    private long id;
    private String route_and_schedule;
    private long liner_id;

    public static Schedule createSchedule(String route_and_schedule, long liner_id) {
        Schedule schedule = new Schedule();
        schedule.setRoute_and_schedule(route_and_schedule);
        schedule.setLiner_id(liner_id);
        return schedule;
    }

    public Schedule() {
    }

    public Schedule(String route_and_schedule, long liner_id) {
        this.route_and_schedule = route_and_schedule;
        this.liner_id = liner_id;
    }

    public Schedule(long id, String route_and_schedule, long liner_id) {
        this.id = id;
        this.route_and_schedule = route_and_schedule;
        this.liner_id = liner_id;
    }

    public long getId() {
        return id;
    }

    public String getRoute_and_schedule() {
        return route_and_schedule;
    }

    public long getLiner_id() {
        return liner_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRoute_and_schedule(String route_and_schedule) {
        this.route_and_schedule = route_and_schedule;
    }

    public void setLiner_id(long liner_id) {
        this.liner_id = liner_id;
    }
}
