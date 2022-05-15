package controller.command;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUsersCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        int currentPage;
        int recordsPerPage;

        if(request.getParameter("currentPage") != null) {
             currentPage = Integer.parseInt(request.getParameter("currentPage"));
        }
        else {
            currentPage = Integer.parseInt(String.valueOf(session.getAttribute("currentPage")));
        }

        if(request.getParameter("recordsPerPage") != null) {
            recordsPerPage = Integer.parseInt(request.getParameter("recordsPerPage"));
        }
        else {
            recordsPerPage = Integer.parseInt(String.valueOf(session.getAttribute("recordsPerPage")));
        }

        session.setAttribute("currentPage", currentPage);
        session.setAttribute("recordsPerPage", recordsPerPage);

            DaoFactory daoFactory = DaoFactory.getInstance();
            UserDao userDao = daoFactory.createUserDao();
            List<User> userList = userDao.findUsers(currentPage, recordsPerPage);
            session.setAttribute("userList", userList);
            int rows = userDao.getNumberOfRows();
            int numberOfPages = rows/recordsPerPage;
            if(numberOfPages % recordsPerPage > 0){
                numberOfPages++;
            }

            session.setAttribute("numberOfPages", numberOfPages);
            session.setAttribute("currentPage", currentPage);
            session.setAttribute("recordsPerPage", recordsPerPage);
        return "/showAllUserTable.jsp";
    }
}
