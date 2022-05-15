package controller.command;

import model.dao.DaoFactory;
import model.dao.RouteDao;
import model.dao.TicketDao;
import model.dao.UserDao;
import model.entity.Route;
import model.entity.Ticket;
import model.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateTicketCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();

        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(((String)request.getSession().getAttribute("lang"))));
        if(session.getAttribute("loggedUserId") == null){
            session.setAttribute("ticketCreateValidationError", bundle.getString("user.has.to.login"));
            return "redirect:/login.jsp";
        }

        int idUser = Integer.parseInt(String.valueOf(session.getAttribute("loggedUserId")));
        int idRoute = Integer.parseInt(request.getParameter("idRoute"));

        DaoFactory daoFactory = DaoFactory.getInstance();
        RouteDao routeDao = daoFactory.createRouteDao();
        Route routeAssignedToTicket = routeDao.findById(idRoute);

        DaoFactory daoFactory1 = DaoFactory.getInstance();
        UserDao userDao = daoFactory1.createUserDao();
        User currentUser = userDao.findById(idUser);

        if(currentUser.getBalance().compareTo(routeAssignedToTicket.getPrice()) < 0){
            session.setAttribute("ticketCreateValidationError", bundle.getString("user.has.not.enough.money"));
            return "redirect:/user/profile.jsp";
        }

        Route updatedRoute = new Route.Builder()
                .withRouteId(routeAssignedToTicket.getRouteId())
                .withFirstStation(routeAssignedToTicket.getFirstStation())
                .withLastStation(routeAssignedToTicket.getLastStation())
                .withDeparture(routeAssignedToTicket.getDeparture())
                .withArrival(routeAssignedToTicket.getArrival())
                .withTrainId(routeAssignedToTicket.getTrainId())
                .withRouteTime(routeAssignedToTicket.getRouteTime())
                .withFreeSeats(routeAssignedToTicket.getFreeSeats() - 1)
                .withPrice(routeAssignedToTicket.getPrice())
                .build();

        User updatedUser = new User.Builder()
                .withId(currentUser.getId())
                .withNickname(currentUser.getNickname())
                .withPassword(currentUser.getPassword())
                .withEmail(currentUser.getEmail())
                .withRole(currentUser.getRole())
                .withName(currentUser.getName())
                .withBalance(currentUser.getBalance().subtract(routeAssignedToTicket.getPrice()))
                .build();

        Ticket newTicket = new Ticket.Builder()
                .withRouteId(routeAssignedToTicket.getRouteId())
                .withPrice(routeAssignedToTicket.getPrice())
                .withUserId(idUser)
                .build();

        try {
            DaoFactory daoFactory2 = DaoFactory.getInstance();
            TicketDao ticketDao = daoFactory2.createTicketDao();
            ticketDao.create(newTicket);

            DaoFactory daoFactory3 = DaoFactory.getInstance();
            UserDao userDao1 = daoFactory3.createUserDao();
            userDao1.update(updatedUser);

            DaoFactory daoFactory4 = DaoFactory.getInstance();
            RouteDao routeDao1 = daoFactory4.createRouteDao();
            routeDao1.update(updatedRoute);
            session.setAttribute("ticketCreateComplete", bundle.getString("ticket.create.complete"));
        }
        catch (Exception e){
            e.printStackTrace();
            session.setAttribute("ticketCreateValidationError", bundle.getString("unknown.error"));
        }

        return "redirect:/listMyTickets";
    }
}
