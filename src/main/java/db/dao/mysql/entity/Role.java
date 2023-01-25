package db.dao.mysql.entity;

public class Role extends Entity {
    private String name;

    public enum Roles {
        ADMIN(1);
        private final long code;

        Roles(long code) {
            this.code = code;
        }

        public long getCode() {
            return code;
        }
    }

    public static Role createRole(long id, String name) {
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
