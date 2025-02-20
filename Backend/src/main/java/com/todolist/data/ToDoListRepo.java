package com.todolist.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.todolist.models.ToDoList;


public interface ToDoListRepo extends MongoRepository<ToDoList, String> {
    
}