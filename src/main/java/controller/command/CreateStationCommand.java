package controller.command;

import model.dao.DaoFactory;
import model.dao.StationDao;
import model.entity.Station;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class CreateStationCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();

        String stationNameUA = request.getParameter("stationNameUA");
        String stationNameEN = request.getParameter("stationNameEN");

        String stationNameUaRegex = "^[А-ЩЬЮЯҐІЇЄ][a-щьюяґіїє’]{0,20}[-]{0,1}[А-ЩЬЮЯҐІЇЄ]{0,1}[а-щьюяґіїє’]{0,20}$";
        String stationNameEnRegex = "^[A-Z][a-z']{0,20}[-]{0,1}[A-Z]{0,1}[a-z']{0,20}$";

        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(((String)request.getSession().getAttribute("lang"))));


        if(CommandUtility.validation(stationNameUA, stationNameUaRegex)){
            System.out.println("stationNameUA valid");
        }
        else{
            System.out.println("stationNameUA invalid");
            session.setAttribute("stationCreateValidationError", bundle.getString("station.name.ua.invalid"));
            return "redirect:/admin/createStation.jsp";
        }
        if(CommandUtility.validation(stationNameEN, stationNameEnRegex)){
            System.out.println("stationNameEN valid");
        }
        else{
            System.out.println("stationNameEN invalid");
           session.setAttribute("stationCreateValidationError", bundle.getString("station.name.en.invalid"));
            return "redirect:/admin/createStation.jsp";
        }


        Station newStation = new Station.Builder()
                .withNameUA(stationNameUA)
                .withNameEN(stationNameEN)
                .build();

        DaoFactory daoFactory = DaoFactory.getInstance();
        StationDao stationDao = daoFactory.createStationDao();
        stationDao.create(newStation);
        session.setAttribute("stationCreateComplete", bundle.getString("station.create.complete"));
        return "redirect:/listStation";

    }
}
