/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.JDBCConnect;
import models.RandomString;
import models.SubTodo;

/**
 *
 * @author Sang
 */
public class SubTodoDAO {
    
    public static ArrayList<SubTodo> getSubTodoList(String todoID) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ArrayList<SubTodo> subTodoList = new ArrayList<>();
            String sql = "Select * From Sub_TodoList Where todoID = '" + todoID + "'";
            
            ResultSet resultSet = connect.excuteQuery(sql);
            
            while(resultSet.next()) {
                SubTodo subTodo = new SubTodo();
                subTodo.setId(resultSet.getString("id"));
                subTodo.setTodoId(resultSet.getString("todoID"));
                subTodo.setTitle(resultSet.getString("title"));
                subTodo.setIsCompleted(resultSet.getBoolean("is_completed"));
                
                subTodoList.add(subTodo);
            }
            
            connect.close();
            
            return subTodoList;
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public static SubTodo getTodo(String subTodoId) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            SubTodo subTodo = new SubTodo();
            String sqlTodo = "Select TOP 1 From Sub_TodoList"
                    + "Where id = '" + subTodoId + "'";
            
            ResultSet resultSet = connect.excuteQuery(sqlTodo);
            
            while(resultSet.next()) {
                subTodo.setId(resultSet.getString("id"));
                subTodo.setTodoId(resultSet.getString("todoID"));
                subTodo.setTitle(resultSet.getString("title"));
                subTodo.setIsCompleted(resultSet.getBoolean("is_completed"));
                
                break;
            }
            
            connect.close();
            
            return subTodo;
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public static boolean createTodo(SubTodo subTodo) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String id = "std_" + RandomString.random(20);
        
        while(getTodo(id) != null) {
            id = RandomString.random(20);
        }
        
        String sql = "Insert Into Sub_TodoList Values ('" + id + ", '" + subTodo.getTodoId() + "'"
                + "'" + subTodo.getTitle() + "', 0)";
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    
    public static boolean deleteTodo(SubTodo subTodo) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Delete From Sub_TodoList Where id = '" + subTodo.getId() + "'";
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    
    public static boolean updateTodo(SubTodo subTodo) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Delete From Sub_TodoList Where id = '" + subTodo.getId() + "'";
        
        int result = connect.excuteUpdate(sql);
        
        return result > 0;
    }
}
