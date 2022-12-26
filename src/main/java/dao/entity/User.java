package dao.entity;

public class User {
    private long id;
    private String full_name;
    private String email;
    private String password;
    private boolean paid;
    private long liner_id;//

    public static User createUser(String full_name, String password, boolean paid) {
        User user = new User();
        user.setFull_name(full_name);
        user.setPassword(password);
        user.setPaid(paid);

        return user;
    }

    public User() {

    }

    public User(String full_name, String email, String password, boolean paid) {
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.paid = paid;
    }

    public User(long id, String full_name, String email, String password, boolean paid, long liner_id) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
        this.password = password;
        this.paid = paid;
        this.liner_id = liner_id;
    }

    public long getId() {
        return id;
    }

    public String getFull_name() {
        return full_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPaid() {
        return paid;
    }

    public long getLiner_id() {
        return liner_id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public void setLiner_id(long liner_id) {
        this.liner_id = liner_id;
    }
}
