/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stodo.dao;

import com.stodo.models.Category;
import com.stodo.models.JDBCConnect;
import java.util.ArrayList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sang
 */
public class CategoriesDAO {
    
    public static ArrayList<Category> getCategories(String todoId) {
        try {
            ArrayList<Category> categories = new ArrayList<>();
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            String sql = "SELECT Categories.* " +
                    "FROM TodoList " +
                    "INNER JOIN Todo_Categories ON TodoList.id = Todo_Categories.todoID " +
                    "INNER JOIN Categories ON Todo_Categories.cateID = Categories.id " +
                    "WHERE TodoList.id = '" + todoId + "'";
            
            ResultSet rs = connect.excuteQuery(sql);
            while(rs.next()) {
                Category category = new Category();
                category.setId(rs.getString("id"));
                category.setCateName(rs.getString("cateName"));
                category.setUsername(rs.getString("username"));
                category.setIconSVG(rs.getString("iconSVG"));
                
                categories.add(category);
            }
            
            return categories;
                
        } catch (SQLException ex) {
            Logger.getLogger(CategoriesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
