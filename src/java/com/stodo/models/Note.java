/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stodo.models;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Sang
 */
public class Note implements Serializable {
    protected String id;
    protected String content;
    protected String username;
    protected LocalDateTime dateCreate;

    public Note() {
    }

    public Note(String id, String content, String username, LocalDateTime dateCreate) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.dateCreate = dateCreate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }
    
}
