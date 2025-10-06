package com.clenildonferreira.delrio_todolist_api.repository;

import com.clenildonferreira.delrio_todolist_api.entity.Todo;
import com.clenildonferreira.delrio_todolist_api.enums.StatusTarefa;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TodoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    void findById_shouldReturnTodoWhenExists() {
        // Given
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
        todo.setStatus(StatusTarefa.ABERTA);
        todo.setPriority(1);
        todo.setCreatedAt(new Date());
        
        Todo savedTodo = entityManager.persistAndFlush(todo);

        // When
        Optional<Todo> foundTodo = todoRepository.findById(savedTodo.getId());

        // Then
        assertThat(foundTodo).isPresent();
        assertThat(foundTodo.get().getTitle()).isEqualTo("Test Todo");
        assertThat(foundTodo.get().getDescription()).isEqualTo("Test Description");
    }

    @Test
    void findById_shouldReturnEmptyWhenNotExists() {
        // When
        Optional<Todo> foundTodo = todoRepository.findById(999L);

        // Then
        assertThat(foundTodo).isEmpty();
    }

    @Test
    void save_shouldPersistTodo() {
        // Given
        Todo todo = new Todo();
        todo.setTitle("New Todo");
        todo.setDescription("New Description");
        todo.setStatus(StatusTarefa.EM_ANDAMENTO);
        todo.setPriority(2);
        todo.setCreatedAt(new Date());

        // When
        Todo savedTodo = todoRepository.save(todo);

        // Then
        assertThat(savedTodo.getId()).isNotNull();
        assertThat(todoRepository.findById(savedTodo.getId())).isPresent();
    }

    @Test
    void deleteById_shouldRemoveTodo() {
        // Given
        Todo todo = new Todo();
        todo.setTitle("To Delete");
        todo.setPriority(0);
        todo.setCreatedAt(new Date());
        
        Todo savedTodo = entityManager.persistAndFlush(todo);

        // When
        todoRepository.deleteById(savedTodo.getId());

        // Then
        assertThat(todoRepository.findById(savedTodo.getId())).isEmpty();
    }
}