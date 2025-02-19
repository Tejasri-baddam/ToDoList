package com.todolist.models;

public class ToDoList {
    private int id;
    private boolean taskcompleted;
    private String taskToBeDone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskToBeDone() {
        return taskToBeDone;
    }

    public void setTask(String taskToBeDone) {
        this.taskToBeDone = taskToBeDone;
    }

    public boolean isCompleted() {
        return taskcompleted;
    }

    public void setCompleted(boolean taskCompleted) {
        this.taskcompleted = taskCompleted;
    }
}
