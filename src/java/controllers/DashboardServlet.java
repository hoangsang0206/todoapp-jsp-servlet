/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import models.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sang
 */
@WebServlet(name="DashboardServlet", urlPatterns={"/dashboard"})
public class DashboardServlet extends HttpServlet {
    JDBCConnect connect = new JDBCConnect();
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
        try {
            connect.getConnection();
            String sqlTodo = "Select * From TodoList Order By dateCreate ASC";
            String sqlNote = "Select * From Notes Order By dateCreate ASC";
            
            ResultSet rsTodo = connect.excuteQuery(sqlTodo);
            ResultSet rsNote = connect.excuteQuery(sqlNote);

            ArrayList<Todo> todoList = new ArrayList<>();
            ArrayList<Note> notes = new ArrayList<>();
        
            while(rsTodo.next()) {
                Todo todo = new Todo();
                todo.setId(rsTodo.getString("id"));
                todo.setTitle(rsTodo.getString("title"));
                todo.setDescription(rsTodo.getString("description"));
                todo.setUsername(rsTodo.getString("username"));
                
                todo.setDateCreate(rsTodo.getTimestamp("dateCreate") != null 
                        ? rsTodo.getTimestamp("dateCreate").toLocalDateTime() : LocalDateTime.now());
                todo.setDateCompleted(rsTodo.getTimestamp("dateCompleted") != null 
                        ? rsTodo.getTimestamp("dateCompleted").toLocalDateTime() : null);
                
                //Get sub task in todo
                ArrayList<SubTodo> subTodoList = new ArrayList<>();
                String sqlSubTodo  = "Select * From Sub_TodoList Where todoID = '" + todo.getId() + "'";
                ResultSet rsSubTodo = connect.excuteQuery(sqlSubTodo);
                while(rsSubTodo.next()) {
                    SubTodo subTodo = new SubTodo();
                    subTodo.setId(rsSubTodo.getString("id"));
                    subTodo.setTodo(todo);
                    subTodo.setTitle(rsSubTodo.getString("title"));
                    subTodo.setIsCompleted(rsSubTodo.getBoolean("is_completed"));
                    
                    subTodoList.add(subTodo);
                }
                
                todo.setSubTodoList(subTodoList);
                
                todoList.add(todo);
            }
            
            while(rsNote.next()) {
                Note note = new Note();
                note.setId(rsNote.getString("id"));
                note.setContent(rsNote.getString("content"));
                note.setUsername("username");
                note.setDateCreate(rsNote.getTimestamp("dateCreate") != null 
                        ? rsNote.getTimestamp("dateCreate").toLocalDateTime() : LocalDateTime.now());
                
                notes.add(note);
            }
            
            connect.close();
            
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
            
            request.setAttribute("TodayList", dayTodo);
            request.setAttribute("Notes", notes);
//            request.setAttribute("MonthTodo", monthTodo);
//            request.setAttribute("WeekTodo", weekTodo);
//            request.setAttribute("DayTodo", dayTodo);
            request.getRequestDispatcher("dashboard.jsp").forward(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(DashboardServlet.class.getName()).log(Level.SEVERE, null, ex);
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
