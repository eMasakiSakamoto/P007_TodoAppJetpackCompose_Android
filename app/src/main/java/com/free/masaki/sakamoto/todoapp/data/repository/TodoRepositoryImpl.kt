package com.free.masaki.sakamoto.todoapp.data.repository

import com.free.masaki.sakamoto.todoapp.data.local.TodoDao
import com.free.masaki.sakamoto.todoapp.data.mapper.toDomain
import com.free.masaki.sakamoto.todoapp.data.mapper.toEntity
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import com.free.masaki.sakamoto.todoapp.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override fun observeActiveTodos(): Flow<List<Todo>> {
        return todoDao.observeActiveTodos().map { list -> list.map { it.toDomain() } }
    }

    override fun observeTrashedTodos(): Flow<List<Todo>> {
        return todoDao.observeTrashedTodos().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun getTodoById(id: Long): Todo? {
        return todoDao.getTodoById(id)?.toDomain()
    }

    override suspend fun addTodo(todo: Todo): Long {
        return todoDao.insert(todo.toEntity())
    }

    override suspend fun updateTodo(todo: Todo) {
        todoDao.update(todo.toEntity())
    }

    override suspend fun moveToTrash(todoId: Long) {
        val todo = todoDao.getTodoById(todoId) ?: return
        todoDao.update(todo.copy(isTrashed = true, updatedAt = System.currentTimeMillis()))
    }

    override suspend fun restoreTodo(todoId: Long) {
        val todo = todoDao.getTodoById(todoId) ?: return
        todoDao.update(todo.copy(isTrashed = false, updatedAt = System.currentTimeMillis()))
    }

    override suspend fun deleteTodoPermanently(todoId: Long) {
        todoDao.deleteById(todoId)
    }
}
