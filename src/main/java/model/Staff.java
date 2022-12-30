package model;

public class Staff extends Model {
    private String name;
    private String surname;
    private String specialization;
    private long liner_id;

    public static Staff createStaff(String name, String surname, String specialization, long liner_id) {
        Staff staff = new Staff();
        staff.setName(name);
        staff.setSurname(surname);
        staff.setSpecialization(specialization);
        staff.setLiner_id(liner_id);
        return staff;
    }

    public Staff() {
        super();
    }

    public Staff(long id) {
        super(id);
    }

    public Staff(String name, String surname, String specialization, long liner_id) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.liner_id = liner_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setLiner_id(long liner_id) {
        this.liner_id = liner_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public long getLiner_id() {
        return liner_id;
    }
}
