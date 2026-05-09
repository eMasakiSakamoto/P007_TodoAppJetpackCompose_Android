package com.free.masaki.sakamoto.todoapp.domain.usecase

import com.free.masaki.sakamoto.todoapp.domain.repository.TodoRepository
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import javax.inject.Inject

class GetTodoByIdUseCase @Inject constructor(
    private val repository: TodoRepository,
) {
    suspend operator fun invoke(todoId: Long): Todo? {
        return repository.getTodoById(todoId)
    }
}
