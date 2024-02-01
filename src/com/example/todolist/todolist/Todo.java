package com.example.todolist.todolist;

public class Todo {
    private int id;
    private String task;
    private String dueDate;
    private boolean completed;
    private String priority;
    private String category;

    public Todo(int id, String task, String dueDate, boolean completed, String priority, String category) {
        this.id = id;
        this.task = task;
        this.dueDate = dueDate;
        this.completed = completed;
        this.priority = priority;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getTask() {
        return task;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getPriority() {
        return priority;
    }

    public String getCategory() {
        return category;
    }
}
