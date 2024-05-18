/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package apicontrollers;

import dao.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Account;

/**
 *
 * @author Sang
 */
@WebServlet(name="AccountServlet", urlPatterns={"/api/account"})
public class AccountServlet extends HttpServlet {
   
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
        String type = request.getParameter("t");
        
        switch (type) {
            case "changepass":
            {
                String oldPass = request.getParameter("oldpass");
                String newPass = request.getParameter("newpass");
                String newPassConf = request.getParameter("newpassconf");
                
                if(oldPass == null || oldPass.isEmpty() 
                        || newPass == null || newPass.isEmpty()
                        || newPassConf == null || newPassConf.isEmpty()) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().print("Mật khẩu không được để trống");
                    break;
                }
                
                if(!newPass.equals(newPassConf)) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().print("Xác nhận mật khẩu không đúng");
                    break;
                }
                
                Account account = AccountDAO.getLoggedInUser(request);
                
                if(!AccountDAO.checkPassword(oldPass, account.getUsername())) {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().print("Mật khẩu cũ không chính xác");
                    break;
                }
                
                if(AccountDAO.changePassword(account.getUsername(), newPass)) {
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.getWriter().print("Không thể đổi mật khẩu");
                }
                
                break;
            }
            case "changeinfo":
            {
                String fullName = request.getParameter("name");
                String email = request.getParameter("email");
                Account account = AccountDAO.getLoggedInUser(request);
                
                if(AccountDAO.updateInfomation(fullName, email, account.getUsername())) {
                    account = AccountDAO.getUser(account.getUsername(), account.getPasswordHash());
                    HttpSession session = request.getSession(false);
                    session.removeAttribute("user");
                    session.setAttribute("user", account);
                    response.setStatus(HttpServletResponse.SC_OK);
                } else {
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
                break;
            }
            default:
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                break;
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
