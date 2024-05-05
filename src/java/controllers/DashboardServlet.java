/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.AccountDAO;
import models.Todo;
import models.Note;
import dao.NotesDAO;
import dao.TodoListDAO;
import models.Account;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 *
 * @author Sang
 */
@WebServlet(name="DashboardServlet", urlPatterns={"/dashboard", ""})
public class DashboardServlet extends HttpServlet {
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
        if(!AccountDAO.checkLogin(request)) {
            response.sendRedirect("login");
            return;
        }
        
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("user");
        
        ArrayList<Todo> todoList = new ArrayList<>();
        todoList = TodoListDAO.getTodoList(account.getUsername());
        ArrayList<Note> notes = new ArrayList<>();
        notes = NotesDAO.getNotes(account.getUsername());
        
        ArrayList<Todo> todayTodoList = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        for(Todo todo : todoList) {
            LocalDateTime createDate = todo.getDateCreate();;

            if(createDate != null) {
                if(now.toLocalDate().isEqual(createDate.toLocalDate())) {
                    todayTodoList.add(todo);
                }
            }
        }

        request.setAttribute("TodayList", todayTodoList);
        request.setAttribute("Notes", notes);
        request.setAttribute("ActiveNav", "dashboard");
        
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
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
        if(!AccountDAO.checkLogin(request)) {
            response.sendRedirect("login");
            return;
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
