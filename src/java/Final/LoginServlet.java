/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author apple
 */
@WebServlet(name = "Home", urlPatterns = {"/home"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private static HttpServletRequest setRequest(HttpServletRequest request, int user_id){
        List<CircleBean> circles = CircleDAO.search(user_id);
        List<LocationBean> locationsNear = new ArrayList<>();
        try {
            locationsNear = LocationDAO.search(user_id, 30);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        request.setAttribute("requestList", getRequestUsers(user_id));
        request.setAttribute("locationList", locationsNear);
        request.setAttribute("staticLocationList", getAllStaticLocation());
        request.setAttribute("circleList", circles);
        return request;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            UserBean user = new UserBean();
            user.setUserName(request.getParameter("un"));
            user.setPassword(request.getParameter("pw"));

            user = UserDAO.login(user);
            int user_id = user.getUser_id();
            
            
            if (user.isValid()) {
                request.setAttribute("servletName", "servletToJsp");
                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                request = setRequest(request, user_id);
                request.getRequestDispatcher("home.jsp").forward(request, response);
            } else {
                String message = "Unknown username/password. Please retry.";
                request.setAttribute("message", message);
                request.getRequestDispatcher("index.jsp").include(request, response);
            }
        } catch (Throwable theException) {
            System.out.println(theException);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (((HttpServletRequest) request).getSession().getAttribute("currentSessionUser") == null) {
            System.out.println(request.getContextPath() + " + " + request.getServletPath());
            response.sendRedirect("index.jsp");
        } else {    
            int user_id = ((UserBean) request.getSession().getAttribute("currentSessionUser")).getUser_id();
            request = setRequest(request, user_id);
            System.out.println("Loging using Get! " + user_id +";");
            request.getRequestDispatcher("home.jsp").include(request, response);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private static List<UserBean> getRequestUsers(int user_id) {
        List<UserBean> requestList = new ArrayList<>();
        try {
            requestList = FriendDAO.searchAllRequest(user_id);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return requestList;
    }
    
    private static List<LocationBean> getAllStaticLocation(){
        List<LocationBean> list = new ArrayList<>();
        try {
            return LocationDAO.getAll();
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
