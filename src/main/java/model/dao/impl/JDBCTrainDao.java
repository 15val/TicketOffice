package model.dao.impl;

import model.dao.TrainDao;
import model.entity.Train;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTrainDao implements TrainDao {

    private final Connection connection;

    public JDBCTrainDao(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void create(Train entity) throws SQLException {

    }

    @Override
    public Train findById(int id) {
        Train train = null;
        String query = "SELECT * FROM train WHERE train_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                int allSeats = resultSet.getInt("train_all_seats");
                train = new Train.Builder().withAllSeats(allSeats).withTrainId(id).build();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }


        return train;
    }

    @Override
    public List<Train> findAll() {
        List<Train> list = new ArrayList<>();
        String query = "SELECT * FROM train";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet  resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Train train = new Train.Builder()
                        .withTrainId(resultSet.getInt("train_id"))
                        .withAllSeats(resultSet.getInt("train_all_seats"))
                        .build();

                list.add(train);
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
    public void update(Train entity) throws SQLException {

    }

    @Override
    public void delete(int id) throws SQLException {

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
