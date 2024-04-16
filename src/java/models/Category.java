/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author Sang
 */
public class Category {
    protected String id;
    protected String cateName;
    protected String iconSVG;
    
    protected ArrayList<Todo> todoList;

    public Category() {
    }

    public Category(String id, String cateName, String iconSVG, ArrayList<Todo> todoList) {
        this.id = id;
        this.cateName = cateName;
        this.iconSVG = iconSVG;
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

    public ArrayList<Todo> getTodoList() {
        return todoList;
    }

    public void setTodoList(ArrayList<Todo> todoList) {
        this.todoList = todoList;
    }
    
}
