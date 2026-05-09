package com.free.masaki.sakamoto.todoapp.domain.usecase

import com.free.masaki.sakamoto.todoapp.data.repository.TodoRepository
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import javax.inject.Inject

class UpdateTodoUseCase @Inject constructor(
    private val repository: TodoRepository,
) {
    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo.copy(updatedAt = System.currentTimeMillis()))
    }
}
