package com.free.masaki.sakamoto.todoapp.domain.usecase

import com.free.masaki.sakamoto.todoapp.data.repository.TodoRepository
import javax.inject.Inject

class MoveTodoToTrashUseCase @Inject constructor(
    private val repository: TodoRepository,
) {
    suspend operator fun invoke(todoId: Long) {
        repository.moveToTrash(todoId)
    }
}
