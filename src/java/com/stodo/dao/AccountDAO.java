/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stodo.dao;

import com.stodo.models.Account;
import com.stodo.models.JDBCConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Sang
 */
public class AccountDAO {
  
    public static Account getUser(String username, String passwordHash) {
        try {
            String sql = "Select TOP 1 * From Account Where username = '" + username + "' And passwordHash = N'" + passwordHash + "'";
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ResultSet rsUser = connect.excuteQuery(sql);
            if(rsUser.next()) {
                Account user = new Account();
                user.setUsername(rsUser.getString("username"));
                user.setPasswordHash(rsUser.getString("passwordHash"));
                user.setEmail(rsUser.getString("email"));
                user.setFullName(rsUser.getString("fullname"));
                user.setImageSrc(rsUser.getString("imageSrc"));
                
                return user;
            }
            
            connect.close();

        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static boolean createAccount(String username, String passwordHash) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Insert Into Account (username, passwordHash)"
                + " Values ('" + username + "', N'" + passwordHash + "')";
        
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    public static boolean updateAccount() {
        
        return false;
    }
    
    public static boolean existUser(String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Select TOP 1 * From Account Where username = '" + username + "'";
        try {
            if(connect.excuteQuery(sql).next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connect.close();
        return false;
    }
    
    public static boolean checkLogin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        
        return session.getAttribute("user") != null;
    }
    
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);

            for(byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
            
        }
        
        return null;
    }
}
