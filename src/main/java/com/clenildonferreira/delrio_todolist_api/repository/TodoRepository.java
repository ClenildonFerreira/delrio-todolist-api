package com.clenildonferreira.delrio_todolist_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clenildonferreira.delrio_todolist_api.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
}
