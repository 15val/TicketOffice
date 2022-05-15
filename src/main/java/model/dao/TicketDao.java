package model.dao;

import model.entity.Ticket;

import java.sql.SQLException;
import java.util.List;

public interface TicketDao extends GenericDao<Ticket>{
    int checkAssignedRouteExists(int routeId) throws SQLException;
    List<Ticket> findTicketsByUserId(int userId);
}
