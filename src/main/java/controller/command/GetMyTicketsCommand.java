package controller.command;

import model.dao.DaoFactory;
import model.dao.TicketDao;
import model.entity.Ticket;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GetMyTicketsCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        TicketDao ticketDao = daoFactory.createTicketDao();

        List<Ticket> ticketList = ticketDao.findTicketsByUserId((Integer) request.getSession().getAttribute("loggedUserId"));
        List<Ticket> filteredTicketList = new ArrayList<>();
        Ticket currentTicket;

        ListIterator<Ticket> listIterator = ticketList.listIterator();
        while (listIterator.hasNext()){

            currentTicket = listIterator.next();
            TicketDao ticketDao1 = daoFactory.createTicketDao();
            if(ticketDao1.checkAssignedRouteExists(currentTicket.getRouteId()) == 1){
                filteredTicketList.add(currentTicket);
            }

        }
        request.setAttribute("ticketList", filteredTicketList);
        return "/WEB-INF/user/myTickets.jsp";
    }
}
