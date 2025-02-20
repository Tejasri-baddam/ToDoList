package com.todolist.models;

import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;

@Document(collection = "Tasks")
public class ToDoList {
    @Id
    private String id;
    private boolean taskcompleted;
    private String taskToBeDone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
