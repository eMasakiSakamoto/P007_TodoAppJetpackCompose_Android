package com.free.masaki.sakamoto.todoapp.domain.usecase

import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import com.free.masaki.sakamoto.todoapp.domain.repository.TodoRepository
import javax.inject.Inject

class ToggleTodoCompletionUseCase @Inject constructor(
    private val repository: TodoRepository,
) {
    suspend operator fun invoke(todo: Todo) {
        repository.updateTodo(todo.copy(isCompleted = !todo.isCompleted))
    }
}
