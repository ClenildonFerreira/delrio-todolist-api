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

    public List<TodoDTO> create(List<TodoDTO> todoDTOs) {
        return saveAll(todoDTOs);
    }

    public List<TodoDTO> getAllTodos() {
        Sort sort = Sort.by(Sort.Order.desc("priority"), Sort.Order.asc("title"));
        return toDtoList(todoRepository.findAll(sort));
    }

    public List<TodoDTO> update(List<TodoDTO> todoDTOs) {
        return saveAll(todoDTOs);
    }

    public List<TodoDTO> delete(List<Long> todoIds) {
        List<Todo> todosToDelete = todoRepository.findAllById(todoIds);
        List<TodoDTO> deletedDtos = toDtoList(todosToDelete);
        todoRepository.deleteAllById(todoIds);
        return deletedDtos;
    }

    private List<TodoDTO> saveAll(List<TodoDTO> todoDTOs) {
        List<Todo> todos = todoDTOs.stream()
                .map(TodoDTO::toEntity)
                .collect(Collectors.toList());

        List<Todo> persisted = todoRepository.saveAll(todos);
        return toDtoList(persisted);
    }

    private List<TodoDTO> toDtoList(List<Todo> todos) {
        return todos.stream()
                .map(TodoDTO::fromEntity)
                .collect(Collectors.toList());
    }
}