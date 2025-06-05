package com.example.todo.controller

import com.example.todo.model.Todo
import com.example.todo.service.TodoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
class WebController(private val todoService: TodoService) {

    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("todos", todoService.getAllTodos())
        return "index"
    }

    @GetMapping("/todo/new")
    fun newTodoForm(model: Model): String {
        model.addAttribute("todo", Todo(title = "", description = ""))
        return "form"
    }

    @GetMapping("/todo/{id}/edit")
    fun editTodoForm(@PathVariable id: Long, model: Model): String {
        val todo = todoService.getTodoById(id)
        if (todo != null) {
            model.addAttribute("todo", todo)
            return "form"
        }
        return "redirect:/"
    }

    @PostMapping("/todo")
    fun createTodo(@ModelAttribute todo: Todo): String {
        todoService.createTodo(todo)
        return "redirect:/"
    }

    @PostMapping("/todo/{id}")
    fun updateTodo(@PathVariable id: Long, @ModelAttribute todo: Todo): String {
        todoService.updateTodo(id, todo)
        return "redirect:/"
    }

    @PostMapping("/todo/{id}/delete")
    fun deleteTodo(@PathVariable id: Long): String {
        todoService.deleteTodo(id)
        return "redirect:/"
    }

    @PostMapping("/todo/{id}/toggle")
    fun toggleTodo(@PathVariable id: Long): String {
        val todo = todoService.getTodoById(id)
        if (todo != null) {
            todoService.updateTodo(id, todo.copy(completed = !todo.completed))
        }
        return "redirect:/"
    }
}