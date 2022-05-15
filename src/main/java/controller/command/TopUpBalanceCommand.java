package controller.command;

import model.dao.DaoFactory;
import model.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

public class TopUpBalanceCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        HttpSession session = request.getSession();

        int idUser = Integer.parseInt(request.getParameter("idUser"));
        session.setAttribute("idUser", idUser);
        ResourceBundle bundle = ResourceBundle.getBundle("messages", Locale.forLanguageTag(((String)request.getSession().getAttribute("lang"))));
        BigInteger topUpValue;
        try {
            topUpValue = BigInteger.valueOf(Long.parseLong(request.getParameter("topUpValue")));
            if(topUpValue.compareTo(BigInteger.ZERO) <= 0){
                throw new NumberFormatException();
            }
        }
        catch (Exception e){
            session.setAttribute("topUpValueValidationError", bundle.getString("balance.top.up.illegal.value"));
            return "redirect:/getUserProfile";
        }
        System.out.println(idUser);
        System.out.println(topUpValue);

        DaoFactory daoFactory = DaoFactory.getInstance();
        UserDao userDao = daoFactory.createUserDao();
        userDao.topUpBalance(idUser, topUpValue);

        session.setAttribute("topUpComplete", bundle.getString("balance.top.up.successful"));
        return "redirect:/getUserProfile";
    }
}
