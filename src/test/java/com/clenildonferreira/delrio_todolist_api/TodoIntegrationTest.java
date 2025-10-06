package com.clenildonferreira.delrio_todolist_api;

import com.clenildonferreira.delrio_todolist_api.dto.TodoDTO;
import com.clenildonferreira.delrio_todolist_api.enums.StatusTarefa;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void todoCrudOperations_shouldWorkCorrectly() throws Exception {
        TodoDTO newTodo = new TodoDTO();
        newTodo.setTitle("Integration Test Task");
        newTodo.setDescription("Test description for integration test");
        newTodo.setPriority(1);
        newTodo.setCreatedAt(new Date());

        String todoJson = objectMapper.writeValueAsString(newTodo);

        // POST 
        String createdTodoJson = mockMvc.perform(post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Integration Test Task"))
                .andExpect(jsonPath("$.status").value("ABERTA"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        TodoDTO createdTodo = objectMapper.readValue(createdTodoJson, TodoDTO.class);
        Long todoId = createdTodo.getId();

        // GET 
        mockMvc.perform(get("/todos")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        // PATCH 
        TodoDTO updatedTodo = new TodoDTO();
        updatedTodo.setTitle("Updated Integration Test Task");
        updatedTodo.setDescription("Updated description");
        updatedTodo.setStatus(StatusTarefa.EM_ANDAMENTO);
        updatedTodo.setPriority(2);

        String updatedTodoJson = objectMapper.writeValueAsString(updatedTodo);

        mockMvc.perform(patch("/todos/{id}", todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTodoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todoId))
                .andExpect(jsonPath("$.title").value("Updated Integration Test Task"))
                .andExpect(jsonPath("$.status").value("EM_ANDAMENTO"));

        // DELETE 
        mockMvc.perform(delete("/todos/{id}", todoId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/todos")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.totalElements").value(0));
    }
}