/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Final;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author 睿
 */
@WebServlet(name = "PostMessageServelet", urlPatterns = {"/api/postMessage"})
@MultipartConfig
public class PostMessageServelet extends HttpServlet {

    public static final String UPLOAD_DIR = "/Users/apple/WebStorage/Tourini/images";

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

        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">        
        String fileName = filePart.getSubmittedFileName();
        InputStream fileContent;
        String privacy = request.getParameter("privacy").toLowerCase();
        int circle_id = Integer.parseInt(request.getParameter("circle_id"));
        String text = request.getParameter("text").length()!=0 ? request.getParameter("text"): "I posted a picture." ;
        int requestId = Integer.parseInt(request.getParameter("user_id"));
        int location_id = Integer.parseInt(request.getParameter("location"));
        String message = "false";
        boolean messageResult = false;
        int photoResult = 0;
        if (fileName.length() == 0) {
            System.out.println("no file");
            MessageBean bean = new MessageBean();
            bean.setText(text);
            bean.setUser_id(requestId);
            bean.setPrivacy(privacy);
            bean.setCircle_id(circle_id);
            bean.setLocation_id(location_id);
            try {
                messageResult = MessageDAO.post(bean);
            } catch (SQLException ex) {
                Logger.getLogger(PostMessageServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (messageResult) {
                message = "true";
            }
        } else {
            fileContent = filePart.getInputStream();
            File fileDir = new File(UPLOAD_DIR + File.separator + request.getParameter("user_id"));
            if (!fileDir.exists()) {
                fileDir.mkdirs();
                System.out.println("Create directory!");
            }

            try {
                photoResult = PhotoDAO.post(text, requestId, location_id, privacy, circle_id);
                if (photoResult != 0) {
                    File file = new File(fileDir, Integer.toString(photoResult)+".jpg");
                    Files.copy(fileContent, file.toPath());
                    String imageFileName = file.getName();
                    System.out.println(imageFileName);
                    message = "true";

                }
            } catch (SQLException ex) {
                Logger.getLogger(PostMessageServelet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(message);
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
