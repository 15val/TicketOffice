package controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import model.entity.User;

public class RedirectFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();


        User currentUser = (User) session.getAttribute("loggedUser");


        if(request.getRequestURI().contains("showAllUsers")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/WEB-INF/admin/showAllUsers.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("GetUsers")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/WEB-INF/admin/showAllUserTable.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }
        if(request.getRequestURI().contains("userbasis")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.USER) {
                request.getRequestDispatcher("/WEB-INF/user/userbasis.jsp").forward(request, servletResponse);
           }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("profile")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.USER) {
                request.getRequestDispatcher("/WEB-INF/user/profile.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("myTickets")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.USER) {
                request.getRequestDispatcher("/WEB-INF/user/myTickets.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("showAllStations")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                    request.getRequestDispatcher("/WEB-INF/admin/showAllStations.jsp").forward(request, servletResponse);
              }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("routeDetails.jsp")){
            if(request.getParameter("idRoute") != null) {
                request.getRequestDispatcher("/routeDetails.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/index.jsp");
            }
        }
        if(request.getRequestURI().contains("listStation")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/listStation").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }




        if(request.getRequestURI().contains("getUserProfile")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.USER) {
                request.getRequestDispatcher("/getUserProfile").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }
        if(request.getRequestURI().contains("newStation")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/newStation").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("editStation")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                String path = request.getRequestURI();
                path = path.substring(path.indexOf("/editStation"));
                request.getRequestDispatcher(path).forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("createStation")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/WEB-INF/admin/createStation.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("showAllRoutes")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/WEB-INF/admin/showAllRoutes.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }


        if(request.getRequestURI().contains("newRoute")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/newRoute").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }
        if(request.getRequestURI().contains("editRoute")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                String path = request.getRequestURI();
                path = path.substring(path.indexOf("/editRoute"));
                request.getRequestDispatcher(path).forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        if(request.getRequestURI().contains("createRoute")){
            if(currentUser != null && currentUser.getRole() == User.ROLE.ADMIN) {
                request.getRequestDispatcher("/WEB-INF/admin/createRoute.jsp").forward(request, servletResponse);
            }
            else {
                response.sendRedirect("/ticketoffice/login.jsp");
            }
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
