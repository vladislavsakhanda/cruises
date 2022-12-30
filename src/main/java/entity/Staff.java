package entity;

public class Staff {
    private long id;
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
    }

    public Staff(String name, String surname, String specialization, long liner_id) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.liner_id = liner_id;
    }

    public Staff(long id, String name, String surname, String specialization, long liner_id) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.liner_id = liner_id;
    }

    public void setId(long id) {
        this.id = id;
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

    public long getId() {
        return id;
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
