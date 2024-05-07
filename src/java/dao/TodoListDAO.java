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
    
    
    public static boolean createTodo(Todo todo, String username) {
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
        
        if(result > 0 && todo.getCategories() != null && todo.getCategories().size() > 0) {
           for(Category category : todo.getCategories()) {
               String sqlCate = "Insert Into Todo_Categories Values ('" + id + "', '" + category.getId() + "')";
               connect.excuteUpdate(sqlCate);
           } 
        }
        
        connect.close();
        
        return result > 0;
    }    
    
    
    public static boolean deleteTodo(String id, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Delete From TodoList Where id = '" + id + "' And username = '" + username + "'";
        
        ArrayList<Category> categories = CategoriesDAO.getTodoCategories(id);
        if(categories != null && categories.size() > 0) {
            String sql1 = "Delete From Todo_Categories Where todoID = '" + id + "'";
            connect.excuteUpdate(sql1);
        }
        
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    
    public static boolean updateTodo(String type, Todo todo, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql;
        
        if(type != null && type.equals("status")) {
            sql = "Update TodoList Set is_completed='" + todo.isIsCompleted() + "'"
                    + " Where id = '" + todo.getId() + "' And username = '" + username +"'";
        } else {
            sql = String.format("Update TodoList Set title = N'%s', description = N'%s', dateCompleted = '%s', is_completed='%b'"
                + " Where id = '%s' And username = '%s'", todo.getTitle(), todo.getDescription(),
                FormatLocalDateTime.formatSQL(todo.getDateCompleted()), todo.isIsCompleted(), todo.getId(), username);
        }
        
        
        
        int result = connect.excuteUpdate(sql);
        if(result > 0 && type.equals("status") && todo.getCategories() != null && todo.getCategories().size() > 0) {
            ArrayList<Category> categories = CategoriesDAO.getTodoCategories(todo.getId());
            for(Category category : todo.getCategories()) {
                if(categories != null && categories.size() > 0) {
                    String sqlCate = "Update Todo_Categories Set cateID = '" + category.getId() + "'"
                        + " Where todoID = '" + todo.getId() + "'";
                    connect.excuteUpdate(sqlCate);
                } else {
                    String sqlCate = "Insert Into Todo_Categories Values ('" + todo.getId() + "', '" + category.getId() + "')";
                    connect.excuteUpdate(sqlCate);
                } 
            }
        }
        
        connect.close();
        
        return result > 0;
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
        
        String sql = String.format("Insert Into Sub_TodoList (id, todoID, title) Values ('%s', '%s', '%s')",
                id, todoID, title);
        
        int result = connect.excuteUpdate(sql);

        connect.close();
        
        return result > 0;
    }
    
    public static boolean updateSubtodo(SubTodo stodo, String type) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update Sub_TodoList Set " 
                + (type.equals("update") ? "title='" + stodo.getTitle() + "'" 
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
