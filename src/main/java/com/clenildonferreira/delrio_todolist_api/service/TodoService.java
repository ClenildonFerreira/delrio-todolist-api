package com.clenildonferreira.delrio_todolist_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.clenildonferreira.delrio_todolist_api.dto.TodoDTO;
import com.clenildonferreira.delrio_todolist_api.entity.Todo;
import com.clenildonferreira.delrio_todolist_api.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public TodoDTO create(TodoDTO todoDTO) {
        Todo saved = todoRepository.save(todoDTO.toEntity());
        return TodoDTO.fromEntity(saved);
    }

    public List<TodoDTO> getAllTodos() {
        Sort sort = Sort.by(Sort.Order.desc("priority"), Sort.Order.asc("title"));
        return toDtoList(todoRepository.findAll(sort));
    }

    public TodoDTO update(TodoDTO todoDTO) {
        Todo updated = todoRepository.save(todoDTO.toEntity());
        return TodoDTO.fromEntity(updated);
    }

    public void delete(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    private List<TodoDTO> toDtoList(List<Todo> todos) {
        return todos.stream()
                .map(TodoDTO::fromEntity)
                .collect(Collectors.toList());
    }
}