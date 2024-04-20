/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stodo.models;

import java.util.ArrayList;

/**
 *
 * @author Sang
 */
public class Account {
    protected String username;
    protected String password;
    protected String fullName;
    protected String email;
    protected String imageSrc;
    
    protected ArrayList<Category> categories;
    protected ArrayList<Todo> todoList;
    protected ArrayList<Note> notes;

    public Account() {
    }

    public Account(String username, String password, String fullName, String email, String imageSrc, ArrayList<Category> categories, ArrayList<Todo> todoList, ArrayList<Note> notes) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.imageSrc = imageSrc;
        this.categories = categories;
        this.todoList = todoList;
        this.notes = notes;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<Todo> todoList) {
        this.todoList = todoList;
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }
    
}
