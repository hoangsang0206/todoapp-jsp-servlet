/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package apicontrollers;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AccountDAO;
import dao.TodoListDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import utils.LocalDateTimeAdapter;
import models.Todo;
import models.Account;
import models.Category;

/**
 *
 * @author Sang
 */
@WebServlet(name="TodoServlet", urlPatterns={"/api/todo"})
public class TodoServlet extends HttpServlet {
   
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
        PrintWriter printWriter = response.getWriter();
        
        response.setContentType("application/json");
        
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
        
        Account account = AccountDAO.getLoggedInUser(request);
        String id = request.getParameter("id");
        String type = request.getParameter("t");
        
        if(id != null && !id.isEmpty()) {
            Todo todo = TodoListDAO.getTodo(id, account.getUsername());
            printWriter.print(gson.toJson(todo));
            
        } else if(type != null && !type.isEmpty() && type.equals("day")) {
            ArrayList<Todo> todoList = TodoListDAO.getTodayTodoList(account.getUsername());
            printWriter.print(gson.toJson(todoList));
            
        } else if(type != null && !type.isEmpty() && type.equals("upcoming")) {
            ArrayList<Todo> todoList = TodoListDAO.getTodayTodoList(account.getUsername());
            printWriter.print(gson.toJson(TodoListDAO.filterUpcomingTodo(todoList)));
            
        } else if(type != null && !type.isEmpty() && type.equals("daterange")) {
            String startDate= request.getParameter("s");
            String endDate = request.getParameter("e");
            if(startDate == null || endDate == null || startDate.isEmpty() || endDate.isEmpty()) {
                return;
            }
            
            ArrayList<Todo> todoList = TodoListDAO.getTodoListByDateRange(LocalDate.parse(startDate).atStartOfDay(), 
                    LocalDate.parse(endDate).atStartOfDay(), account.getUsername());
            printWriter.print(gson.toJson(todoList));
            
        } else {
            ArrayList<Todo> todoList = TodoListDAO.getTodoList(account.getUsername());
            printWriter.print(gson.toJson(todoList));
            
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
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String date = request.getParameter("date");
        String listCate = request.getParameter("categories");
        
        LocalDateTime dateTime = LocalDateTime.parse(date);
        
        Account account = AccountDAO.getLoggedInUser(request);
        
        Todo todo = new Todo();
        todo.setTitle(title);
        todo.setDescription(description);
        todo.setDateCreate(LocalDateTime.now());
        todo.setDateToComplete(dateTime);
        
        ArrayList<Category> categories = new ArrayList<>();
        if(listCate != null && !listCate.isEmpty()) {
            String[] values = listCate.split(",");
            for(String value : values) {
                if(value != null && !value.isEmpty()) {
                    Category category = new Category();
                    category.setId(value);
                    
                    categories.add(category);
                }
            }
            
            todo.setCategories(categories);
        }
        
        if(TodoListDAO.createTodo(todo, account.getUsername())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }        
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        String type = request.getParameter("t");
        String id = request.getParameter("id");
        String status = request.getParameter("status");
        
        Account account = AccountDAO.getLoggedInUser(request);
        
        Todo todo = new Todo();
        todo.setId(id);
        if(type != null && type.equals("status")) {
            todo.setIsCompleted(status.equals("completed"));
        } else {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String date = request.getParameter("date");
            String listCate = request.getParameter("categories");
            LocalDateTime dateTime = LocalDateTime.parse(date);
                    
            todo.setTitle(title);
            todo.setDescription(description);
            todo.setDateCreate(LocalDateTime.now());
            todo.setDateToComplete(dateTime);
            todo.setIsCompleted(status.equals("completed"));
            
            ArrayList<Category> categories = new ArrayList<>();
            if(listCate != null && !listCate.isEmpty()) {
                String[] values = listCate.split(",");
                for(String value : values) {
                    if(value != null && !value.isEmpty()) {
                        Category category = new Category();
                        category.setId(value);

                        categories.add(category);
                    }
                }

                todo.setCategories(categories);
            }
        }
             
        if(TodoListDAO.updateTodo(type, todo, account.getUsername())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }        
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String id = request.getParameter("id");
        Account account = AccountDAO.getLoggedInUser(request);
        
        if(TodoListDAO.deleteTodo(id, account.getUsername())) {
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
