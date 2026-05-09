package com.free.masaki.sakamoto.todoapp.presentation.trash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import com.free.masaki.sakamoto.todoapp.domain.usecase.DeleteTodoPermanentlyUseCase
import com.free.masaki.sakamoto.todoapp.domain.usecase.ObserveTrashedTodosUseCase
import com.free.masaki.sakamoto.todoapp.domain.usecase.RestoreTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TrashScreenState(
    val todos: List<Todo> = emptyList(),
    val showErrorDialog: Boolean = false,
    val errorMessage: String = "",
)

@HiltViewModel
class TrashViewModel @Inject constructor(
    private val observeTrashedTodosUseCase: ObserveTrashedTodosUseCase,
    private val restoreTodoUseCase: RestoreTodoUseCase,
    private val deleteTodoPermanentlyUseCase: DeleteTodoPermanentlyUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(TrashScreenState())
    val uiState: StateFlow<TrashScreenState> = _uiState

    init {
        observeTrashedTodos()
    }

    private fun observeTrashedTodos() {
        viewModelScope.launch {
            observeTrashedTodosUseCase()
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

    fun restore(todoId: Long) {
        viewModelScope.launch {
            try {
                restoreTodoUseCase(todoId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    showErrorDialog = true,
                    errorMessage = e.message ?: "復元に失敗しました",
                )
            }
        }
    }

    fun deletePermanently(todoId: Long) {
        viewModelScope.launch {
            try {
                deleteTodoPermanentlyUseCase(todoId)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    showErrorDialog = true,
                    errorMessage = e.message ?: "完全削除に失敗しました",
                )
            }
        }
    }

    fun dismissErrorDialog() {
        _uiState.value = _uiState.value.copy(
            showErrorDialog = false,
            errorMessage = "",
        )
    }
}
