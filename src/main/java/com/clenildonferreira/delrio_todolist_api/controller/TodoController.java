package com.clenildonferreira.delrio_todolist_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clenildonferreira.delrio_todolist_api.dto.TodoDTO;
import com.clenildonferreira.delrio_todolist_api.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getAllTodos() {
        return ResponseEntity.ok(todoService.getAllTodos());
    }

    @PostMapping
    public ResponseEntity<List<TodoDTO>> createTodos(@RequestBody List<TodoDTO> todoDTOs) {
        List<TodoDTO> created = todoService.create(todoDTOs);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping
    public ResponseEntity<List<TodoDTO>> updateTodos(@RequestBody List<TodoDTO> todoDTOs) {
        return ResponseEntity.ok(todoService.update(todoDTOs));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTodos(@RequestBody List<Long> todoIds) {
        todoService.delete(todoIds);
        return ResponseEntity.noContent().build();
    }
}