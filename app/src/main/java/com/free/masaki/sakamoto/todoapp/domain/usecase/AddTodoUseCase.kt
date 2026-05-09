package com.free.masaki.sakamoto.todoapp.domain.usecase

import com.free.masaki.sakamoto.todoapp.data.repository.TodoRepository
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository,
) {
    suspend operator fun invoke(title: String, description: String) {
        val now = System.currentTimeMillis()
        repository.addTodo(
            Todo(
                title = title,
                description = description,
                createdAt = now,
                updatedAt = now,
            )
        )
    }
}
