package services;

import db.dao.*;
import db.dao.mysql.MySqlUserDAO;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory() {

    }

    public static synchronized ServiceFactory getServiceFactory() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    UserService userService;
    TripService tripService;
    StaffService staffService;
    RoleService roleService;
    RoleHasUserService roleHasUserService;
    LinerService linerService;

    public UserService getUserService(UserDAO userDAO) {
        if (userService == null) {
            userService = new UserService(userDAO);
        }
        return userService;
    }

    public TripService getTripService(TripDAO tripDAO) {
        if (tripService == null) {
            tripService = new TripService(tripDAO);
        }
        return tripService;
    }

    public StaffService getStaffService(StaffDAO staffDAO) {
        if (staffService == null) {
            staffService = new StaffService(staffDAO);
        }
        return staffService;
    }

    public RoleService getRoleService(RoleDAO roleDAO) {
        if (roleService == null) {
            roleService = new RoleService(roleDAO);
        }
        return roleService;
    }

    public RoleHasUserService getRoleHasUserService(RoleHasUserDAO roleHasUserDAO) {
        if (roleHasUserService == null) {
            roleHasUserService = new RoleHasUserService(roleHasUserDAO);
        }
        return roleHasUserService;
    }

    public LinerService getLinerService(LinerDAO linerDAO) {
        if (linerService == null) {
            linerService = new LinerService(linerDAO);
        }
        return linerService;
    }
}
