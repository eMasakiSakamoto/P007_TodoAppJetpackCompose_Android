package com.free.masaki.sakamoto.todoapp.data.mapper

import com.free.masaki.sakamoto.todoapp.data.local.TodoEntity
import com.free.masaki.sakamoto.todoapp.domain.model.Todo

fun TodoEntity.toDomain(): Todo = Todo(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isCompleted = isCompleted,
    isTrashed = isTrashed,
)

fun Todo.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    description = description,
    createdAt = createdAt,
    updatedAt = updatedAt,
    isCompleted = isCompleted,
    isTrashed = isTrashed,
)
