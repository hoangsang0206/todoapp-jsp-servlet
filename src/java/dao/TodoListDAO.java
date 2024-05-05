/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Sang
 */
import models.Category;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.JDBCConnect;
import models.RandomString;
import models.Todo;
import models.SubTodo;
import org.apache.jasper.tagplugins.jstl.core.ForEach;
import utils.FormatLocalDateTime;

public class TodoListDAO {
    
    private static ArrayList<Todo> getTodoListBySQL(String sqlQuery) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ArrayList<Todo> todoList = new ArrayList<>();
            
            ResultSet rsTodo = connect.excuteQuery(sqlQuery);
            
            while(rsTodo.next()) {
                Todo todo = new Todo();
                todo.setId(rsTodo.getString("id"));
                todo.setTitle(rsTodo.getString("title"));
                todo.setDescription(rsTodo.getString("description"));
                todo.setUsername(rsTodo.getString("username"));
                todo.setIsCompleted(rsTodo.getBoolean("is_completed"));
                
                todo.setDateCreate(rsTodo.getTimestamp("dateCreate") != null
                        ? rsTodo.getTimestamp("dateCreate").toLocalDateTime() : LocalDateTime.now());
                todo.setDateCompleted(rsTodo.getTimestamp("dateCompleted") != null
                        ? rsTodo.getTimestamp("dateCompleted").toLocalDateTime() : null);
                
                //Get sub task in todo
                ArrayList<SubTodo> subTodoList = SubTodoDAO.getSubTodoList(todo.getId());
                todo.setSubTodoList(subTodoList);
                
                //Get categories
                ArrayList<Category> categories = CategoriesDAO.getTodoCategories(todo.getId());
                todo.setCategories(categories);
                
                todoList.add(todo);
            }
            
            connect.close();
            return todoList;
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }

    public static ArrayList<Todo> getTodoList(String username) {
        String sqlTodo = "Select * From TodoList Where username = '" + username +"' Order By dateCreate ASC";
        ArrayList<Todo> todoList = getTodoListBySQL(sqlTodo);
        
        return todoList;
    }
    
    public static ArrayList<Todo> getTodayTodoList(String username) {
        String sqlTodo = "Select * From TodoList Where username = '" + username 
                +"' And Convert(DATE, dateCreate) = Convert(DATE, Getdate()) Order By dateCreate ASC";
        ArrayList<Todo> todoList = getTodoListBySQL(sqlTodo);
        
        return todoList;
    }
    
    public static ArrayList<Todo> getTodoListByCategory(String categoryID) {
        String sql = "SELECT TodoList.* " +
                    "FROM Categories " +
                    "INNER JOIN Todo_Categories ON Categories.id = Todo_Categories.cateID " +
                    "INNER JOIN TodoList ON Todo_Categories.todoID = TodoList.id " +
                    "WHERE Categories.id = '" + categoryID + "'";
        ArrayList<Todo> todoList = getTodoListBySQL(sql);
         
        return todoList;
    }
    
    public static ArrayList<Todo> searchTodoList(String username, String todoName) {
        String sqlTodo = "Select * From TodoList "
                + "Where username = '" + username +"' And title = '" + todoName + "' "
                + "Order By dateCreate ASC";
        
         ArrayList<Todo> todoList = getTodoListBySQL(sqlTodo);
        
        return todoList;
    }
    
    
    public static Todo getTodo(String todoID, String username) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            Todo todo = new Todo();
            String sqlTodo = "Select TOP 1 * From TodoList "
                    + "Where username = '" + username + "' And id = '" + todoID + "'";
            
            ResultSet rsTodo = connect.excuteQuery(sqlTodo);
            
            if(rsTodo.next()) {
                todo.setId(rsTodo.getString("id"));
                todo.setTitle(rsTodo.getString("title"));
                todo.setDescription(rsTodo.getString("description"));
                todo.setUsername(rsTodo.getString("username"));
                todo.setIsCompleted(rsTodo.getBoolean("is_completed"));

                todo.setDateCreate(rsTodo.getTimestamp("dateCreate") != null
                        ? rsTodo.getTimestamp("dateCreate").toLocalDateTime() : LocalDateTime.now());
                todo.setDateCompleted(rsTodo.getTimestamp("dateCompleted") != null
                        ? rsTodo.getTimestamp("dateCompleted").toLocalDateTime() : null);

                //Get sub task in todo
                ArrayList<SubTodo> subTodoList = SubTodoDAO.getSubTodoList(todo.getId());
                todo.setSubTodoList(subTodoList);

                //Get categories
                ArrayList<Category> categories = CategoriesDAO.getTodoCategories(todo.getId());
                todo.setCategories(categories);
                
                connect.close();
                return todo;
             }
            
            connect.close();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public static boolean createTodo(Todo todo, ArrayList<Category> categories, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String id = "td_" + RandomString.random(20);
        
        while(getTodo(id, username) != null) {
            id = "td_" + RandomString.random(20);
        }
        
        String sql = String.format("Insert Into TodoList (id, title, description, username, dateCreate, dateCompleted) Values ('%s', N'%s', "
                + "N'%s', '%s', Getdate(), '%s')", id, todo.getTitle(), todo.getDescription(),
                username, FormatLocalDateTime.formatSQL(todo.getDateCompleted()));
        int result = connect.excuteUpdate(sql);
        
        if(result > 0 && categories.size() > 0) {
           for(Category category : categories) {
               String sqlCate = "Insert Into Todo_Categories Values ('" + id + "', '" + category.getId() + "')";
               connect.excuteUpdate(sqlCate);
           } 
        }
        
        connect.close();
        
        return result > 0;
    }
    
    
    public static boolean deleteTodo(Todo todo, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Delete From TodoList Where id = '" + todo.getId() + "' And username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    
    public static boolean updateTodo(Todo todo, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = String.format("Update TodoList Set title = N'%s', description = N''%s dateCompleted = '%s'"
                + "Where id = '%s' And username = '%s'", todo.getTitle(), todo.getDescription(),
                todo.getDateCompleted(), todo.getId(), username);
        
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
}
