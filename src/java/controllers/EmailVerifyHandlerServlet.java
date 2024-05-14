/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Account;
import utils.RandomString;
import utils.SendEmail;

/**
 *
 * @author Sang
 */
@WebServlet(name="EmailVerifyHandlerServlet", urlPatterns={"/verify"})
public class EmailVerifyHandlerServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

    } 

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String key = request.getParameter("key");
        String username = request.getParameter("u");
        if(key == null || key.isEmpty() || username == null || username.isEmpty()) {
            return;
        }
        
        if(AccountDAO.verifyEmail(username, key)) {
            response.sendRedirect("./setting");
        } else {
            response.sendRedirect("./error");
        }
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Account account = AccountDAO.getLoggedInUser(request);
        if(account == null) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        };
        if(AccountDAO.getEmailVerificationStatus(account.getUsername())) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        };
            
        String key = RandomString.random(40);
                    
        if(AccountDAO.setVerifycationKey(account.getUsername(), key)) {
            
            String emailHtml = "";
            emailHtml += "<div style=\"width: 500px; max-width: calc(100% - 50px)\">";
            emailHtml += "<img src=\"https://lhswebstorage.blob.core.windows.net/todoweb/email-images/2909200.jpg\" style=\"width: 100%\">";
            emailHtml += "<h3>XÁC NHẬN ĐỊA CHỈ EMAIL</h3>";
            emailHtml += "<p>Vui lòng nhấn vào bút bên dưới để xác nhận địa chỉ email của bạn</p>";
            emailHtml += "<a href=\"http://localhost:8080/todoapp/verify?key=" + key + "&u=" + account.getUsername() + "\">Xác nhận</a>";
            emailHtml += "</div>";
            
            SendEmail.sendEmail(account.getEmail(), emailHtml);
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
