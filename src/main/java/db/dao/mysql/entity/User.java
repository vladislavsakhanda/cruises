package db.dao.mysql.entity;

import exeptions.IllegalFieldException;

import java.util.regex.Pattern;

import static db.dao.mysql.entity.EntityConstants.REGEX_EMAIL;
import static db.dao.mysql.entity.EntityConstants.REGEX_NAME_AND_SURNAME;

public class User extends Entity {
    private String name;
    private String surname;
    private String email;
    private String password;

    public static User createUser(String name, String surname, String email, String password) throws IllegalFieldException {
        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public static User createUser(long id, String name, String surname, String email, String password) throws IllegalFieldException {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setSurname(surname);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }

    public User() {
        super();
    }

    public User(long id) throws IllegalFieldException {
        super(id);
    }

    public User(String name, String surname, String email, String password) throws IllegalFieldException {
        // We use the ready-made createUser method, which has checks
        User user = User.createUser(name, surname, email, password);

        this.name = user.getName();
        this.surname = user.getSurname();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setEmail(String email) throws IllegalFieldException {
        if (email == null) {
            throw new IllegalFieldException("Email is null.");
        } else if (email.length() == 0) {
            throw new IllegalFieldException("Email cannot be zero.");
        } else if (!Pattern.compile(REGEX_EMAIL).matcher(email).matches()) {
            throw new IllegalFieldException("Email is incorrect.");
        }
        this.email = email;
    }

    public void setPassword(String password) throws IllegalFieldException {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + getId() + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
