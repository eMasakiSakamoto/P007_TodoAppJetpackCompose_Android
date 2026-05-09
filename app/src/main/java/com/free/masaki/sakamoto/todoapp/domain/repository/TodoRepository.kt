package com.free.masaki.sakamoto.todoapp.domain.repository

import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun observeActiveTodos(): Flow<List<Todo>>
    fun observeTrashedTodos(): Flow<List<Todo>>

    suspend fun getTodoById(id: Long): Todo?
    suspend fun addTodo(todo: Todo): Long
    suspend fun updateTodo(todo: Todo)
    suspend fun moveToTrash(todoId: Long)
    suspend fun restoreTodo(todoId: Long)
    suspend fun deleteTodoPermanently(todoId: Long)
}