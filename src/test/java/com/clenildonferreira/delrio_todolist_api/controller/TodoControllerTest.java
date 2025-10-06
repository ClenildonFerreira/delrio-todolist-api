package com.clenildonferreira.delrio_todolist_api.controller;

import com.clenildonferreira.delrio_todolist_api.dto.PagedResponseDTO;
import com.clenildonferreira.delrio_todolist_api.dto.TodoDTO;
import com.clenildonferreira.delrio_todolist_api.enums.StatusTarefa;
import com.clenildonferreira.delrio_todolist_api.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @Autowired
    private ObjectMapper objectMapper;

    private TodoDTO sampleTodoDTO;

    @BeforeEach
    void setUp() {
        sampleTodoDTO = new TodoDTO();
        sampleTodoDTO.setId(1L);
        sampleTodoDTO.setTitle("Test Task");
        sampleTodoDTO.setDescription("Test Description");
        sampleTodoDTO.setStatus(StatusTarefa.ABERTA);
        sampleTodoDTO.setPriority(1);
        sampleTodoDTO.setCreatedAt(new Date());
    }

    @Test
    void getAllTodosPaginated_shouldReturnPagedTodos() throws Exception {
        // Given
        PagedResponseDTO<TodoDTO> pagedResponse = new PagedResponseDTO<>(
                Arrays.asList(sampleTodoDTO),
                0, 10, 1, 1, true, true);
        
        when(todoService.getAllTodos(0, 10)).thenReturn(pagedResponse);

        // When & Then
        mockMvc.perform(get("/todos")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Test Task"))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10));
    }

    @Test
    void createTodo_shouldCreateAndReturnTodo() throws Exception {
        // Given
        when(todoService.create(any(TodoDTO.class))).thenReturn(sampleTodoDTO);

        // When & Then
        mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleTodoDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Test Task"));
    }

    @Test
    void updateTodo_shouldUpdateAndReturnTodo() throws Exception {
        // Given
        TodoDTO updatedTodo = new TodoDTO();
        updatedTodo.setId(1L);
        updatedTodo.setTitle("Updated Task");
        updatedTodo.setDescription("Updated Description");
        updatedTodo.setStatus(StatusTarefa.EM_ANDAMENTO);
        updatedTodo.setPriority(2);
        updatedTodo.setCreatedAt(new Date());
        
        when(todoService.update(anyLong(), any(TodoDTO.class))).thenReturn(updatedTodo);

        // When & Then
        mockMvc.perform(patch("/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated Task"))
                .andExpect(jsonPath("$.status").value("EM_ANDAMENTO"));
    }

    @Test
    void deleteTodo_shouldDeleteTodo() throws Exception {
        // Given
        doNothing().when(todoService).delete(anyLong());

        // When & Then
        mockMvc.perform(delete("/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}