package controller.command;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

public interface Command {
    String execute(HttpServletRequest request) throws SQLException;
}
