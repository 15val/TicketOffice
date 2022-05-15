package model.dao;

import model.dao.impl.JDBCDaoFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract UserDao createUserDao();

    public abstract TrainDao createTrainDao();

    public abstract StationDao createStationDao();
    public abstract RouteDao createRouteDao();
    public abstract TicketDao createTicketDao();

    public static DaoFactory getInstance(){
        if(daoFactory == null){
            synchronized (DaoFactory.class){
                if(daoFactory==null){
                    daoFactory = new JDBCDaoFactory();
                }
            }
        }
        return daoFactory;
    }


}

