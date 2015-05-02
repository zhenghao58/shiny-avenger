/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.io.IOException;
import java.io.PrintWriter;
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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            UserBean user = new UserBean();
            user.setUserName(request.getParameter("un"));
            user.setPassword(request.getParameter("pw"));

            user = UserDAO.login(user);
            if (user.isValid()) {
                request.setAttribute("servletName", "servletToJsp");
                HttpSession session = request.getSession(true);
                session.setAttribute("currentSessionUser", user);
                request.setAttribute("requestList", getRequestUsers(user.getUser_id()));
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
            request.setAttribute("requestList", getRequestUsers(user_id));
            System.out.println("Loging using Get! " + user_id);
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

//
//            UserBean ub1 = new UserBean();
//            ub1.setUser_id(6);
//            ub1.setName("David");
//            UserBean ub2 = new UserBean();
//            ub2.setUser_id(8);
//            ub2.setName("Adam");
//            UserBean ub3 = new UserBean();
//            ub3.setUser_id(4);
//            ub3.setName("Tom");
//            UserBean ub4 = new UserBean();
//            ub4.setUser_id(3);
//            ub4.setName("Jack");
        List<UserBean> requestList = new ArrayList<>();
        try {
            //            requestList.add(ub1);
//            requestList.add(ub2);
//            requestList.add(ub3);
//            requestList.add(ub4);
//            return requestList;
            requestList = FriendDAO.searchAllRequest(user_id);
        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(requestList);
        return requestList;
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
