/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Sang
 */
public class SubTodo {
    protected String id;
    protected String title;
    protected boolean isCompleted;
    protected Todo todo;

    public SubTodo() {
    }

    public SubTodo(String id, String title, boolean isCompleted, Todo todo) {
        this.id = id;
        this.title = title;
        this.isCompleted = isCompleted;
        this.todo = todo;
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

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
    
}
