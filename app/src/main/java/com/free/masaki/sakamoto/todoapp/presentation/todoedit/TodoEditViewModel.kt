package com.free.masaki.sakamoto.todoapp.presentation.todoedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.free.masaki.sakamoto.todoapp.domain.model.Todo
import com.free.masaki.sakamoto.todoapp.domain.usecase.AddTodoUseCase
import com.free.masaki.sakamoto.todoapp.domain.usecase.GetTodoByIdUseCase
import com.free.masaki.sakamoto.todoapp.domain.usecase.UpdateTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TodoEditScreenState(
    val id: Long? = null,
    val title: String = "",
    val description: String = "",
    val isEditMode: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String = "",
    val shouldCloseScreen: Boolean = false,
)

@HiltViewModel
class TodoEditViewModel @Inject constructor(
    savedStateHandler: SavedStateHandle,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
) : ViewModel() {

    private val todoId: Long? = savedStateHandler["todoId"]

    private val _uiState = MutableStateFlow(TodoEditScreenState())
    val uiState: StateFlow<TodoEditScreenState> = _uiState

    init {
        if (todoId != null) {
            loadTodo(todoId)
        }
    }

    private fun loadTodo(id: Long) {
        viewModelScope.launch {
            try {
                val todo = getTodoByIdUseCase(id)
                if (todo != null) {
                    _uiState.value = _uiState.value.copy(
                        id = todo.id,
                        title = todo.title,
                        description = todo.description,
                        isEditMode = true,
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        showErrorDialog = true,
                        errorMessage = "タスクが見つかりません",
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    showErrorDialog = true,
                    errorMessage = e.message ?: "読み込みに失敗しました",
                )
            }
        }
    }

    fun updateTitle(value: String) {
        _uiState.value = _uiState.value.copy(title = value)
    }

    fun updateDescription(value: String) {
        _uiState.value = _uiState.value.copy(description = value)
    }

    fun saveTodo() {
        viewModelScope.launch {
            try {
                val current = _uiState.value
                if (current.title.isBlank()) {
                    _uiState.value = current.copy(
                        showErrorDialog = true,
                        errorMessage = "タスク名を入力してください",
                    )
                    return@launch
                }

                if (current.isEditMode && current.id != null) {
                    updateTodoUseCase(
                        Todo(
                            id = current.id,
                            title = current.title,
                            description = current.description,
                            createdAt = 0L,
                            updatedAt = System.currentTimeMillis(),
                        )
                    )
                } else {
                    addTodoUseCase(current.title, current.description)
                }

                _uiState.value = _uiState.value.copy(shouldCloseScreen = true)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    showErrorDialog = true,
                    errorMessage = e.message ?: "保存に失敗しました",
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

    fun clearCloseFlag() {
        _uiState.value = _uiState.value.copy(shouldCloseScreen = false)
    }
}
