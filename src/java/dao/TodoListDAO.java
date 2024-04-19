/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Sang
 */
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

public class TodoListDAO {
    public TodoListDAO() {
        
    }
    
    
    public ArrayList<Todo> getTodoList(String username) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ArrayList<Todo> todoList = new ArrayList<>();
            String sqlTodo = "Select * From TodoList Where username = '" + username +"'  Order By dateCreate ASC";
            
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
                
                //Get sub task in todo
                ArrayList<SubTodo> subTodoList = new ArrayList<>();
                SubTodoDAO subTodoDAO = new SubTodoDAO();
                subTodoList = subTodoDAO.getSubTodoList(todo.getId());
                
                todo.setSubTodoList(subTodoList);
                
                todoList.add(todo);
            }
            
            connect.close();
            return todoList;
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return new ArrayList<>();
    }
    
    
    public ArrayList<Todo> searchTodoList(String username, String todoName) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ArrayList<Todo> todoList = new ArrayList<>();
            String sqlTodo = "Select * From TodoList"
                    + "Where username = '" + username +"' And title = '" + todoName + "' "
                    + "Order By dateCreate ASC";
            
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
            
            connect.close();
            return todoList;
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public Todo getTodo(String todoID, String username) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            Todo todo = new Todo();
            String sqlTodo = "Select TOP 1 From TodoList"
                    + "Where username = '" + username +"' And id = '" + todoID + "'";
            
            ResultSet rsTodo = connect.excuteQuery(sqlTodo);
            
            while(rsTodo.next()) {
                todo.setId(rsTodo.getString("id"));
                todo.setTitle(rsTodo.getString("title"));
                todo.setDescription(rsTodo.getString("description"));
                todo.setUsername(rsTodo.getString("username"));
                
                todo.setDateCreate(rsTodo.getTimestamp("dateCreate") != null
                        ? rsTodo.getTimestamp("dateCreate").toLocalDateTime() : LocalDateTime.now());
                todo.setDateCompleted(rsTodo.getTimestamp("dateCompleted") != null
                        ? rsTodo.getTimestamp("dateCompleted").toLocalDateTime() : null);
                
                break;
            }
            
            connect.close();
            
            return todo;
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public boolean createTodo(Todo todo, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String id = "td_" + RandomString.random(20);
        
        while(getTodo(id, username) != null) {
            id = RandomString.random(20);
        }
        
        String sql = String.format("Insert Into TodoList (id, title, description, username, dateCreate) Values ('%s', N'%s', "
                + "N'%s', '%s', '%s')", todo.getId(), todo.getTitle(), todo.getDescription(),
                username, todo.getDateCreate().toString());
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    
    public boolean deleteTodo(Todo todo, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Delete From TodoList Where id = '" + todo.getId() + "' And username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    
    public boolean updateTodo(Todo todo, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = String.format("Update TodoList Set title = N'%s', description = N''%s dateCompleted = '%s'"
                + "Where id = '%s' And username = '%s'", todo.getTitle(), todo.getDescription(),
                todo.getDateCompleted(), todo.getId(), username);
        
        int result = connect.excuteUpdate(sql);
        
        return result > 0;
    }
}
