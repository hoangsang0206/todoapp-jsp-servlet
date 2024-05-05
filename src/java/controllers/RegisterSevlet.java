/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.AccountDAO;
import dao.CategoriesDAO;
import models.Account;
import models.Category;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 *
 * @author Sang
 */
@WebServlet(name="RegisterSevlet", urlPatterns={"/register"})
public class RegisterSevlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterSevlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterSevlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        request.getRequestDispatcher("register.jsp").forward(request, response);
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
        request.removeAttribute("Error");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmpassword");
        
        if(username == null || password == null || confirmPassword == null ||
                username.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            String error = "Vui lòng nhập đầu đủ thông tin.";
            setFormError(error, request, response);
            return;
        } 
        
        if(!password.equals(confirmPassword)) {
            String error = "Xác nhận mật khẩu không đúng.";
            setFormError(error, request, response);
            return;
        }
        
        if(AccountDAO.existUser(username)) {
            String error = "Tài khoản này đã tồn tại.";
            setFormError(error, request, response);
            return;
        }
        
        
        boolean result = AccountDAO.createAccount(username, AccountDAO.hashPassword(password));
        if(result) {
            
            Category category1 = new Category(null, "Công việc", "#e30019", LocalDateTime.now(), null, null);
            Category category2 = new Category(null, "Giải trí", "#052ffe", LocalDateTime.now(), null, null);
            
            CategoriesDAO.createCategory(category1, username);
            CategoriesDAO.createCategory(category2, username);
            
            Account account = AccountDAO.getUser(username, AccountDAO.hashPassword(password));
            HttpSession session = request.getSession();
            session.setAttribute("user", account);
            response.sendRedirect("dashboard");
        }
    }
    
    private void setFormError(String error, HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
        request.setAttribute("Error", error);
        request.getRequestDispatcher("register.jsp").forward(request, response);
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
