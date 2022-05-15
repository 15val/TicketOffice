package controller.command;

import model.dao.DaoFactory;
import model.dao.StationDao;
import model.entity.Station;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class GetStationsCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        DaoFactory daoFactory = DaoFactory.getInstance();
        StationDao stationDao = daoFactory.createStationDao();
        List<Station> stationList = stationDao.findAll();
        request.setAttribute("stationList", stationList);

        return "/WEB-INF/admin/showAllStations.jsp";
    }
}
