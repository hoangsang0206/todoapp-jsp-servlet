/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package com.stodo.controllers;

import com.stodo.models.Todo;
import com.stodo.models.Note;
import com.stodo.dao.NotesDAO;
import com.stodo.dao.TodoListDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
/**
 *
 * @author Sang
 */
@WebServlet(name="DashboardServlet", urlPatterns={"/dashboard"})
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
        String username = "admin";

        NotesDAO notesDAO = new NotesDAO();

        ArrayList<Todo> todoList = TodoListDAO.getTodoList(username);
        ArrayList<Note> notes = notesDAO.getNotes(username);

        //--
        ArrayList<Todo> monthTodo = new ArrayList<>();
        ArrayList<Todo> weekTodo = new ArrayList<>();
        ArrayList<Todo> dayTodo = new ArrayList<>();

        int monthCompleted = 0, weekCompleted = 0, dayCompleted = 0;

        LocalDateTime now = LocalDateTime.now();
        for(Todo todo : todoList) {
            LocalDateTime createDate = todo.getDateCreate();;

            if(createDate != null && createDate.getMonth() == now.getMonth()) {
                monthTodo.add(todo);

                if(now.truncatedTo(ChronoUnit.DAYS).isEqual(createDate.truncatedTo(ChronoUnit.DAYS))) {
                    weekTodo.add(todo);


                    if(now.toLocalDate().isEqual(createDate.toLocalDate())) {
                        dayTodo.add(todo);
                    }
                }
            }
        }

        request.setAttribute("TodayList", todoList);
        request.setAttribute("Notes", notes);
//            request.setAttribute("MonthTodo", monthTodo);
//            request.setAttribute("WeekTodo", weekTodo);
//            request.setAttribute("DayTodo", dayTodo);
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
        processRequest(request, response);
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
