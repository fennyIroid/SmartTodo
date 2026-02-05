package com.example.smarttodo.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smarttodo.data.bean.Priority

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String,
    val description: String?,
    val priority: Priority,
    val category: String,
    val dueTime: String?,
    val repeat: String = "None",
    val isReminderEnabled: Boolean = false,
    val isCompleted: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)