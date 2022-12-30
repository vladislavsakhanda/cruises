package entity;

public class RoleHasUser {
    private long role_id;
    private long user_id;

    public static RoleHasUser createRoleHasUser(long role_id, long user_id) {
        RoleHasUser roleHasUser = new RoleHasUser();
        roleHasUser.setRole_id(role_id);
        roleHasUser.setUser_id(user_id);
        return roleHasUser;
    }

    public RoleHasUser() {
    }

    public RoleHasUser(long role_id, long user_id) {
        this.role_id = role_id;
        this.user_id = user_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public long getUser_id() {
        return user_id;
    }


}
