package controller.command;

import model.dao.DaoFactory;
import model.dao.RouteDao;
import model.entity.Route;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ShowDetailsRouteCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getInstance();
        RouteDao routeDao = daoFactory.createRouteDao();
        int idRoute;
        try {
            idRoute = Integer.parseInt(request.getParameter("idRoute"));
        }
        catch (NumberFormatException e){
            idRoute = Integer.parseInt(String.valueOf(session.getAttribute("idRoute")));
        }
        session.setAttribute("idRoute", idRoute);
        List<Route> routeList = new ArrayList<>();
        routeList.add(routeDao.findById(idRoute));
        request.setAttribute("routeList", routeList);
        return "/routeDetails.jsp";
    }
}
