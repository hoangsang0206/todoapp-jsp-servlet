/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.JDBCConnect;
import models.Note;
import models.RandomString;

/**
 *
 * @author Sang
 */
public class NotesDAO {
    public NotesDAO() {
        
    }
    
    
    public ArrayList<Note> getNotes(String username) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            ArrayList<Note> noteList = new ArrayList<>();
            String sqlNote = "Select * From Notes Where username = '" + username +"'  Order By dateCreate ASC";
            
            ResultSet rsNote = connect.excuteQuery(sqlNote);
            
            while(rsNote.next()) {
                Note note = new Note();
                note.setId(rsNote.getString("id"));
                note.setContent(rsNote.getString("content"));
                note.setUsername(rsNote.getString("username"));
                
                note.setDateCreate(rsNote.getTimestamp("dateCreate") != null
                        ? rsNote.getTimestamp("dateCreate").toLocalDateTime() : LocalDateTime.now());
                
                
                noteList.add(note);
            }
            
            connect.close();
            
            return noteList;
            
        } catch (SQLException ex) {
            Logger.getLogger(NotesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
      
    
    public Note getNote(String noteID, String username) {
        try {
            JDBCConnect connect = new JDBCConnect();
            connect.getConnection();
            
            Note note = new Note();
            String sqlNote = "Select TOP 1 From Notes"
                    + "Where username = '" + username +"' And id = '" + noteID + "'";
            
            ResultSet rsNote = connect.excuteQuery(sqlNote);
            
            while(rsNote.next()) {
                note.setId(rsNote.getString("id"));
                note.setContent(rsNote.getString("content"));
                note.setUsername(rsNote.getString("username"));
                
                note.setDateCreate(rsNote.getTimestamp("dateCreate") != null
                        ? rsNote.getTimestamp("dateCreate").toLocalDateTime() : LocalDateTime.now());
                
                break;
            }
            
            connect.close();
            
            return note;
        } catch (SQLException ex) {
            Logger.getLogger(NotesDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    
    public boolean createNote(Note note, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String id = "n_" + RandomString.random(20);
        
        while(getNote(id, username) != null) {
            id = RandomString.random(20);
        }
        
        String sql = String.format("Insert Into Notes (id, content, username, dateCreate) Values ('%s', N'%s', "
                + "N'%s', '%s', '%s')", note.getId(), note.getContent(), username, note.getDateCreate().toString());
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    
    public boolean deleteNote(Note note, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Delete From Notes Where id = '" + note.getId() + "' And username = '" + username + "'";
        int result = connect.excuteUpdate(sql);
        
        connect.close();
        
        return result > 0;
    }
    
    
    public boolean updateNote(Note note, String username) {
        JDBCConnect connect = new JDBCConnect();
        connect.getConnection();
        
        String sql = "Update NoteList Set content = N'" + note.getContent() + "'"
                + "Where id = '" + note.getId() + "' And username = '" + username + "'";
        
        int result = connect.excuteUpdate(sql);
        
        return result > 0;
    }
}
