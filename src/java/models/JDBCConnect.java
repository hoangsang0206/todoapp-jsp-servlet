/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Sang
 */
public class JDBCConnect {
    private Connection connection = null;
    private Statement statement = null;
    private String serverName = "AORUS-LAPTOP";
    private String dbName = "DBTodoApp";
    private String username = "sa";
    private String password = "S@ng0356493696";
    
    public JDBCConnect() {
    }
    
    public void getConnection() {
        try {
            String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            Class.forName(driver);
            
            String connectionURL = "jdbc:sqlserver://" + serverName
                    + ":1433;encrypt=true;trustServerCertificate=true;databaseName=" + dbName
                    + ";user=" + username + ";password=" + password;
            
            connection = DriverManager.getConnection(connectionURL);
            
//            System.out.println(connection == null ? "-- Kết nối cơ sở dữ liệu thất bại."
//                    : "-- Kết nối cơ sở dữ liệu thành công.");
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCConnect.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void close() {
        try {
            connection.close();
            statement.close();
            
//            System.out.println("-- Đã dừng kết nối cơ sở dữ liệu.");
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ResultSet excuteQuery(String sqlQuery) {
        try {
            statement = connection.createStatement();
            
            return statement.executeQuery(sqlQuery);
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public int excuteUpdate(String sqlQuery) {
        try {
            statement = connection.createStatement();
            return statement.executeUpdate(sqlQuery);
            
        } catch (SQLException ex) {
            Logger.getLogger(JDBCConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
}
