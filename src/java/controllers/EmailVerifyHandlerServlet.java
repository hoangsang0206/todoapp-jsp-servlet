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
            String emailHtml = "<table style=\"width: 100%; text-align: center;\">\n" +
                "<tr>\n" +
                "<td style=\"width: 35%\"></td>\n" +
                "<td>\n" +
                "<div style=\"width: 500px; max-width: 100%; background-color: #f8f8f8; padding: 30px; border-radius: 10px;\">\n" +
                "<img src=\"https://lhswebstorage.blob.core.windows.net/todoweb/email-images/2909200.jpg\" style=\"width: 100%; display: block; margin-bottom: 20px;\">\n" +
                "<h2 style=\"color: #000; margin-top: 0;\">XÁC NHẬN ĐỊA CHỈ EMAIL</h2>\n" +
                "<p style=\"color: #000; margin-bottom: 20px;\">Vui lòng nhấn vào nút bên dưới để xác nhận địa chỉ email của bạn</p>\n" +
                "<a href=\"http://localhost:8080/todoapp/verify?key=" + key + "&u=" + account.getUsername() + "\" style=\"padding: 10px 25px; border-radius: 6px; background-color: blueviolet; color: #fff; text-decoration: none; font-size: 18px; font-weight: 500;\">Xác nhận</a>\n" +
                "</div>\n" +
                "</td>\n" +
                "<td style=\"width: 35%\"></td>\n" +
                "</tr>\n" +
                "</table>";
            
            if(SendEmail.sendEmail(account.getEmail(), emailHtml)) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
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
