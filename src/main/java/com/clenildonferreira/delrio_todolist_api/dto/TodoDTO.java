package com.clenildonferreira.delrio_todolist_api.dto;

import java.util.Date;

import com.clenildonferreira.delrio_todolist_api.entity.Todo;
import com.clenildonferreira.delrio_todolist_api.enums.StatusTarefa;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Todo", description = "Representa uma tarefa da lista")
public class TodoDTO {
    @Schema(description = "Identificador único da tarefa", example = "1")
    private Long id;

    @Schema(description = "Título da tarefa", example = "Comprar mantimentos")
    private String title;

    @Schema(description = "Descrição detalhada", example = "Ir ao mercado e comprar itens da lista")
    private String description;

    @Schema(description = "Status de conclusão", example = "false")
    private StatusTarefa status;

    @Schema(description = "Prioridade da tarefa (0 é a mais alta)", example = "0")
    private Integer priority;

    @Schema(description = "Data de criação", example = "2025-01-15T10:30:00Z")
    private Date createdAt;

    public TodoDTO() {
    }

    public TodoDTO(Long id, String title, String description, Boolean status, Integer priority, Date createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        setCreatedAt(createdAt);
    }

    public static TodoDTO fromEntity(Todo todo) {
        if (todo == null) {
            return null;
        }
        Date createdAtCopy = todo.getCreatedAt() == null ? null : new Date(todo.getCreatedAt().getTime());
        return new TodoDTO(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isStatus(),
                todo.getPriority(),
                createdAtCopy);
    }

    public Todo toEntity() {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle(title);
        todo.setDescription(description);
        if (status != null) {
            todo.setStatus(status);
        }
        if (priority != null) {
            todo.setPriority(priority);
        }
        todo.setCreatedAt(createdAt == null ? null : new Date(createdAt.getTime()));
        return todo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public StatusTarefa getStatus() {
        return status;
    }

    public void setStatus(StatusTarefa status) {
        this.status = status;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreatedAt() {
        return createdAt == null ? null : new Date(createdAt.getTime());
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt == null ? null : new Date(createdAt.getTime());
    }
}