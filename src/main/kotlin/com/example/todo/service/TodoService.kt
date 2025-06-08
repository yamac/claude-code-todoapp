package com.example.todo.service

import com.example.todo.mapper.TodoMapper
import com.example.todo.model.Todo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import org.slf4j.LoggerFactory

@Service
@Transactional
class TodoService(private val todoMapper: TodoMapper) {
    
    private val logger = LoggerFactory.getLogger(TodoService::class.java)

    fun getAllTodos(): List<Todo> {
        return todoMapper.findAll()
    }

    fun getTodoById(id: Long): Todo? {
        return todoMapper.findById(id)
    }

    fun createTodo(todo: Todo): Todo {
        // Increment all existing sort orders to make room at the top
        todoMapper.incrementAllSortOrders()
        
        // New todo gets sort order 1 (top position)
        val newTodo = todo.copy(
            sortOrder = 1,
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
            sortOrder = todo.sortOrder,
            updatedAt = LocalDateTime.now()
        )
        
        todoMapper.update(updatedTodo)
        return updatedTodo
    }

    fun deleteTodo(id: Long): Boolean {
        return todoMapper.delete(id) > 0
    }
    
    fun reorderTodos(todoIds: List<Long>): Boolean {
        logger.info("Reordering todos: $todoIds")
        return try {
            todoIds.forEachIndexed { index, todoId ->
                val newSortOrder = index + 1
                logger.debug("Setting todo $todoId to sort order $newSortOrder")
                val result = todoMapper.updateSortOrder(todoId, newSortOrder)
                logger.debug("Update result for todo $todoId: $result")
            }
            logger.info("Successfully reordered ${todoIds.size} todos")
            true
        } catch (e: Exception) {
            logger.error("Failed to reorder todos", e)
            false
        }
    }
}