/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package apicontrollers;

import com.google.gson.Gson;
import dao.SubTodoDAO;
import dao.TodoListDAO;
import models.SubTodo;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 *
 * @author Sang
 */
@WebServlet(name="SubTodoServlet", urlPatterns={"/api/subtodo"})
public class SubTodoServlet extends HttpServlet {
   
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
        
        String user = request.getParameter("user");
        String todoId = request.getParameter("id");
        
        ArrayList<SubTodo> subTodo = SubTodoDAO.getSubTodoList(todoId);
        Gson gson = new Gson();
        
        String json = gson.toJson(subTodo);

        printWriter.print(json);
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
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        
        if(SubTodoDAO.createSubTodo(id, title)) {
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
        String title = request.getParameter("title");
        String isComplete = request.getParameter("complete");
        
        SubTodo stodo = new SubTodo();
        
        if(isComplete != null && !isComplete.isEmpty()) {
            stodo.setIsCompleted(Boolean.parseBoolean(isComplete));
        }
        
        stodo.setId(id);
        stodo.setTitle(title);
        
        if(SubTodoDAO.updateSubtodo(stodo, type)) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        String id = request.getParameter("id");
        
        if(SubTodoDAO.deleteSubTodo(id)) {
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
