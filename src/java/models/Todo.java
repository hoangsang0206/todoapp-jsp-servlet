/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 *
 * @author Sang
 */
public class Todo {
    protected String id;
    protected String title;
    protected String description;
    protected String username;
    protected LocalDateTime dateCreate;
    protected LocalDateTime dateCompleted;
    
    protected ArrayList<Category> categories;
    protected ArrayList<SubTodo> subTodoList;

    public Todo() {
    }

    public Todo(String id, String title, String description, String username, LocalDateTime dateCreate, LocalDateTime dateCompleted, ArrayList<Category> categories, ArrayList<SubTodo> subTodoList) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.username = username;
        this.dateCreate = dateCreate;
        this.dateCompleted = dateCompleted;
        this.categories = categories;
        this.subTodoList = subTodoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public LocalDateTime getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(LocalDateTime dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    public ArrayList<SubTodo> getSubTodoList() {
        return subTodoList;
    }

    public void setSubTodoList(ArrayList<SubTodo> subTodoList) {
        this.subTodoList = subTodoList;
    }

}
