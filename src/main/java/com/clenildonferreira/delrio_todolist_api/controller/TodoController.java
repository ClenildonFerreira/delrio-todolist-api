package com.clenildonferreira.delrio_todolist_api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.clenildonferreira.delrio_todolist_api.dto.PagedResponseDTO;
import com.clenildonferreira.delrio_todolist_api.dto.TodoDTO;
import com.clenildonferreira.delrio_todolist_api.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/todos")
@Tag(name = "Tarefas", description = "Operações para gerenciamento de tarefas")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping(params = { "page", "size" })
    @Operation(summary = "Listar tarefas", description = "Obtém todas as tarefas cadastradas ordenadas por prioridade")
    @ApiResponse(responseCode = "200", description = "Lista de tarefas paginada retornada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PagedResponseDTO.class)))
    public ResponseEntity<PagedResponseDTO<TodoDTO>> getAllTodosPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PagedResponseDTO<TodoDTO> pagedResponse = todoService.getAllTodos(page, size);
        return ResponseEntity.ok(pagedResponse);
    }

    @PostMapping
    @Operation(summary = "Criar tarefa", description = "Cria uma nova tarefa a partir dos dados enviados")
    @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)))
    public ResponseEntity<TodoDTO> createTodo(@RequestBody TodoDTO todoDTO) {
        TodoDTO created = todoService.create(todoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualizar tarefa", description = "Atualiza parcialmente uma tarefa existente")
    @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso", content = @Content(mediaType = "application/json", schema = @Schema(implementation = TodoDTO.class)))
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    public ResponseEntity<TodoDTO> updateTodo(@PathVariable Long id, @RequestBody TodoDTO todoDTO) {
        return ResponseEntity.ok(todoService.update(id, todoDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir tarefa", description = "Remove uma tarefa existente")
    @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        todoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}