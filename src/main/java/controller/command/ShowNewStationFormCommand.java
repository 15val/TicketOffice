package controller.command;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public class ShowNewStationFormCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        return "/WEB-INF/admin/createStation.jsp";
    }
}
