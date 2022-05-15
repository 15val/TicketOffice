package controller.command;

import model.dao.DaoFactory;
import model.dao.StationDao;
import model.entity.Station;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class ShowEditStationFormCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        DaoFactory daoFactory = DaoFactory.getInstance();
        StationDao stationDao = daoFactory.createStationDao();
        int id = 0;
        if(request.getParameter("id") != null) {
            id = Integer.parseInt(request.getParameter("id"));
        }
        else {
           id = Integer.parseInt(String.valueOf(session.getAttribute("id")));
        }

        Station existingStation = stationDao.findById(id);
        request.setAttribute("station", existingStation);
        return "/WEB-INF/admin/createStation.jsp";

    }
}
