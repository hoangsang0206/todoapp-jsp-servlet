/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package apicontrollers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.AccountDAO;
import dao.CategoriesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import models.Account;
import models.Category;
import utils.LocalDateTimeAdapter;

/**
 *
 * @author Sang
 */
@WebServlet(name="Categories", urlPatterns={"/api/categories"})
public class CategoriesServlet extends HttpServlet {
   
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
        PrintWriter printWriter = response.getWriter();
        
        response.setContentType("application/json");
        
        String id = request.getParameter("id");
        Account account = AccountDAO.getLoggedInUser(request);
        
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        
        if(id != null && !id.isEmpty()) {
            Category category = CategoriesDAO.getCategory(id);
            printWriter.print(gson.toJson(category));
        } else {
            ArrayList<Category> categories = CategoriesDAO.getUserCategories(account.getUsername());
            printWriter.print(gson.toJson(categories));
        }
        
        printWriter.flush();
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
        String name = request.getParameter("name");
        String color = URLDecoder.decode(request.getParameter("color"), "UTF-8");
        
        Account account = AccountDAO.getLoggedInUser(request);
        
        Category category = new Category();
        category.setCateName(name);
        category.setIconColor(color);
        category.setDateCreate(LocalDateTime.now());
        
        if(CategoriesDAO.createCategory(category, account.getUsername())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
    throws ServletException, IOException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String color = URLDecoder.decode(request.getParameter("color"), "UTF-8");
        
        Account account = AccountDAO.getLoggedInUser(request);
        
        Category category = new Category();
        category.setId(id);
        category.setCateName(name);
        category.setIconColor(color);
        
        if(CategoriesDAO.updateCategory(category, account.getUsername())) {
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
        
        if(CategoriesDAO.deleteCategory(id, account.getUsername())) {
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
