/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import models.Account;
import models.JDBCConnect;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.RandomString;

/**
 *
 * @author Sang
 */
public class AccountDAO {
    public static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9-]+\\\\.)+[A-Za-z]{2,}$");
    public static final String STATUS_DELETED = "DELETED";
    public static final String STATUS_PENDING_DELETE = "PENDING_DELETE";
  
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
                user.setEmailVerified(rsUser.getBoolean("email_verified"));
                user.setFullName(rsUser.getString("fullname"));
                user.setImageSrc(rsUser.getString("imageSrc"));
                user.setStatus(rsUser.getString("status"));
                
                return user;
            }
            
            connect.close();

        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static Account getLoggedInUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            Account account = (Account) session.getAttribute("user");
            return account;
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
    
    public static boolean updatePassword(String username, String newPassword) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String code = RandomString.randomCode(5);
        String sql = "Update Account Set passwordHash = N'" + hashPassword(newPassword) 
                + "' And code = '" + code + "' Where username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    public static boolean changePassword(String username, String oldPassword, String newPassword) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String oldPasswordHash = hashPassword(oldPassword);
        String newPasswordHash = hashPassword(newPassword);
        if(!oldPasswordHash.equals(newPasswordHash)) {
            return false;
        }
        
        String sql = "Update Account Set passwordHash = N'" + newPasswordHash + "' Where username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    public static boolean updateEmail(String email, String username) {
        if(!isValidEmail(email)) {
            return false;
        }
        
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update Account Set email = N'" + email + "' Where username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    private static String getVerificationKey(String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Select email_verify_key From Account Where username = '" + username + "'";
        ResultSet resultSet = connect.excuteQuery(sql);
        
        String key = null;
        try {
            if(resultSet.next()) {
                key = resultSet.getString("email_verify_key");
                connect.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connect.close();
        return key;
    }
    
    public static boolean getEmailVerificationStatus(String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Select email_verified From Account Where username = '" + username + "'";
        ResultSet resultSet = connect.excuteQuery(sql);
        boolean status = false;
        try {
            if(resultSet.next()) {
                status = resultSet.getBoolean("email_verified");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        connect.close();
        return status;
    }
    
    public static boolean verifyEmail(String username, String verificationKey) {
        if(!verificationKey.equals(getVerificationKey(username))) {
            return false;
        }
                
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update Account Set email_verified = 1 Where username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    public static boolean setVerifycationKey(String username, String key) {
        String sql = "Update Account Set email_verify_key = '" + key + "' Where username = '" + username + "'";
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    public static boolean updateImage(String username, String imageSrc) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update Account Set imageSrc = N'" + imageSrc + "' Where username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
    }
    
    public static boolean updateStatus(String status, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update Account Set status = '" + status + "' Where username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        connect.close();
        
        return result > 0;
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
    
    public static boolean isValidEmail(String email) {
        if(email == null || email.isEmpty()) {
            return false;
        }
        
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        
        return matcher.matches();
    }
}
