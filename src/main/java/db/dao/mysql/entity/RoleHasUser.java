package db.dao.mysql.entity;

import exeptions.IllegalFieldException;

public class RoleHasUser extends Entity {
    private long role_id;
    private long user_id;

    public static RoleHasUser createRoleHasUser(long role_id, long user_id) throws IllegalFieldException {
        RoleHasUser roleHasUser = new RoleHasUser();
        roleHasUser.setRoleId(role_id);
        roleHasUser.setUserId(user_id);
        return roleHasUser;
    }

    public RoleHasUser() {
        super();
    }

    public RoleHasUser(long id) throws IllegalFieldException {
        super(id);
    }

    public RoleHasUser(long role_id, long user_id) throws IllegalFieldException {
        // We use the ready-made createRoleHasUser method, which has checks
        RoleHasUser roleHasUser = RoleHasUser.createRoleHasUser(role_id, user_id);

        this.role_id = roleHasUser.getRoleId();
        this.user_id = roleHasUser.getUserId();
    }

    public void setRoleId(long role_id) throws IllegalFieldException {
        if (role_id <= 0) {
            throw new IllegalFieldException("liner_id must be greater than zero.");
        }
        this.role_id = role_id;
    }

    public void setUserId(long user_id) throws IllegalFieldException {
        if (user_id <= 0) {
            throw new IllegalFieldException("user_id must be greater than zero.");
        }
        this.user_id = user_id;
    }

    public long getRoleId() {
        return role_id;
    }

    public long getUserId() {
        return user_id;
    }


}
