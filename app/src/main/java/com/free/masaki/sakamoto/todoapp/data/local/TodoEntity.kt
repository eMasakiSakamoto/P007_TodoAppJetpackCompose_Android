package com.free.masaki.sakamoto.todoapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val description: String,
    val createdAt: Long,
    val updatedAt: Long,
    val isCompleted: Boolean,
    val isTrashed: Boolean,
)
