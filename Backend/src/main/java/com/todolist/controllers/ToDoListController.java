package com.todolist.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.todolist.models.ToDoList;
import com.todolist.services.ToDOListService;


@RestController
@RequestMapping("/todolist")
public class ToDoListController {
    @Autowired
    private ToDOListService toDoListService;

    @GetMapping
    public ResponseEntity<List<ToDoList>> getTodoList() {
        List<ToDoList> list = toDoListService.getTodoList();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDoList> getTodoById(@PathVariable int id) {
        return toDoListService.getToDoListById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ToDoList> addTodo(@RequestBody ToDoList todoItem) {
        ToDoList createdItem = toDoListService.addTaskToList(todoItem);
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToDoList> updateTodo(@PathVariable int id, @RequestBody ToDoList updatedTask) {
        return toDoListService.updateToDoList(id, updatedTask)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable int id) {
        if (toDoListService.deleteToDoList(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
   
}
