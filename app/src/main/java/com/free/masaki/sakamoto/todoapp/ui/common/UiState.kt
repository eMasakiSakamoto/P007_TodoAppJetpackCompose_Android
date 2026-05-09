package com.free.masaki.sakamoto.todoapp.ui.common

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val errorMessage: String? = null,
)
