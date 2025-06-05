package com.example.todo.service

import com.example.todo.mapper.TodoMapper
import com.example.todo.model.Todo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class TodoService(private val todoMapper: TodoMapper) {

    fun getAllTodos(): List<Todo> {
        return todoMapper.findAll()
    }

    fun getTodoById(id: Long): Todo? {
        return todoMapper.findById(id)
    }

    fun createTodo(todo: Todo): Todo {
        val newTodo = todo.copy(
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        todoMapper.insert(newTodo)
        return newTodo
    }

    fun updateTodo(id: Long, todo: Todo): Todo? {
        val existingTodo = todoMapper.findById(id) ?: return null
        
        val updatedTodo = existingTodo.copy(
            title = todo.title,
            description = todo.description,
            completed = todo.completed,
            updatedAt = LocalDateTime.now()
        )
        
        todoMapper.update(updatedTodo)
        return updatedTodo
    }

    fun deleteTodo(id: Long): Boolean {
        return todoMapper.delete(id) > 0
    }
}