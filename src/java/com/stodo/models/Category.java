/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.stodo.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Sang
 */
public class Category implements Serializable {
    protected String id;
    protected String cateName;
    protected String iconSVG;
    protected String username;
    
    protected ArrayList<Todo> todoList;

    public Category() {
    }

    public Category(String id, String cateName, String iconSVG, String username, ArrayList<Todo> todoList) {
        this.id = id;
        this.cateName = cateName;
        this.iconSVG = iconSVG;
        this.username = username;
        this.todoList = todoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getIconSVG() {
        return iconSVG;
    }

    public void setIconSVG(String iconSVG) {
        this.iconSVG = iconSVG;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<Todo> todoList) {
        this.todoList = todoList;
    }
    
}
