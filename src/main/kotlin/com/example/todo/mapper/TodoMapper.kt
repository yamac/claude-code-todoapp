package com.example.todo.mapper

import com.example.todo.model.Todo
import org.apache.ibatis.annotations.Mapper

@Mapper
interface TodoMapper {
    fun findAll(): List<Todo>
    fun findById(id: Long): Todo?
    fun insert(todo: Todo): Int
    fun update(todo: Todo): Int
    fun delete(id: Long): Int
}