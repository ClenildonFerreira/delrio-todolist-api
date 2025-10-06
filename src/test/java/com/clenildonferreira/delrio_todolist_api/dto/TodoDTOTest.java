package com.clenildonferreira.delrio_todolist_api.dto;

import com.clenildonferreira.delrio_todolist_api.entity.Todo;
import com.clenildonferreira.delrio_todolist_api.enums.StatusTarefa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TodoDTOTest {

    private TodoDTO todoDTO;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date();
        todoDTO = new TodoDTO();
        todoDTO.setId(1L);
        todoDTO.setTitle("Test Task");
        todoDTO.setDescription("Test Description");
        todoDTO.setStatus(StatusTarefa.ABERTA);
        todoDTO.setPriority(1);
        todoDTO.setCreatedAt(testDate);
    }

    @Test
    void constructor_shouldInitializeFields() {
        // When
        TodoDTO newTodoDTO = new TodoDTO(2L, "New Task", "New Description", StatusTarefa.EM_ANDAMENTO, 2, testDate);

        // Then
        assertEquals(2L, newTodoDTO.getId());
        assertEquals("New Task", newTodoDTO.getTitle());
        assertEquals("New Description", newTodoDTO.getDescription());
        assertEquals(StatusTarefa.EM_ANDAMENTO, newTodoDTO.getStatus());
        assertEquals(Integer.valueOf(2), newTodoDTO.getPriority());
        assertNotNull(newTodoDTO.getCreatedAt());
    }

    @Test
    void fromEntity_shouldConvertEntityToDTO() {
        // Given
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Entity Task");
        todo.setDescription("Entity Description");
        todo.setStatus(StatusTarefa.CONCLUIDA);
        todo.setPriority(0);
        todo.setCreatedAt(testDate);

        // When
        TodoDTO result = TodoDTO.fromEntity(todo);

        // Then
        assertNotNull(result);
        assertEquals(todo.getId(), result.getId());
        assertEquals(todo.getTitle(), result.getTitle());
        assertEquals(todo.getDescription(), result.getDescription());
        assertEquals(todo.getStatus(), result.getStatus());
        assertEquals(Integer.valueOf(todo.getPriority()), result.getPriority());
        assertEquals(todo.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void fromEntity_shouldReturnNullWhenEntityIsNull() {
        // When
        TodoDTO result = TodoDTO.fromEntity(null);

        // Then
        assertNull(result);
    }

    @Test
    void toEntity_shouldConvertDTOToEntity() {
        // When
        Todo result = todoDTO.toEntity();

        // Then
        assertNotNull(result);
        assertEquals(todoDTO.getId(), result.getId());
        assertEquals(todoDTO.getTitle(), result.getTitle());
        assertEquals(todoDTO.getDescription(), result.getDescription());
        assertEquals(todoDTO.getStatus(), result.getStatus());
        assertEquals(todoDTO.getPriority(), result.getPriority());
        assertEquals(todoDTO.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    void gettersAndSetters_shouldWorkCorrectly() {
        // Test getters
        assertEquals(1L, todoDTO.getId());
        assertEquals("Test Task", todoDTO.getTitle());
        assertEquals("Test Description", todoDTO.getDescription());
        assertEquals(StatusTarefa.ABERTA, todoDTO.getStatus());
        assertEquals(Integer.valueOf(1), todoDTO.getPriority());
        assertEquals(testDate, todoDTO.getCreatedAt());

        // Test setters
        Date newDate = new Date();
        todoDTO.setId(2L);
        todoDTO.setTitle("Updated Task");
        todoDTO.setDescription("Updated Description");
        todoDTO.setStatus(StatusTarefa.EM_ANDAMENTO);
        todoDTO.setPriority(3);
        todoDTO.setCreatedAt(newDate);

        assertEquals(2L, todoDTO.getId());
        assertEquals("Updated Task", todoDTO.getTitle());
        assertEquals("Updated Description", todoDTO.getDescription());
        assertEquals(StatusTarefa.EM_ANDAMENTO, todoDTO.getStatus());
        assertEquals(Integer.valueOf(3), todoDTO.getPriority());
        assertEquals(newDate, todoDTO.getCreatedAt());
    }

    @Test
    void getCreatedAt_shouldReturnCopyOfDate() {
        // When
        Date result = todoDTO.getCreatedAt();

        // Then
        assertNotNull(result);
        assertNotSame(testDate, result);
        assertEquals(testDate, result);
    }

    @Test
    void setCreatedAt_shouldHandleNullValues() {
        // When
        todoDTO.setCreatedAt(null);

        // Then
        assertNull(todoDTO.getCreatedAt());
    }
}