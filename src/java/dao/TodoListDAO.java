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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
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
                        ? rsTodo.getTimestamp("dateCreate").toLocalDateTime() : null);
                todo.setDateToComplete(rsTodo.getTimestamp("dateToComplete") != null
                        ? rsTodo.getTimestamp("dateToComplete").toLocalDateTime() : null);
                
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
        String sqlTodo = "Select * From TodoList Where username = '" + username +"' Order By dateToComplete DESC";
        ArrayList<Todo> todoList = getTodoListBySQL(sqlTodo);
        
        return todoList;
    }
    
    public static ArrayList<Todo> getTodayTodoList(String username) {
        String sqlTodo = "Select * From TodoList Where username = '" + username 
                +"' And Convert(DATE, dateToComplete) = Convert(DATE, Getdate()) Order By dateToComplete DESC";
        ArrayList<Todo> todoList = getTodoListBySQL(sqlTodo);
        
        return todoList;
    }
    
    public static ArrayList<Todo> getTodoListByCategory(String categoryID) {
        String sql = "SELECT TodoList.* " +
                    "FROM Categories " +
                    "INNER JOIN Todo_Categories ON Categories.id = Todo_Categories.cateID " +
                    "INNER JOIN TodoList ON Todo_Categories.todoID = TodoList.id " +
                    "WHERE Categories.id = '" + categoryID + "' " +
                    "Order By dateToComplete DESC";
        ArrayList<Todo> todoList = getTodoListBySQL(sql);
         
        return todoList;
    }
    
    public static ArrayList<Todo> getTodoListByDateRange(LocalDateTime startDate, LocalDateTime endDate, String username) {
        String sql = String.format("Select * From TodoList Where username = '%s' And (dateToComplete Between '%s' And '%s') Order By dateToComplete DESC",
                username, FormatLocalDateTime.formatSQLOnlyDate(startDate), FormatLocalDateTime.formatSQLOnlyDate(endDate));
        ArrayList<Todo> todoList = getTodoListBySQL(sql);
         
        return todoList;
    }
    
    public static ArrayList<Todo> searchTodoList(String username, String todoName) {
        String sqlTodo = "Select * From TodoList "
                + "Where username = '" + username +"' And title = '" + todoName + "' "
                + "Order By dateToComplete DESC";
        
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
                        ? rsTodo.getTimestamp("dateCreate").toLocalDateTime() : null);
                todo.setDateToComplete(rsTodo.getTimestamp("dateToComplete") != null
                        ? rsTodo.getTimestamp("dateToComplete").toLocalDateTime() : null);

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
        
        String sql = String.format("Insert Into TodoList (id, title, description, username, dateCreate, dateToComplete) Values ('%s', N'%s', "
                + "N'%s', '%s', Getdate(), '%s')", id, todo.getTitle(), todo.getDescription(),
                username, FormatLocalDateTime.formatSQL(todo.getDateToComplete()));
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
        
        ArrayList<SubTodo> stodoList = SubTodoDAO.getSubTodoList(id);
        if(stodoList.size() > 0) {
            for(SubTodo stodo : stodoList ) {
                SubTodoDAO.deleteSubTodo(stodo.getId());
            }
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
            sql = String.format("Update TodoList Set title = N'%s', description = N'%s', dateToComplete = '%s', is_completed='%b'"
                + " Where id = '%s' And username = '%s'", todo.getTitle(), todo.getDescription(),
                FormatLocalDateTime.formatSQL(todo.getDateToComplete()), todo.isIsCompleted(), todo.getId(), username);
        }
        
              
        int result = connect.excuteUpdate(sql);
        if(result > 0 && todo.getCategories() != null && todo.getCategories().size() > 0) {            
            for(Category category : todo.getCategories()) {
                if(!CategoriesDAO.checkTodoCategoryExist(todo.getId(), category.getId())) {
                    String sqlCate = "Insert Into Todo_Categories Values ('" + todo.getId() + "', '" + category.getId() + "')";
                    connect.excuteUpdate(sqlCate);
                }
            }
            
            ArrayList<Category> categoroies = CategoriesDAO.getTodoCategories(todo.getId());
            ArrayList<Category> categoriesNotExist = new ArrayList<>();
            
            categoriesNotExist = (ArrayList<Category>) categoroies.stream()
                    .filter(category -> todo.getCategories().stream().noneMatch(item -> item.getId().equals(category.getId())))
                    .collect(Collectors.toList());
            
            for(Category category : categoriesNotExist) {
                String sqlDel = "Delete From Todo_Categories Where todoID = '" + todo.getId() + "' And cateID = '" + category.getId() + "'";
                connect.excuteUpdate(sqlDel);
            }
        }
        
        connect.close();
        
        return result > 0;
    }
    
    
    //
    public static ArrayList<Todo> filterTodayTodoList(ArrayList<Todo> todoList) {
        return (ArrayList<Todo>) todoList.stream()
                .filter(todo -> todo.getDateToComplete().toLocalDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
    }
    
    public static ArrayList<Todo> filterWeekTodoList(ArrayList<Todo> todoList) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        LocalDate endOfWeek = startOfWeek.plusDays(7);
        
        ArrayList<Todo> weekTodoList = new ArrayList<>();
        
        for(Todo todo : todoList) {
            LocalDate itemDate = todo.getDateToComplete().toLocalDate();
            if(itemDate.isEqual(startOfWeek) || (itemDate.isAfter(startOfWeek) && itemDate.isBefore(endOfWeek))) {
                weekTodoList.add(todo);
            }
        }
        
        return (ArrayList<Todo>) todoList.stream()
                .filter(todo -> todo.getDateToComplete().toLocalDate().isEqual(startOfWeek) 
                        || (todo.getDateToComplete().toLocalDate().isAfter(startOfWeek) 
                                && todo.getDateToComplete().toLocalDate().isBefore(endOfWeek)))
                .collect(Collectors.toList());
    }
    
    public static ArrayList<Todo> filterMonthTodoList(ArrayList<Todo> todoList) {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        int year = today.getYear();
        
        ArrayList<Todo> monthTodoList = new ArrayList<>();
        
        for(Todo todo : todoList) {
            LocalDate itemDate = todo.getDateToComplete().toLocalDate();
            if(itemDate.getMonthValue() == month && itemDate.getYear() == year) {
                monthTodoList.add(todo);
            }
        }
        
        return monthTodoList;
    }
    
    public static ArrayList<Todo> filterBeforeWeekTodoList(ArrayList<Todo> todoList) {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(java.time.temporal.TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY));
        
        return (ArrayList<Todo>) todoList.stream()
                .filter(todo -> todo.getDateToComplete().toLocalDate().isBefore(startOfWeek))
                .collect(Collectors.toList());
    }
    
    public static ArrayList<Todo> filterCompletedTodo(ArrayList<Todo> todoList) { 
        return (ArrayList<Todo>) todoList.stream().filter(todo -> todo.isIsCompleted()).collect(Collectors.toList());
    }
    
    public static ArrayList<Todo> filterUpcomingTodo(ArrayList<Todo> todoList) { 
        return (ArrayList<Todo>) todoList.stream()
                .filter(todo -> todo.getDateToComplete().isAfter(LocalDateTime.now()) && todo.getDateToComplete().isBefore(LocalDateTime.now().plusMinutes(30)))
                .collect(Collectors.toList());
    }
    
    public static ArrayList<Todo> sortTodoList(ArrayList<Todo> todoList, String sort) {
        if(sort.equals("desc")) {
            todoList.sort(Comparator.comparing(Todo::getDateToComplete).reversed());
        } else {
            todoList.sort(Comparator.comparing(Todo::getDateToComplete));
        }
        
        
        return todoList;
    }
}
