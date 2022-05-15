package controller;

import controller.command.*;
import model.entity.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Servlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    public void init(ServletConfig servletConfig){


        servletConfig.getServletContext()
                .setAttribute("loggedUser", new User());

        commands.put("logout", new LogOutCommand());
        commands.put("login", new LoginCommand());
        commands.put("register", new RegisterCommand());
        commands.put("GetUsers", new GetUsersCommand());
        commands.put("listStation", new GetStationsCommand());
        commands.put("insertStation", new CreateStationCommand());
        commands.put("deleteStation", new DeleteStationCommand());
        commands.put("newStation", new ShowNewStationFormCommand());
        commands.put("updateStation", new UpdateStationCommand());
        commands.put("editStation", new ShowEditStationFormCommand());
        commands.put("adminListRoute", new GetRoutesCommand());
        commands.put("userListRoute", new GetRoutesUserCommand());
        commands.put("listRoute", new GetRoutesPublicCommand());
        commands.put("insertRoute", new CreateRouteCommand());
        commands.put("deleteRoute", new DeleteRouteCommand());
        commands.put("newRoute", new ShowNewRouteFormCommand());
        commands.put("updateRoute", new UpdateRouteCommand());
        commands.put("editRoute", new ShowEditRouteFormCommand());
        commands.put("showDetailsRoute", new ShowDetailsRouteCommand());
        commands.put("buyTicket", new CreateTicketCommand());
        commands.put("listMyTickets", new GetMyTicketsCommand());
        commands.put("getUserProfile", new GetUserProfileCommand());
        commands.put("topUpBalance", new TopUpBalanceCommand());
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        processRequest(request, response);

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {



        String path = request.getRequestURI();
        path = path.replaceAll(".*/ticketoffice/" , "");
        Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp");
        String page = null;
        try {
            page = command.execute(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if(page.contains("redirect:")){
            if(!response.isCommitted()) {
                response.sendRedirect(page.replace("redirect:", "/ticketoffice"));
            }
        }else {
            if(!response.isCommitted()) {
                request.getRequestDispatcher(page).forward(request, response);
            }
        }
    }
}