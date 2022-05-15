package controller.command;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetUserProfileCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.createUserDao();
        User userProfile = userDao.findById((Integer) request.getSession().getAttribute("loggedUserId"));
        List<User> list = new ArrayList<>();
        list.add(userProfile);
        request.setAttribute("userProfile", list);
        return "/WEB-INF/user/profile.jsp";
    }
}
