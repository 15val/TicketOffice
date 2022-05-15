package controller.command;

import model.dao.DaoFactory;
import model.dao.RouteDao;
import model.entity.Route;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class GetRoutesUserCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        RouteDao routeDao = daoFactory.createRouteDao();
        List<Route> routeList = routeDao.findAll();
        request.setAttribute("routeList", routeList);

        return "/WEB-INF/user/userbasis.jsp";
    }
}