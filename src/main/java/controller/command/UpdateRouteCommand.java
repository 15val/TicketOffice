package controller.command;

import model.dao.DaoFactory;
import model.dao.RouteDao;
import model.dao.StationDao;
import model.dao.TrainDao;
import model.entity.Route;
import model.entity.Station;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class UpdateRouteCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();

        int idRoute = Integer.parseInt(request.getParameter("idRoute"));
        session.setAttribute("idRoute", idRoute);

        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(((String)request.getSession().getAttribute("lang"))));

        if(request.getParameter("departure") == null || Objects.equals(request.getParameter("departure"), "")
                || request.getParameter("arrival") == null || Objects.equals(request.getParameter("arrival"), "")){
            session.setAttribute("routeUpdateValidationError", bundle.getString("route.no.date"));
            return "redirect:/editRoute";
        }

        int firstStationId = Integer.parseInt(request.getParameter("firstStation"));
        int lastStationId = Integer.parseInt(request.getParameter("lastStation"));


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm");

        LocalDateTime departure = LocalDateTime.parse(request.getParameter("departure"), formatter);
        LocalDateTime arrival = LocalDateTime.parse(request.getParameter("arrival"), formatter);
        int trainId = Integer.parseInt(request.getParameter("trainId"));

        if(request.getParameter("routePrice") == null || Objects.equals(request.getParameter("routePrice"), "")){
            session.setAttribute("routeUpdateValidationError", bundle.getString("route.price.invalid"));
            return "redirect:/editRoute";
        }

        BigInteger routePrice = BigInteger.valueOf(Long.parseLong(request.getParameter("routePrice")));


        if(firstStationId == lastStationId){
            session.setAttribute("routeUpdateValidationError", bundle.getString("route.same.stations"));
            return "redirect:/editRoute";
        }

        if(departure.isBefore(LocalDateTime.now())){
            session.setAttribute("routeUpdateValidationError", bundle.getString("route.departure.before.now"));
            return "redirect:/editRoute";
        }

        if(departure.isAfter(arrival)) {
            session.setAttribute("routeUpdateValidationError", bundle.getString("route.departure.after.arrival"));
            return "redirect:/editRoute";
        }

        if(departure.isAfter(LocalDateTime.now().plusYears(2)) || arrival.isAfter(LocalDateTime.now().plusYears(2))){
            session.setAttribute("routeUpdateValidationError", bundle.getString("route.two.years.later"));
            return "redirect:/editRoute";
        }

        if(routePrice.compareTo(BigInteger.ZERO) <= 0 || routePrice.compareTo(BigInteger.valueOf(100000)) > 0){
            session.setAttribute("routeUpdateValidationError", bundle.getString("route.price.invalid"));
            return "redirect:/editRoute";
        }

        DaoFactory daoFactory = DaoFactory.getInstance();
        StationDao stationDao = daoFactory.createStationDao();
        Station firstStation = stationDao.findById(firstStationId);
        DaoFactory daoFactory2 = DaoFactory.getInstance();
        StationDao stationDao2 = daoFactory2.createStationDao();
        Station lastStation = stationDao2.findById(lastStationId);

        String routeTime = "";
        long minutes = ChronoUnit.MINUTES.between(departure, arrival);
        long hours = ChronoUnit.HOURS.between(departure, arrival);
        if(hours < 10){
            routeTime += "0" + hours + ":";
        }
        else {
            routeTime += hours + ":";
        }
        if (minutes % 60 < 10){
            routeTime += "0" + minutes % 60;
        }
        else {
            routeTime += minutes % 60;
        }

        DaoFactory daoFactory3 = DaoFactory.getInstance();
        TrainDao trainDao = daoFactory3.createTrainDao();

        int freeSeats = trainDao.findById(trainId).getAllSeats();

        session.removeAttribute("idRoute");

        Route newRoute = new Route.Builder()
                .withRouteId(idRoute)
                .withFirstStation(firstStation)
                .withLastStation(lastStation)
                .withDeparture(departure)
                .withArrival(arrival)
                .withTrainId(trainId)
                .withRouteTime(routeTime)
                .withFreeSeats(freeSeats)
                .withPrice(routePrice)
                .build();

        DaoFactory daoFactory4 = DaoFactory.getInstance();
        RouteDao routeDao = daoFactory4.createRouteDao();
        routeDao.update(newRoute);

        session.setAttribute("routeUpdateComplete", bundle.getString("route.update.complete"));
        return "redirect:/adminListRoute";
    }
}
