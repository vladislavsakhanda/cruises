package model;

public class Role extends Model {
    private String name;

    public static Role createRole (long id, String name) {
        Role role = new Role();
        role.setId(id);
        role.setName(name);
        return role;
    }

    public Role() {
        super();
    }

    public Role(long id) {
        super(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
