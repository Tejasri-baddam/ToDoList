package com.todolist.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.todolist.data.ToDoListRepo;
import com.todolist.models.ToDoList;

@ExtendWith(MockitoExtension.class)
public class ToDoListServiceTest {

    @Mock
    private ToDoListRepo toDoListRepository;

    @InjectMocks
    private ToDOListService toDoListService;

    private ToDoList task1;
    private ToDoList task2;
    private List<ToDoList>list;
    AutoCloseable openMocks;

    @BeforeEach
    void setUp(){
        openMocks = MockitoAnnotations.openMocks(this);
        task1  = new ToDoList();
        task1.setId(UUID.randomUUID().toString());
        task1.setTask("Complete Assignment");
        task1.setCompleted(false);

        task2  = new ToDoList();
        task2.setId(UUID.randomUUID().toString());
        task2.setTask("Read a Book");
        task2.setCompleted(false);
        list = new ArrayList<>(Arrays.asList(task1, task2));
    }

    @AfterEach
    void closeMocks() throws Exception {
        openMocks.close();
    }

    @Test
    void testGetToDoList(){
        when(toDoListRepository.findAll()).thenReturn(list);

        List<ToDoList>tasks = toDoListService.getTodoList();
        assertEquals(list, tasks, "The two lists should be equal");
        verify(toDoListRepository,times(1)).findAll();
    }
    
    @Test
    void testGetToDoListById(){
        when(toDoListRepository.findById(task1.getId())).thenReturn(list.stream().filter(item->item.getId().equals(task1.getId())).findFirst());

        Optional<ToDoList> task = toDoListService.getToDoListById(task1.getId());

        assertTrue(task.isPresent());
        assertEquals(task.get().getTaskToBeDone(), "Complete Assignment", "The tasks should be same");
        verify(toDoListRepository,times(1)).findById(task1.getId());
    }

    @Test
    void testGetToDoListById_NotFound(){
        when(toDoListRepository.findById("Invalid_Id")).thenReturn(list.stream().filter(item->item.getId().equals("Invalid_Id")).findFirst());

        Optional<ToDoList> task = toDoListService.getToDoListById("Invalid_Id");

        assertFalse(task.isPresent());
        verify(toDoListRepository,times(1)).findById("Invalid_Id");
    }

    @Test
    void testAddTaskToList(){
        ToDoList newTask = new ToDoList();
        newTask.setTask("Do yoga morning at 8am");
        newTask.setCompleted(false);

        when(toDoListRepository.save(any(ToDoList.class))).thenAnswer(invocation -> {
            ToDoList task = invocation.getArgument(0);
            task.setId(UUID.randomUUID().toString());
            list.add(task);
            return task;
        });

        ToDoList savedTask = toDoListService.addTaskToList(newTask);

        assertNotNull(savedTask.getId());
        assertEquals(savedTask.getTaskToBeDone(),"Do yoga morning at 8am");
        verify(toDoListRepository, times(1)).save(any(ToDoList.class));
    }

    @Test
    void testUpdateToDoList_Success(){
        when(toDoListRepository.findById(task2.getId())).thenReturn(list.stream().filter(item->item.getId().equals(task2.getId())).findFirst());
        when(toDoListRepository.save(any(ToDoList.class))).thenAnswer(invocation -> {
            ToDoList taskToSave = invocation.getArgument(0);
            int index = list.indexOf(list.stream().filter(item -> item.getId().equals(taskToSave.getId())).findFirst().orElse(null));
            list.set(index, taskToSave);
            return taskToSave;
        });
        ToDoList updateTask = new ToDoList();
        updateTask.setTask("Read a Book before sleeping");
        updateTask.setCompleted(false);

        Optional<ToDoList> result = toDoListService.updateToDoList(task2.getId(), updateTask);

        assertTrue(result.isPresent());
        assertEquals("Read a Book before sleeping", result.get().getTaskToBeDone());
        assertFalse(result.get().isCompleted());
        verify(toDoListRepository, times(1)).findById(task2.getId());
        verify(toDoListRepository, times(1)).save(any(ToDoList.class));
    }

    @Test
    void testUpdateToDoList_NotFound(){
        when(toDoListRepository.findById("Invalid_Id")).thenReturn(Optional.empty());

        Optional<ToDoList> result = toDoListService.updateToDoList("Invalid_Id", new ToDoList());

        assertFalse(result.isPresent());
        verify(toDoListRepository, times(1)).findById("Invalid_Id");
    }

    @Test
    void testDeleteToDoList_Success(){
        when(toDoListRepository.existsById(task1.getId())).thenReturn(true);
        doAnswer(invocation -> {
            String idToDelete = invocation.getArgument(0);
            list.removeIf(item -> item.getId().equals(idToDelete));
            return null; 
        }).when(toDoListRepository).deleteById(task1.getId());

        boolean isDeleted = toDoListService.deleteToDoList(task1.getId());

        assertTrue(isDeleted);
        verify(toDoListRepository, times(1)).existsById(task1.getId());
        verify(toDoListRepository, times(1)).deleteById(task1.getId());
    }

    @Test
    void testDeleteToDoList_NotFound(){
        when(toDoListRepository.existsById("Invalid_Id")).thenReturn(false);
        boolean isDeleted = toDoListService.deleteToDoList("Invalid_Id");
        assertFalse(isDeleted);
        verify(toDoListRepository, times(1)).existsById("Invalid_Id");
        verify(toDoListRepository, never()).deleteById(anyString());
    }
}
