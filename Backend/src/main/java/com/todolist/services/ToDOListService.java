package com.todolist.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todolist.data.ToDoListRepo;
import com.todolist.models.ToDoList;

@Service
public class ToDOListService {
    @Autowired
    private ToDoListRepo toDoListRepository;

    public List<ToDoList> getTodoList() {
        return toDoListRepository.findAll();
    }

    public Optional<ToDoList> getToDoListById(String id) {
        return toDoListRepository.findById(id);
    }

    public ToDoList addTaskToList(ToDoList task) {
         task.setId(UUID.randomUUID().toString());
        toDoListRepository.save(task);
        return task;
    }

    public Optional<ToDoList> updateToDoList(String id, ToDoList updatedTask) {
        return toDoListRepository.findById(id).map(item -> {
            item.setTask(updatedTask.getTaskToBeDone());
            item.setCompleted(updatedTask.isCompleted());
            return toDoListRepository.save(item);
        });
    }

    public boolean deleteToDoList(String id) {
        if (toDoListRepository.existsById(id)) {
            toDoListRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
