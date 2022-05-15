package controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import model.dao.DaoFactory;
import model.dao.UserDao;
import model.entity.User;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegisterCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        User.ROLE role = User.ROLE.valueOf(request.getParameter("role"));



        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(((String)request.getSession().getAttribute("lang"))));

        if(CommandUtility.validation(name, bundle.getString("name.regexp"))){
            System.out.println("name valid");
        }
        else{
            System.out.println("name invalid");
            session.setAttribute("validationError", bundle.getString("invalid.name"));
            return "/register.jsp";
        }
        if(CommandUtility.validation(nickname, bundle.getString("nickname.regexp"))){
            System.out.println("nickname valid");
        }
        else{
            System.out.println("nickname invalid");
            session.setAttribute("validationError", bundle.getString("invalid.nickname"));
            return "/register.jsp";
        }
        if(CommandUtility.validation(password, bundle.getString("password.regexp"))){
            System.out.println("password valid");
        }
        else{
            System.out.println("password invalid");
            session.setAttribute("validationError", bundle.getString("invalid.password"));
            return "/register.jsp";
        }
        if(CommandUtility.validation(email, bundle.getString("email.regexp"))){
            System.out.println("email valid");
        }
        else{
            System.out.println("email invalid");
            session.setAttribute("validationError", bundle.getString("invalid.email"));
            return "/register.jsp";
        }

            User newUser = new User.Builder()
                    .withName(name)
                    .withNickname(nickname)
                    .withPassword(password)
                    .withEmail(email)
                    .withRole(role)
                    .build();
try {
    DaoFactory daoFactory = DaoFactory.getInstance();
    UserDao userDao = daoFactory.createUserDao();
    userDao.create(newUser);
    session.setAttribute("registrationComplete", bundle.getString("register.complete"));
    return "/login.jsp";
}catch (SQLException ex){
    session.setAttribute("validationError", bundle.getString("nickname.exists"));
    return "/register.jsp";
}


    }
}
