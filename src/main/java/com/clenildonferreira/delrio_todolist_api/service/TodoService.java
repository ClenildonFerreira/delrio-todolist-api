package com.clenildonferreira.delrio_todolist_api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        validateTitle(todoDTO.getTitle());
        
        if (todoDTO.getStatus() == null) {
            todoDTO.setStatus(StatusTarefa.ABERTA);
        }
        
        Todo saved = todoRepository.save(todoDTO.toEntity());
        return TodoDTO.fromEntity(saved);
    }

    public List<TodoDTO> getAllTodos() {
        Sort sort = Sort.by(
                Sort.Order.asc("priority"),
                Sort.Order.desc("status"),
                Sort.Order.asc("title"));
        return toDtoList(todoRepository.findAll(sort));
    }

    public TodoDTO update(Long id, TodoDTO todoDTO) {
        Todo existing = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada para o id informado."));

        merge(existing, todoDTO);
        validateTitle(existing.getTitle());

        Todo updated = todoRepository.save(existing);
        return TodoDTO.fromEntity(updated);
    }

    public void delete(Long todoId) {
        todoRepository.deleteById(todoId);
    }

    private void merge(Todo target, TodoDTO source) {
        if (source.getTitle() != null) {
            target.setTitle(source.getTitle());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getStatus() != null) {
            target.setStatus(source.getStatus());
        }
        if (source.getPriority() != null) {
            target.setPriority(source.getPriority());
        }
        if (source.getCreatedAt() != null) {
            target.setCreatedAt(source.getCreatedAt());
        }
    }

    private void validateTitle(String title) {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("O título é obrigatório e não pode ser vazio.");
        }
    }

    private List<TodoDTO> toDtoList(List<Todo> todos) {
        return todos.stream()
                .map(TodoDTO::fromEntity)
                .collect(Collectors.toList());
    }
}