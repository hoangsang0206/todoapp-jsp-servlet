/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stodo.dao;

import com.stodo.models.Category;
import com.stodo.models.JDBCConnect;
import com.stodo.models.Todo;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sang
 */
public class CategoriesDAO {
    
    private static ArrayList<Category> getCategoriesBySQL(String sql) {
        try {
            ArrayList<Category> categories = new ArrayList<>();
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ResultSet rs = connect.excuteQuery(sql);
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setCateName(rs.getString("cateName"));
                category.setUsername(rs.getString("username"));
                category.setIconSVG(rs.getString("iconSVG"));
                
                // Get TodoList in category without category list
                ArrayList<Todo> todoList = new ArrayList<>();
                String sqlTodo = "SELECT TodoList.* " +
                    "FROM Categories " +
                    "INNER JOIN Todo_Categories ON Categories.id = Todo_Categories.cateID " +
                    "INNER JOIN TodoList ON Todo_Categories.todoID = TodoList.id " +
                    "WHERE Categories.id = '" + rs.getString("id") + "'";
                ResultSet rsTodo = connect.excuteQuery(sqlTodo);
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
                    
                    todoList.add(todo);
                }
                
                
                category.setTodoList(todoList);
                
                categories.add(category);
            }
            
            return categories;
                
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }
    
    
    public static ArrayList<Category> getUserCategories(String username) {
        String sql = "Select * From Categories Where username = '" + username + "'";
        
        return getCategoriesBySQL(sql);
    }
    
    public static ArrayList<Category> getTodoCategories(String todoId) {
        String sql = "SELECT Categories.* " +
                    "FROM TodoList " +
                    "INNER JOIN Todo_Categories ON TodoList.id = Todo_Categories.todoID " +
                    "INNER JOIN Categories ON Todo_Categories.cateID = Categories.id " +
                    "WHERE TodoList.id = '" + todoId + "'";
        
        return getCategoriesBySQL(sql);
    }
}
