package com.example.todo.controller

import com.example.todo.model.Todo
import com.example.todo.service.TodoService
import com.fasterxml.jackson.databind.ObjectMapper
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDateTime

@WebMvcTest(TodoController::class)
class TodoControllerTest {

    @TestConfiguration
    class TestConfig {
        @Bean
        fun todoService() = mockk<TodoService>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var todoService: TodoService

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val testTodo = Todo(
        id = 1,
        title = "Test Todo",
        description = "Test Description",
        completed = false,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    @Test
    fun `should return all todos`() {
        val todos = listOf(testTodo)
        every { todoService.getAllTodos() } returns todos

        mockMvc.perform(get("/api/todos"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].title").value("Test Todo"))

        verify { todoService.getAllTodos() }
    }

    @Test
    fun `should return todo by id`() {
        every { todoService.getTodoById(1) } returns testTodo

        mockMvc.perform(get("/api/todos/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.title").value("Test Todo"))

        verify { todoService.getTodoById(1) }
    }

    @Test
    fun `should return 404 when todo not found`() {
        every { todoService.getTodoById(999) } returns null

        mockMvc.perform(get("/api/todos/999"))
            .andExpect(status().isNotFound)

        verify { todoService.getTodoById(999) }
    }

    @Test
    fun `should create new todo`() {
        val newTodo = testTodo.copy(id = null)
        every { todoService.createTodo(any()) } returns testTodo

        mockMvc.perform(
            post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newTodo))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Test Todo"))

        verify { todoService.createTodo(any()) }
    }

    @Test
    fun `should update existing todo`() {
        val updatedTodo = testTodo.copy(title = "Updated Todo")
        every { todoService.updateTodo(1, any()) } returns updatedTodo

        mockMvc.perform(
            put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedTodo))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Updated Todo"))

        verify { todoService.updateTodo(1, any()) }
    }

    @Test
    fun `should delete todo`() {
        every { todoService.deleteTodo(1) } returns true

        mockMvc.perform(delete("/api/todos/1"))
            .andExpect(status().isNoContent)

        verify { todoService.deleteTodo(1) }
    }

    @Test
    fun `should return 404 when deleting non-existent todo`() {
        every { todoService.deleteTodo(999) } returns false

        mockMvc.perform(delete("/api/todos/999"))
            .andExpect(status().isNotFound)

        verify { todoService.deleteTodo(999) }
    }
}