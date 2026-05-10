package com.free.masaki.sakamoto.todoapp.presentation.todolist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import com.free.masaki.sakamoto.todoapp.domain.usecase.MoveTodoToTrashUseCase
import com.free.masaki.sakamoto.todoapp.domain.usecase.ObserveTodosUseCase
import com.free.masaki.sakamoto.todoapp.domain.usecase.ToggleTodoCompletionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoListScreenState(
    val todos: List<Todo> = emptyList(),
    val showErrorDialog: Boolean = false,
    val errorMessage: String = "",
)

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val observeTodosUseCase: ObserveTodosUseCase,
    private val moveTodoToTrashUseCase: MoveTodoToTrashUseCase,
    private val toggleTodoCompletionUseCase: ToggleTodoCompletionUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TodoListScreenState())
    val uiState: StateFlow<TodoListScreenState> = _uiState

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            observeTodosUseCase()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        showErrorDialog = true,
                        errorMessage = e.message ?: "不明なエラーが発生しました",
                    )
                }
                .collectLatest { todos ->
                    _uiState.value = _uiState.value.copy(todos = todos)
                }
        }
    }

    fun moveToTrash(todoId: Long) {
        viewModelScope.launch {
            try {
                moveTodoToTrashUseCase(todoId)
            } catch (e: Exception) {
                _uiState.value = uiState.value.copy(
                    showErrorDialog = true,
                    errorMessage = e.message ?: "ゴミ箱移動に失敗しました",
                )
            }
        }
    }

    fun onToggleCompletion(todo: Todo) {
        viewModelScope.launch {
            toggleTodoCompletionUseCase(todo)
        }
    }

    fun dismissErrorDialog() {
        _uiState.value = _uiState.value.copy(
            showErrorDialog = false,
            errorMessage = "",
        )
    }
}
