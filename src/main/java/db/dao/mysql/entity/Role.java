package db.dao.mysql.entity;

import exeptions.IllegalFieldException;

public class Role extends Entity {
    private Roles roles;

    public enum Roles {
        ADMIN(3);
        private final long code;

        Roles(long code) {
            this.code = code;
        }

        public long getCode() {
            return code;
        }
    }

    public static Role createRole(long id, Roles roles) throws IllegalFieldException {
        Role role = new Role();
        role.setId(id);
        role.setRole(roles);
        return role;
    }

    public Role() {
        super();
    }

    public Role(long id) throws IllegalFieldException {
        super(id);
    }

    public Roles getRole() {
        return roles;
    }

    public void setRole(Roles roles) throws IllegalFieldException {
        if (roles == null)
            throw new IllegalFieldException("role is null.");
        this.roles = roles;
    }
}
