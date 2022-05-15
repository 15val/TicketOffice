package model.dao.impl;

import model.dao.DaoFactory;
import model.dao.RouteDao;
import model.dao.StationDao;
import model.entity.Route;
import model.entity.Station;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCRouteDao implements RouteDao {

    private final Connection connection;

    public JDBCRouteDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(Route route) throws SQLException {
        String query = "INSERT INTO route (first_station_id, last_station_id, train_departure," +
                "train_arrival, train_id, route_time, free_seats, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,route.getFirstStation().getId());
            preparedStatement.setInt(2,route.getLastStation().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(route.getDeparture()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(route.getArrival()));
            preparedStatement.setInt(5, route.getTrainId());
            preparedStatement.setString(6, route.getRouteTime());
            preparedStatement.setInt(7, route.getFreeSeats());
            preparedStatement.setLong(8, route.getPrice().longValue());
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException ex) {
            connection.rollback();
            ex.printStackTrace();
            throw new SQLException();
        }
        finally {
            close();
        }
    }

    @Override
    public Route findById(int id) {
        Route route = null;
        String query = "SELECT * FROM route WHERE route_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                DaoFactory daoFactory = DaoFactory.getInstance();
                StationDao stationDao = daoFactory.createStationDao();
                Station firstStation = stationDao.findById(resultSet.getInt("first_station_id"));
                DaoFactory daoFactory2 = DaoFactory.getInstance();
                StationDao stationDao2 = daoFactory2.createStationDao();
                Station lastStation = stationDao2.findById(resultSet.getInt("last_station_id"));


                route = new Route.Builder()
                        .withRouteId(resultSet.getInt("route_id"))
                        .withFirstStation(firstStation)
                        .withLastStation(lastStation)
                        .withDeparture(resultSet.getTimestamp("train_departure").toLocalDateTime())
                        .withArrival(resultSet.getTimestamp("train_arrival").toLocalDateTime())
                        .withTrainId(resultSet.getInt("train_id"))
                        .withRouteTime(resultSet.getString("route_time"))
                        .withFreeSeats(resultSet.getInt("free_seats"))
                        .withPrice(resultSet.getBigDecimal("price").toBigInteger())
                        .build();

                }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }


        return route;

    }

    @Override
    public List<Route> findAll() {
        List<Route> list = new ArrayList<>();
        String query = "SELECT * FROM route";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet  resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                DaoFactory daoFactory = DaoFactory.getInstance();
                StationDao stationDao = daoFactory.createStationDao();
                Station firstStation = stationDao.findById(resultSet.getInt("first_station_id"));
                DaoFactory daoFactory2 = DaoFactory.getInstance();
                StationDao stationDao2 = daoFactory2.createStationDao();
                Station lastStation = stationDao2.findById(resultSet.getInt("last_station_id"));


                Route route = new Route.Builder()
                        .withRouteId(resultSet.getInt("route_id"))
                        .withFirstStation(firstStation)
                        .withLastStation(lastStation)
                        .withDeparture(resultSet.getTimestamp("train_departure").toLocalDateTime())
                        .withArrival(resultSet.getTimestamp("train_arrival").toLocalDateTime())
                        .withTrainId(resultSet.getInt("train_id"))
                        .withRouteTime(resultSet.getString("route_time"))
                        .withFreeSeats(resultSet.getInt("free_seats"))
                        .withPrice(resultSet.getBigDecimal("price").toBigInteger())
                        .build();

                        list.add(route);
            }

        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }

        return list;
    }

    @Override
    public void update(Route route) throws SQLException {
        String query = "UPDATE route SET first_station_id = ?, last_station_id = ?," +
                "train_departure = ?, train_arrival = ?, train_id = ?, route_time = ?," +
                " free_seats = ?, price = ? WHERE route_id = ?";

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,route.getFirstStation().getId());
            preparedStatement.setInt(2,route.getLastStation().getId());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(route.getDeparture()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(route.getArrival()));
            preparedStatement.setInt(5, route.getTrainId());
            preparedStatement.setString(6, route.getRouteTime());
            preparedStatement.setInt(7, route.getFreeSeats());
            preparedStatement.setLong(8, route.getPrice().longValue());
            preparedStatement.setInt(9,route.getRouteId());
            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException ex) {
            connection.rollback();
            ex.printStackTrace();
            throw new SQLException();
        }
        finally {
            close();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String query = "DELETE FROM route WHERE route_id = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);

            preparedStatement.execute();
            connection.commit();
        }
        catch (SQLException ex) {
            connection.rollback();
            ex.printStackTrace();
            throw new SQLException();
        }
        finally {
            close();
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
