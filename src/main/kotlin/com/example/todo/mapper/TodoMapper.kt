package com.example.todo.mapper

import com.example.todo.model.Todo
import org.apache.ibatis.annotations.*

@Mapper
interface TodoMapper {
    @Select("""
        SELECT id, title, description, completed, sort_order, created_at, updated_at
        FROM todos
        ORDER BY sort_order ASC
    """)
    @Results(
        Result(property = "id", column = "id", id = true),
        Result(property = "title", column = "title"),
        Result(property = "description", column = "description"),
        Result(property = "completed", column = "completed"),
        Result(property = "sortOrder", column = "sort_order"),
        Result(property = "createdAt", column = "created_at"),
        Result(property = "updatedAt", column = "updated_at")
    )
    fun findAll(): List<Todo>
    
    @Select("""
        SELECT id, title, description, completed, sort_order, created_at, updated_at
        FROM todos
        WHERE id = #{id}
    """)
    @Results(
        Result(property = "id", column = "id", id = true),
        Result(property = "title", column = "title"),
        Result(property = "description", column = "description"),
        Result(property = "completed", column = "completed"),
        Result(property = "sortOrder", column = "sort_order"),
        Result(property = "createdAt", column = "created_at"),
        Result(property = "updatedAt", column = "updated_at")
    )
    fun findById(id: Long): Todo?
    
    @Insert("""
        INSERT INTO todos (title, description, completed, sort_order, created_at, updated_at)
        VALUES (#{title}, #{description}, #{completed}, #{sortOrder}, #{createdAt}, #{updatedAt})
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    fun insert(todo: Todo): Int
    
    @Update("""
        UPDATE todos
        SET title = #{title},
            description = #{description},
            completed = #{completed},
            sort_order = #{sortOrder},
            updated_at = #{updatedAt}
        WHERE id = #{id}
    """)
    fun update(todo: Todo): Int
    
    @Delete("DELETE FROM todos WHERE id = #{id}")
    fun delete(id: Long): Int
    
    @Update("""
        UPDATE todos 
        SET sort_order = #{sortOrder}
        WHERE id = #{id}
    """)
    fun updateSortOrder(id: Long, sortOrder: Int): Int
    
    @Select("SELECT COALESCE(MAX(sort_order), 0) + 1 FROM todos")
    fun getNextSortOrder(): Int
}