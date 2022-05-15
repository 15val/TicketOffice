package controller.command;

import javax.servlet.http.HttpServletRequest;


public class LogOutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        CommandUtility.logoutUser(request);
        return "redirect:/index.jsp";
    }
}
