package com.clenildonferreira.delrio_todolist_api.service;

import com.clenildonferreira.delrio_todolist_api.dto.PagedResponseDTO;
import com.clenildonferreira.delrio_todolist_api.dto.TodoDTO;
import com.clenildonferreira.delrio_todolist_api.entity.Todo;
import com.clenildonferreira.delrio_todolist_api.enums.StatusTarefa;
import com.clenildonferreira.delrio_todolist_api.repository.TodoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    private Todo sampleTodo;
    private TodoDTO sampleTodoDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        sampleTodo = new Todo();
        sampleTodo.setId(1L);
        sampleTodo.setTitle("Test Task");
        sampleTodo.setDescription("Test Description");
        sampleTodo.setStatus(StatusTarefa.ABERTA);
        sampleTodo.setPriority(1);
        sampleTodo.setCreatedAt(new Date());
        
        sampleTodoDTO = new TodoDTO();
        sampleTodoDTO.setId(1L);
        sampleTodoDTO.setTitle("Test Task");
        sampleTodoDTO.setDescription("Test Description");
        sampleTodoDTO.setStatus(StatusTarefa.ABERTA);
        sampleTodoDTO.setPriority(1);
        sampleTodoDTO.setCreatedAt(new Date());
    }

    @Test
    void create_shouldCreateTodoWithDefaultStatus() {
        // Given
        TodoDTO todoWithoutStatus = new TodoDTO();
        todoWithoutStatus.setTitle("Task without status");
        todoWithoutStatus.setDescription("Description");
        todoWithoutStatus.setPriority(0);
        
        Todo savedTodo = new Todo();
        savedTodo.setId(1L);
        savedTodo.setTitle("Task without status");
        savedTodo.setDescription("Description");
        savedTodo.setStatus(StatusTarefa.ABERTA); // Default status
        savedTodo.setPriority(0);
        savedTodo.setCreatedAt(new Date());
        
        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        // When
        TodoDTO result = todoService.create(todoWithoutStatus);

        // Then
        assertNotNull(result);
        assertEquals(StatusTarefa.ABERTA, result.getStatus());
        assertEquals("Task without status", result.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void create_shouldCreateTodoWithProvidedStatus() {
        // Given
        TodoDTO todoWithStatus = new TodoDTO();
        todoWithStatus.setTitle("Task with status");
        todoWithStatus.setDescription("Description");
        todoWithStatus.setStatus(StatusTarefa.EM_ANDAMENTO);
        todoWithStatus.setPriority(1);
        
        Todo savedTodo = new Todo();
        savedTodo.setId(1L);
        savedTodo.setTitle("Task with status");
        savedTodo.setDescription("Description");
        savedTodo.setStatus(StatusTarefa.EM_ANDAMENTO);
        savedTodo.setPriority(1);
        savedTodo.setCreatedAt(new Date());
        
        when(todoRepository.save(any(Todo.class))).thenReturn(savedTodo);

        // When
        TodoDTO result = todoService.create(todoWithStatus);

        // Then
        assertNotNull(result);
        assertEquals(StatusTarefa.EM_ANDAMENTO, result.getStatus());
        assertEquals("Task with status", result.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void getAllTodos_shouldReturnPagedTodos() {
        // Given
        Pageable pageable = PageRequest.of(0, 10);
        Page<Todo> todoPage = new PageImpl<>(Arrays.asList(sampleTodo), pageable, 1);
        
        when(todoRepository.findAll(pageable)).thenReturn(todoPage);

        // When
        PagedResponseDTO<TodoDTO> result = todoService.getAllTodos(0, 10);

        // Then
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(0, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(1, result.getTotalElements());
        verify(todoRepository, times(1)).findAll(pageable);
    }

    @Test
    void update_shouldUpdateExistingTodo() {
        // Given
        TodoDTO updatedTodoDTO = new TodoDTO();
        updatedTodoDTO.setTitle("Updated Task");
        updatedTodoDTO.setDescription("Updated Description");
        updatedTodoDTO.setStatus(StatusTarefa.CONCLUIDA);
        updatedTodoDTO.setPriority(2);
        
        Todo existingTodo = new Todo();
        existingTodo.setId(1L);
        existingTodo.setTitle("Original Task");
        existingTodo.setDescription("Original Description");
        existingTodo.setStatus(StatusTarefa.ABERTA);
        existingTodo.setPriority(1);
        existingTodo.setCreatedAt(new Date());
        
        Todo updatedTodo = new Todo();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated Task");
        updatedTodo.setDescription("Updated Description");
        updatedTodo.setStatus(StatusTarefa.CONCLUIDA);
        updatedTodo.setPriority(2);
        updatedTodo.setCreatedAt(new Date());
        
        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(updatedTodo);

        // When
        TodoDTO result = todoService.update(1L, updatedTodoDTO);

        // Then
        assertNotNull(result);
        assertEquals("Updated Task", result.getTitle());
        assertEquals(StatusTarefa.CONCLUIDA, result.getStatus());
        assertEquals(2, result.getPriority());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).save(existingTodo);
    }

    @Test
    void update_shouldThrowExceptionWhenTodoNotFound() {
        // Given
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> todoService.update(1L, sampleTodoDTO)
        );
        
        assertEquals("Tarefa não encontrada para o id informado.", exception.getMessage());
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, never()).save(any(Todo.class));
    }

    @Test
    void delete_shouldDeleteTodo() {
        // Given
        doNothing().when(todoRepository).deleteById(1L);

        // When
        todoService.delete(1L);

        // Then
        verify(todoRepository, times(1)).deleteById(1L);
    }
}