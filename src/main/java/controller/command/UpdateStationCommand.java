package controller.command;

import model.dao.DaoFactory;
import model.dao.StationDao;
import model.entity.Station;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateStationCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();
        int id = Integer.parseInt(request.getParameter("id"));
        String stationNameUA = request.getParameter("stationNameUA");
        String stationNameEN = request.getParameter("stationNameEN");
        session.setAttribute("id", id);

        String stationNameUaRegex = "^[А-ЩЬЮЯҐІЇЄ][a-щьюяґіїє’]{0,20}[-]{0,1}[А-ЩЬЮЯҐІЇЄ]{0,1}[а-щьюяґіїє’]{0,20}$";
        String stationNameEnRegex = "^[A-Z][a-z']{0,20}[-]{0,1}[A-Z]{0,1}[a-z']{0,20}$";

        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(((String)request.getSession().getAttribute("lang"))));


        if(CommandUtility.validation(stationNameUA, stationNameUaRegex)){
            System.out.println("stationNameUA valid");
        }
        else{
            System.out.println("stationNameUA invalid");
            session.setAttribute("stationUpdateValidationError", bundle.getString("station.name.ua.invalid"));
            return "redirect:/editStation";
        }
        if(CommandUtility.validation(stationNameEN, stationNameEnRegex)){
            System.out.println("stationNameEN valid");
        }
        else{
            System.out.println("stationNameEN invalid");
            session.setAttribute("stationUpdateValidationError", bundle.getString("station.name.en.invalid"));
            return "redirect:/editStation";
        }

        session.removeAttribute("id");
        Station updatedStation = new Station.Builder()
                .withId(id)
                .withNameUA(stationNameUA)
                .withNameEN(stationNameEN)
                .build();

        DaoFactory daoFactory = DaoFactory.getInstance();
        StationDao stationDao = daoFactory.createStationDao();
        stationDao.update(updatedStation);
        session.setAttribute("stationUpdateComplete", bundle.getString("station.update.complete"));
        return "redirect:/listStation";
    }
}
