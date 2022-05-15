package controller.command;

import model.dao.DaoFactory;
import model.dao.RouteDao;
import model.entity.Route;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class ShowEditRouteFormCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getInstance();
        RouteDao routeDao = daoFactory.createRouteDao();
        int id = 0;
        if(request.getParameter("idRoute") != null) {
            id = Integer.parseInt(request.getParameter("idRoute"));
        }
        else {
            id = Integer.parseInt(String.valueOf(session.getAttribute("idRoute")));
        }

        Route existingRoute = routeDao.findById(id);
        request.setAttribute("route", existingRoute);
        return "/WEB-INF/admin/createRoute.jsp";
    }
}
