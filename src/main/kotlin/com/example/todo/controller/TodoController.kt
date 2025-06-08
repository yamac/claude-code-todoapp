package com.example.todo.controller

import com.example.todo.model.Todo
import com.example.todo.service.TodoService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.slf4j.LoggerFactory

@RestController
@RequestMapping("/api/todos")
class TodoController(private val todoService: TodoService) {
    
    private val logger = LoggerFactory.getLogger(TodoController::class.java)

    @GetMapping
    fun getAllTodos(): List<Todo> {
        return todoService.getAllTodos()
    }

    @GetMapping("/{id}")
    fun getTodoById(@PathVariable id: Long): ResponseEntity<Todo> {
        val todo = todoService.getTodoById(id)
        return if (todo != null) {
            ResponseEntity.ok(todo)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun createTodo(@RequestBody todo: Todo): ResponseEntity<Todo> {
        val createdTodo = todoService.createTodo(todo)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTodo)
    }

    @PutMapping("/{id}")
    fun updateTodo(@PathVariable id: Long, @RequestBody todo: Todo): ResponseEntity<Todo> {
        val updatedTodo = todoService.updateTodo(id, todo)
        return if (updatedTodo != null) {
            ResponseEntity.ok(updatedTodo)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTodo(@PathVariable id: Long): ResponseEntity<Void> {
        return if (todoService.deleteTodo(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
    
    @PostMapping("/reorder")
    fun reorderTodos(@RequestBody todoIds: List<Long>): ResponseEntity<Map<String, String>> {
        logger.info("Received reorder request for todos: $todoIds")
        return if (todoService.reorderTodos(todoIds)) {
            logger.info("Reorder successful")
            ResponseEntity.ok(mapOf("message" to "Reordered successfully"))
        } else {
            logger.error("Reorder failed")
            ResponseEntity.badRequest().body(mapOf("error" to "Failed to reorder todos"))
        }
    }
}