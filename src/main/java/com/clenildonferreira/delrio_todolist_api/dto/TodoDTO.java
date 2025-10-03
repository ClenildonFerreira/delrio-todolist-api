package com.clenildonferreira.delrio_todolist_api.dto;

import java.util.Date;

import com.clenildonferreira.delrio_todolist_api.entity.Todo;

public class TodoDTO {
    private Long id;
    private String title;
    private String description;
    private Boolean status;
    private Integer priority;
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
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