/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import models.Category;
import utils.FormatLocalDateTime;
import models.JDBCConnect;
import models.RandomString;
import models.Todo;
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
                category.setIconColor(rs.getString("iconColor"));
                
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
                    todo.setDateToComplete(rsTodo.getTimestamp("dateToComplete") != null
                            ? rsTodo.getTimestamp("dateToComplete").toLocalDateTime() : null);
                    
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
        String sql = "Select * From Categories Where username = '" + username + "' Order By dateCreate";
        
        return getCategoriesBySQL(sql);
    }
    
    public static ArrayList<Category> getTodoCategories(String todoId) {
        try {
            String sql = "SELECT Categories.* " +
                    "FROM TodoList " +
                    "INNER JOIN Todo_Categories ON TodoList.id = Todo_Categories.todoID " +
                    "INNER JOIN Categories ON Todo_Categories.cateID = Categories.id " +
                    "WHERE TodoList.id = '" + todoId + "' " +
                    "Order By dateCreate";
            
            ArrayList<Category> categories = new ArrayList<>();
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ResultSet rs = connect.excuteQuery(sql);
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setCateName(rs.getString("cateName"));
                category.setUsername(rs.getString("username"));
                category.setIconColor(rs.getString("iconColor"));
                
                categories.add(category);
            }
            
            return categories;
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }
    
    public static Category getCategory(String id) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();        
        
        Category category = new Category();
        String sql = "Select TOP 1 * From Categories Where id = '" + id + "'";
        
        ResultSet rs = connect.excuteQuery(sql);
        try {
            if(rs.next()) {
                category.setId(rs.getString("id"));
                category.setCateName(rs.getString("cateName"));
                category.setUsername(rs.getString("username"));
                category.setIconColor(rs.getString("iconColor"));
                
                connect.close();
                return category;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connect.close();
        return null;
    }
    
    public static boolean createCategory(Category category, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String id = "c_" + RandomString.random(20);
        
        while(getCategory(id) != null) {
            id = "c_" + RandomString.random(20);
        }
        
        String sql = String.format("Insert Into Categories Values ('%s', N'%s', '%s', N'%s', '%s')",
                id, category.getCateName(), username, category.getIconColor(), FormatLocalDateTime.formatSQL(category.getDateCreate()));
        
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    public static boolean updateCategory(Category category, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update Categories Set cateName = N'" + category.getCateName() + "', iconColor = N'" + category.getIconColor() + "'"
                + " Where id = '" + category.getId() + "' And username = '" + username + "'";
        
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        return result > 0;
    }
    
    public static boolean deleteCategory(String id, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        ArrayList<Todo> todoList = TodoListDAO.getTodoListByCategory(id);
        if(todoList.size() > 0) {
            for(Todo todo : todoList) {
                ArrayList<Category> categories = CategoriesDAO.getTodoCategories(todo.getId());
                if(categories.size() == 1) {
                    TodoListDAO.deleteTodo(todo.getId(), username);
                }
            }
        }
        
        String sql1 = "Delete From Todo_Categories Where cateID = '" + id + "'";
        String sql2 = "Delete From Categories Where id = '" + id + "' And username = '" + username + "'";
        connect.excuteUpdate(sql1);
        int result = connect.excuteUpdate(sql2);
        
        connect.close();
        return result > 0;
    }
    
    //
    public static boolean checkTodoCategoryExist(String todoID, String cateID) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Select TOP 1 * From Todo_Categories Where todoID = '" + todoID + "' And cateID = '" + cateID + "'";
        ResultSet rs = connect.excuteQuery(sql);
        
        try {
            if(rs.next()) {
                connect.close();
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        connect.close();
        return false;
    }
}
