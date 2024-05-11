/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;

/**
 *
 * @author Sang
 */
public class Todo implements Serializable {
    protected String id;
    protected String title;
    protected String description;
    protected String username;
    protected LocalDateTime dateCreate;
    protected LocalDateTime dateToComplete;
    protected boolean isCompleted;
    
    protected ArrayList<Category> categories;
    protected ArrayList<SubTodo> subTodoList;

    public Todo() {
    }

    public Todo(String id, String title, String description, String username, LocalDateTime dateCreate, LocalDateTime dateToComplete, 
            ArrayList<Category> categories, ArrayList<SubTodo> subTodoList, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.username = username;
        this.dateCreate = dateCreate;
        this.dateToComplete = dateToComplete;
        this.categories = categories;
        this.subTodoList = subTodoList;
        this.isCompleted = isCompleted;
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

    public LocalDateTime getDateToComplete() {
        return dateToComplete;
    }

    public void setDateToComplete(LocalDateTime dateToComplete) {
        this.dateToComplete = dateToComplete;
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

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

}
