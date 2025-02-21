package com.todolist.controllers;

import com.todolist.data.ToDoListRepo;
import com.todolist.models.ToDoList;
import com.todolist.services.ToDOListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToDoListController.class)
public class ToDoListControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDOListService toDoListService;

    @MockBean
    private ToDoListRepo toDoListRepo; 

    private ToDoList task1;
    private ToDoList task2;

    @BeforeEach
    public void setUp() {
        task1  = new ToDoList();
        task1.setId(UUID.randomUUID().toString());
        task1.setTask("Complete Assignment");
        task1.setCompleted(false);

        task2  = new ToDoList();
        task2.setId(UUID.randomUUID().toString());
        task2.setTask("Read a Book");
        task2.setCompleted(false);
    }

    @Test
    void testGetTodoList() throws Exception {
        when(toDoListService.getTodoList()).thenReturn(Arrays.asList(task1,task2));

        mockMvc.perform(get("/todolist")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].taskToBeDone").value("Complete Assignment"));
    }

    @Test
    void testGetTodoById() throws Exception {
        when(toDoListService.getToDoListById(task1.getId())).thenReturn(Optional.of(task1));

        mockMvc.perform(get("/todolist/"+task1.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskToBeDone").value("Complete Assignment"));
    }

    @Test
    void testGetTodoByIdNotFound() throws Exception {
        when(toDoListService.getToDoListById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/todolist/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddTodo() throws Exception {
        ToDoList task  = new ToDoList();
        task.setId(UUID.randomUUID().toString());
        task.setTask("Update sofware");
        task.setCompleted(false);
        when(toDoListService.addTaskToList(any(ToDoList.class))).thenReturn(task);

        mockMvc.perform(post("/todolist")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"taskToBeDone\":\"Update sofware\", \"completed\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskToBeDone").value("Update sofware"));
    }

   @Test
    void testUpdateTodo() throws Exception {
        // Ensure task1 has an ID
        assertNotNull(task1.getId(), "task1 ID should not be null");

        // Update task1 before returning it
        task1.setTask("Updated Task");

        when(toDoListService.updateToDoList(eq(task1.getId()), any(ToDoList.class)))
            .thenReturn(Optional.of(task1));

        mockMvc.perform(put("/todolist/" + task1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"taskToBeDone\":\"Updated Task\", \"completed\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskToBeDone").value("Updated Task"));
    }


    @Test
    void testUpdateTodoNotFound() throws Exception {
        when(toDoListService.updateToDoList(eq("1"), any(ToDoList.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/todolist/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"taskToBeDone\":\"Updated Task\", \"completed\":false}"))
                .andExpect(status().isNotFound());
    }


    @Test
    void testDeleteTodo() throws Exception {
        when(toDoListService.deleteToDoList(task1.getId())).thenReturn(true);

        mockMvc.perform(delete("/todolist/"+task1.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTodoNotFound() throws Exception {
        when(toDoListService.deleteToDoList("1")).thenReturn(false);

        mockMvc.perform(delete("/todolist/1"))
                .andExpect(status().isNotFound());
    }
}