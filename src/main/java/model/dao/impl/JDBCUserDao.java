package model.dao.impl;

import model.dao.UserDao;
import model.entity.User;
import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCUserDao implements UserDao {
    private final Connection connection;

    public JDBCUserDao(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void create(User user) throws SQLException {
        String query = "INSERT INTO user (user_name, user_nickname, user_password, user_email, user_role) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getNickname());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, String.valueOf(user.getRole()));
            preparedStatement.execute();
        }
        catch (SQLException ex) {
           ex.printStackTrace();
            throw new SQLException();
        }
        finally {
            close();
        }

    }

    @Override
    public User findById(int id) {
       User user = null;
        String query = "SELECT * FROM user WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = new User.Builder()
                        .withId(resultSet.getInt("user_id"))
                        .withNickname(resultSet.getString("user_nickname"))
                        .withPassword(resultSet.getString("user_password"))
                        .withEmail(resultSet.getString("user_email"))
                        .withRole(User.ROLE.valueOf(resultSet.getString("user_role")))
                        .withName(resultSet.getString("user_name"))
                        .withBalance(resultSet.getBigDecimal("user_balance").toBigInteger())
                        .build();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }


        return user;
    }



    @Override
    public List<User> findAll(){
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM user";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet  resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User.Builder()
                        .withId(resultSet.getInt("user_id"))
                        .withNickname(resultSet.getString("user_nickname"))
                        .withPassword(resultSet.getString("user_password"))
                        .withEmail(resultSet.getString("user_email"))
                        .withRole(User.ROLE.valueOf(resultSet.getString("user_role")))
                        .withName(resultSet.getString("user_name"))
                        .withBalance(resultSet.getBigDecimal("user_balance").toBigInteger())
                        .build();

                list.add(user);
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
    public void update(User user) throws SQLException {
        String query = "UPDATE user SET user_nickname = ?," +
                "user_password = ?, user_email = ?, user_role = ?, user_name = ?, user_balance = ?" +
                " WHERE user_id = ?";

        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getNickname());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, String.valueOf(user.getRole()));
            preparedStatement.setString(5, user.getName());
            preparedStatement.setLong(6, user.getBalance().longValue());
            preparedStatement.setInt(7,user.getId());
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
    public void delete(int id) {

    }

    @Override
    public void close()  {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public List<User> findUsers(int currentPage, int recordsPerPage) {
        List<User> users = new ArrayList<>();
        int start = currentPage * recordsPerPage - recordsPerPage;
        String query = "SELECT * FROM user LIMIT ?, ?";


        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, recordsPerPage);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User.Builder()
                        .withId(resultSet.getInt("user_id"))
                        .withNickname(resultSet.getString("user_nickname"))
                        .withPassword(resultSet.getString("user_password"))
                        .withEmail(resultSet.getString("user_email"))
                        .withRole(User.ROLE.valueOf(resultSet.getString("user_role")))
                        .withName(resultSet.getString("user_name"))
                        .withBalance(resultSet.getBigDecimal("user_balance").toBigInteger())
                        .build();

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }


    public int getNumberOfRows() {
        int numberOfRows = 0;

        String query = "SELECT COUNT(user_id) FROM user";

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                numberOfRows = resultSet.getInt(1);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }
        return numberOfRows;
    }

    @Override
    public Optional<User> findByNickname(String nickname) {

        Optional<User> result = Optional.empty();
        try(PreparedStatement ps = connection.prepareCall("SELECT * FROM user WHERE user_nickname = ?")){
            ps.setString( 1, nickname);
            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()){
                User user = new User.Builder()
                        .withId(rs.getInt("user_id"))
                        .withNickname(rs.getString("user_nickname"))
                        .withPassword(rs.getString("user_password"))
                        .withEmail(rs.getString("user_email"))
                        .withName(rs.getString("user_name"))
                        .withRole(User.ROLE.valueOf(rs.getString("user_role")))
                        .withBalance(rs.getBigDecimal("user_balance").toBigInteger())
                        .build();
                result = Optional.of(user);
            }
        }catch (SQLException ex){
            return Optional.empty();
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return result;
    }

    @Override
    public void topUpBalance(int id, BigInteger value) {
        String query = "UPDATE user SET user_balance = user_balance + ? WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, value.longValue());
            preparedStatement.setInt(2, id);
            preparedStatement.execute();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }
    }
}
