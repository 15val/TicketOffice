package model.dao.impl;

import model.dao.StationDao;
import model.entity.Station;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCStationDao implements StationDao {

    private final Connection connection;

    public JDBCStationDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Station station) throws SQLException {
        String query = "INSERT INTO station (station_name_ua, station_name_en) VALUES (?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, station.getNameUA());
            preparedStatement.setString(2, station.getNameEN());
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
    public Station findById(int id) {
        Station station = null;
        String query = "SELECT * FROM station WHERE station_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){

                String stationNameUA = resultSet.getString("station_name_ua");
                String stationNameEN = resultSet.getString("station_name_en");
                station = new Station.Builder().withNameUA(stationNameUA).withNameEN(stationNameEN).withId(id).build();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }


        return station;
    }

    @Override
    public List<Station> findAll() {
        List<Station> list = new ArrayList<>();
        String query = "SELECT * FROM station";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet  resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
               Station station = new Station.Builder()
                        .withId(resultSet.getInt("station_id"))
                        .withNameUA(resultSet.getString("station_name_ua"))
                        .withNameEN(resultSet.getString("station_name_en"))
                        .build();

                list.add(station);
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
    public void update(Station station) throws SQLException {
        String query = "UPDATE station SET station_name_ua = ?, station_name_en = ? WHERE station_id = ?";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,station.getNameUA());
            preparedStatement.setString(2,station.getNameEN());
            preparedStatement.setInt(3,station.getId());
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
        String query = "DELETE FROM station WHERE station_id = ?";
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
