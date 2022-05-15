package controller.command;

import model.dao.DaoFactory;
import model.dao.StationDao;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class DeleteStationCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        DaoFactory daoFactory = DaoFactory.getInstance();
        StationDao stationDao = daoFactory.createStationDao();
        stationDao.delete(id);
        return "redirect:/listStation";
    }
}
