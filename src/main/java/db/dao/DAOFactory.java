package db.dao;

public abstract class DAOFactory {
    private static DAOFactory instance;

    public static synchronized DAOFactory getDAOFactory () throws Exception {
        if (instance == null) {
            Class<?> clazz = Class.forName(DAOFactory.daoFactoryFCN);
            instance = (DAOFactory) clazz.getDeclaredConstructor().newInstance();
        }
        return instance;
    }

    protected DAOFactory () {

    }
    private static String daoFactoryFCN;

    public static void setDaoFactoryFCN (String daoFactoryFCN) {
        instance = null;
        DAOFactory.daoFactoryFCN = daoFactoryFCN;
    }

    public abstract UserDAO getUserDao();
    public abstract TripDAO getTripDAO();
    public abstract StaffDAO getStaffDAO();
    public abstract ScheduleDAO getScheduleDAO();
    public abstract RoleHasUserDAO getRoleHasUserDAO();
    public abstract RoleDAO getRoleDAO();
    public abstract LinerDAO getLinerDAO();
    public abstract DocumentDAO getDocumentDAO();
}
