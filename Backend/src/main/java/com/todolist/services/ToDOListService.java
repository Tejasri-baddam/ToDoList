package com.todolist.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.todolist.models.ToDoList;

@Service
public class ToDOListService {
    private int counter = 1;
    private List<ToDoList>toDoLists = new ArrayList<>();

    public List<ToDoList> getTodoList() {
        return toDoLists;
    }

    public Optional<ToDoList> getToDoListById(int id) {
        return toDoLists.stream().filter(task -> task.getId()==id).findFirst();
    }

    public ToDoList addTaskToList(ToDoList task) {
        task.setId(counter++);
        toDoLists.add(task);
        return task;
    }

    public Optional<ToDoList> updateToDoList(int id, ToDoList updatedTask) {
        return getToDoListById(id).map(item -> {
            item.setTask(updatedTask.getTaskToBeDone());
            item.setCompleted(updatedTask.isCompleted());
            return item;
        });
    }

    public boolean deleteToDoList(int id) {
        return toDoLists.removeIf(task -> task.getId()==id);
    }

}
