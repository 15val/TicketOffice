package controller.command;

import model.dao.DaoFactory;
import model.dao.RouteDao;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class DeleteRouteCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        int id = Integer.parseInt(request.getParameter("idRoute"));
        DaoFactory daoFactory = DaoFactory.getInstance();
        RouteDao routeDao = daoFactory.createRouteDao();
        routeDao.delete(id);
        return "redirect:/adminListRoute";
    }
}
