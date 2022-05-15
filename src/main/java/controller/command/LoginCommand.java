package controller.command;

import model.entity.User;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");

        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(((String)request.getSession().getAttribute("lang"))));

        if (nickname == null || nickname.equals("") || password == null || password.equals("")) {
            request.getSession().setAttribute("loginError", bundle.getString("wrong.data"));
            return "/login.jsp";
        }


        if (CommandUtility.checkUserIsLogged(request, nickname)) {
            CommandUtility.logoutUser(request);
            return "/login.jsp";
        }

        Optional<User> optionalUser = CommandUtility.getUser(nickname);
        User currentUser = new User();
        if (optionalUser.isPresent()) {
            currentUser = optionalUser.get();
            if (currentUser.getNickname().equals(nickname) && currentUser.getPassword().equals(password)) {
                CommandUtility.setUser(request, currentUser);

                if (currentUser.getRole() == User.ROLE.ADMIN) {

                    return "redirect:/admin/showAllRoutes.jsp";
                }
                if (currentUser.getRole() == User.ROLE.USER) {

                    return "redirect:/user/userbasis.jsp";
                } else {
                    //для гостя
                    return "/login.jsp";
                }
            }
             else {
                request.getSession().setAttribute("loginError", bundle.getString("wrong.data"));
            return "/login.jsp";
            }
        }
        request.getSession().setAttribute("loginError", bundle.getString("wrong.data"));
        return "/login.jsp";
    }
}




