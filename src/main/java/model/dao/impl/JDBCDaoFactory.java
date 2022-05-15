package model.dao.impl;

import model.dao.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDaoFactory extends DaoFactory {

    private final DataSource dataSource = ConnectionPoolHolder.getDataSource();

    @Override
    public UserDao createUserDao() {
        return new JDBCUserDao(getConnection());
    }
    @Override
    public TrainDao createTrainDao() {
        return new JDBCTrainDao(getConnection());
    }
    @Override
    public StationDao createStationDao() {
        return new JDBCStationDao(getConnection());
    }
    @Override
    public RouteDao createRouteDao() {
        return new JDBCRouteDao(getConnection());
    }
    @Override
    public TicketDao createTicketDao() {
        return new JDBCTicketDao(getConnection());
    }


    private Connection getConnection(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

