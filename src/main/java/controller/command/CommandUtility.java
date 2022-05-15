package controller.command;

import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandUtility {

    static boolean checkUserIsLogged(HttpServletRequest request, String nickName){
        User loggedUser = (User) request.getSession().getServletContext()
                .getAttribute("loggedUser");

        if(loggedUser != null){
            if(loggedUser.getNickname() != null) {
                if (loggedUser.getNickname().equals(nickName)) {
                    return true;
                }
            }
        }
        return false;
    }

    static Optional<User> getUser (String nickname){
        DaoFactory daoFactory = DaoFactory.getInstance();
        Optional<User> result = Optional.empty();
        try(UserDao userDao = daoFactory.createUserDao()){
            result = userDao.findByNickname(nickname);
        }

        return result;
    }

    static void setUser(HttpServletRequest request, User user) {
        HttpSession session = request.getSession();
        session.setAttribute("loggedUser", user);
        session.setAttribute("loggedUserId", user.getId());
    }

    static void logoutUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute("loggedUser");
        session.removeAttribute("loggedUserId");
        session.invalidate();
    }

    static boolean validation(String str, String regex){
        if(str.isEmpty()){
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if(matcher.matches()){
            return true;
        }
        else return false;
    }

}
