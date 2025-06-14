package com.example.todo.model

import java.time.LocalDateTime

data class Todo(
    val id: Long? = null,
    val title: String,
    val description: String? = null,
    val completed: Boolean = false,
    val sortOrder: Int = 0,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)