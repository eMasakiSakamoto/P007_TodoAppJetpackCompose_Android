package com.free.masaki.sakamoto.todoapp.domain.model

data class Todo(
    val id: Long = 0,
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isCompleted: Boolean = false,
    val isTrashed: Boolean = false,
)
