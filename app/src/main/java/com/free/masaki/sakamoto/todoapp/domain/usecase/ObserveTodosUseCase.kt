package com.free.masaki.sakamoto.todoapp.domain.usecase

import com.free.masaki.sakamoto.todoapp.domain.repository.TodoRepository
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTodosUseCase @Inject constructor(
    private val repository: TodoRepository,
) {
    operator fun invoke(): Flow<List<Todo>> = repository.observeActiveTodos()
}
