package model.dao.impl;

import model.dao.TicketDao;
import model.entity.Ticket;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCTicketDao implements TicketDao {

    private final Connection connection;

    public JDBCTicketDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int checkAssignedRouteExists(int routeId) throws SQLException {
    int count = 0;
    String query = "SELECT COUNT(route_id) FROM route WHERE route_id = ?";
    PreparedStatement preparedStatement = connection.prepareStatement(query);
        try {
            preparedStatement.setInt(1, routeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        }
            catch (SQLException e){
                e.printStackTrace();
            }
            finally {
              close();
            }
        return count;
    }

    @Override
    public void create(Ticket ticket) throws SQLException {
        String query = "INSERT INTO ticket  (route_id, price, user_id) VALUES (?, ?, ?)";


        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, ticket.getRouteId());
            preparedStatement.setLong(2, ticket.getPrice().longValue());
            preparedStatement.setInt(3,ticket.getUserId());
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
    public Ticket findById(int id) {
        Ticket ticket = null;
        String query = "SELECT * FROM ticket WHERE ticket_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                ticket = new Ticket.Builder()
                        .withTicketId(resultSet.getInt("ticket_id"))
                        .withRouteId(resultSet.getInt("route_id"))
                        .withPrice(resultSet.getBigDecimal("price").toBigInteger())
                        .withUserId(resultSet.getInt("user_id"))
                        .build();
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            close();
        }
        return ticket;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }

    @Override
    public void update(Ticket entity) throws SQLException {

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

    @Override
    public List<Ticket> findTicketsByUserId(int userId) {
        List<Ticket> list = new ArrayList<>();
        String query = "SELECT * FROM ticket WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);
            ResultSet  resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Ticket ticket = new Ticket.Builder()
                        .withTicketId(resultSet.getInt("ticket_id"))
                        .withRouteId(resultSet.getInt("route_id"))
                        .withPrice(resultSet.getBigDecimal("price").toBigInteger())
                        .withUserId(resultSet.getInt("user_id"))
                        .build();

                list.add(ticket);
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
}
