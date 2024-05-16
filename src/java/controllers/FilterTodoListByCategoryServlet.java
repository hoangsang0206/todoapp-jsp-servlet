/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import dao.AccountDAO;
import dao.CategoriesDAO;
import dao.TodoListDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import models.Account;
import models.Todo;

/**
 *
 * @author Sang
 */
@WebServlet(name="FilterTodoListByCategoryServlet", urlPatterns={"/category"})
public class FilterTodoListByCategoryServlet extends HttpServlet {
   
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
        String id = request.getParameter("cid");
        String sort = request.getParameter("sort");
        if(id == null || id.isEmpty()) {
            response.sendRedirect("./error");
            return;
        }
        
        Account account = AccountDAO.getLoggedInUser(request);
        ArrayList<Todo> todoList = TodoListDAO.getTodoListByCategory(id, account.getUsername());

        if(sort != null && !sort.isBlank()) {
            todoList = TodoListDAO.sortTodoList(todoList, sort);
        }
        
        ArrayList<Todo> upcomingTodoList, todayTodoList, weekTodoList, beforeWeekTodoList;
        todayTodoList = TodoListDAO.filterTodayTodoList(todoList);
        upcomingTodoList = TodoListDAO.filterUpcomingTodo(todayTodoList);
        weekTodoList = TodoListDAO.filterWeekTodoList(todoList);
        beforeWeekTodoList = TodoListDAO.filterBeforeWeekTodoList(todoList);
        
        request.setAttribute("ActiveNav", CategoriesDAO.getCategory(id).getId());
        request.setAttribute("Title", CategoriesDAO.getCategory(id).getCateName());
        request.setAttribute("Upcoming", upcomingTodoList);
        request.setAttribute("TodayTodoList", todayTodoList);
        request.setAttribute("WeekTodoList", weekTodoList);
        request.setAttribute("BeforeWeekTodoList", beforeWeekTodoList);
        request.getRequestDispatcher("categorytodolist.jsp").forward(request, response);
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
