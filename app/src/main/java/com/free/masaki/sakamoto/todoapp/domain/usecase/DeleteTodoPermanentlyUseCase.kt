package com.free.masaki.sakamoto.todoapp.domain.usecase

import com.free.masaki.sakamoto.todoapp.domain.repository.TodoRepository
import javax.inject.Inject

class DeleteTodoPermanentlyUseCase @Inject constructor(
    private val repository: TodoRepository,
) {
    suspend operator fun invoke(todoId: Long) {
        repository.deleteTodoPermanently(todoId)
    }
}
