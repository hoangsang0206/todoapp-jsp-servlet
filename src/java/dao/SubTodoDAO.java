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
import utils.RandomString;
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
    
    public static SubTodo getSubTodo(String id) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        SubTodo subTodo = new SubTodo();
        String sql = "Select TOP 1 * From Sub_TodoList Where id = '" + id + "'";
        ResultSet rs = connect.excuteQuery(sql);
        try {
            if(rs.next()) {
                subTodo.setId(rs.getString("id"));
                subTodo.setTodoId(rs.getString("todoID"));
                subTodo.setTitle(rs.getString("title"));
                subTodo.setIsCompleted(rs.getBoolean("is_completed"));
                
                connect.close();
                return subTodo;
            }
        } catch (SQLException ex) {
            Logger.getLogger(TodoListDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connect.close();
        return null;
    }
    
    public static boolean createSubTodo(String todoID, String title) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String id = "std_" + RandomString.random(20);
        
        while(getSubTodo(id) != null) {
            id = "std_" + RandomString.random(20);
        }
        
        String sql = String.format("Insert Into Sub_TodoList (id, todoID, title) Values ('%s', '%s', N'%s')",
                id, todoID, title);
        
        int result = connect.excuteUpdate(sql);

        connect.close();
        
        return result > 0;
    }
    
    public static boolean updateSubtodo(SubTodo stodo, String type) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update Sub_TodoList Set " 
                + (type.equals("update") ? "title=N'" + stodo.getTitle() + "'" 
                : " is_completed='" + stodo.isIsCompleted() + "'")
                + " Where id='" + stodo.getId() + "'";
        
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    public static boolean deleteSubTodo(String id) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Delete Sub_TodoList Where id = '" + id + "'";
        
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
}
