package com.clenildonferreira.delrio_todolist_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.clenildonferreira.delrio_todolist_api.dto.TodoDTO;
import com.clenildonferreira.delrio_todolist_api.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<TodoDTO> create(List<TodoDTO> todoDTOs) {
        List<Todo> todos = todoDTOs.stream()
                .map(TodoDTO::toEntity)
                .collect(Collectors.toList());
        
        List<Todo> savedTodos = todoRepository.saveAll(todos);
        
        return savedTodos.stream()
                .map(TodoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> getAllTodos() {
        Sort sort = Sort.by(Sort.Order.desc("priority"), Sort.Order.asc("title"));
        
        return todoRepository.findAll(sort)
                .stream()
                .map(TodoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> update(List<TodoDTO> todoDTOs) {
        List<Todo> todos = todoDTOs.stream()
                .map(TodoDTO::toEntity)
                .collect(Collectors.toList());
        
        List<Todo> updatedTodos = todoRepository.saveAll(todos);
        
        return updatedTodos.stream()
                .map(TodoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public List<TodoDTO> delete(List<Long> todoIds) {
        todoRepository.deleteAllById(todoIds);
        return todoIds.stream()
                .map(id -> new TodoDTO(id, "Deleted"))
                .collect(Collectors.toList());
    }
}
