package db.dao.mysql.entity;

import exeptions.IllegalFieldException;

import java.util.regex.Pattern;

import static db.dao.mysql.entity.EntityConstants.REGEX_NAME_AND_SURNAME;

public class Staff extends Entity {
    private String name;
    private String surname;
    private Specialization specialization;
    private long liner_id;

    public static enum Specialization {
        COOK("COOK"),
        CLEANER("CLEANER"),
        SAILOR("SAILOR"),
        CAPTAIN("CAPTAIN");

        private final String special;
        Specialization(String code) {
            this.special = code;
        }

        public String getCode() {
            return special;
        }
    }

    public static Staff createStaff(String name, String surname,
                                    Specialization specialization, long liner_id) throws IllegalFieldException {
        Staff staff = new Staff();
        staff.setName(name);
        staff.setSurname(surname);
        staff.setSpecialization(specialization);
        staff.setLinerId(liner_id);
        return staff;
    }

    public Staff() {
        super();
    }

    public Staff(long id) throws IllegalFieldException {
        super(id);
    }

    public Staff(String name, String surname,
                 Specialization specialization, long liner_id) throws IllegalFieldException {
        // We use the ready-made createStaff method, which has checks
        Staff staff = Staff.createStaff(name, surname, specialization, liner_id);

        this.name = staff.getName();
        this.surname = staff.getSurname();
        this.specialization = staff.getSpecialization();
        this.liner_id = staff.getLiner_id();
    }

    public void setName(String name) throws IllegalFieldException {
        if (name == null) {
            throw new IllegalFieldException("Name is null.");
        } else if (name.length() < 3) {
            throw new IllegalFieldException("First name must contain at least 3 characters.");
        } else if (!Pattern.compile(REGEX_NAME_AND_SURNAME).matcher(name).matches()) {
            throw new IllegalFieldException("First name is incorrect.");
        }
        this.name = name;
    }

    public void setSurname(String surname) throws IllegalFieldException {
        if (surname == null) {
            throw new IllegalFieldException("Surname is null.");
        } else if (surname.length() < 3) {
            throw new IllegalFieldException("Surname must contain at least 3 characters.");
        } else if (!Pattern.compile(REGEX_NAME_AND_SURNAME).matcher(surname).matches()) {
            throw new IllegalFieldException("Surname is incorrect.");
        }
        this.surname = surname;
    }

    public void setSpecialization(Specialization specialization) throws IllegalFieldException {
        if (specialization == null) {
            throw new IllegalFieldException("specialization is null.");
        }
        this.specialization = specialization;
    }

    public void setLinerId(long liner_id) throws IllegalFieldException {
        if (liner_id <= 0) {
            throw new IllegalFieldException("liner_id must be greater than zero.");
        }
        this.liner_id = liner_id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public long getLiner_id() {
        return liner_id;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", specialization='" + specialization + '\'' +
                ", liner_id=" + liner_id +
                '}';
    }
}
