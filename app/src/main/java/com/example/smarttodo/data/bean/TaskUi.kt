package com.example.smarttodo.data.bean

data class TaskUi(
    val id: Long = 0,
    val title: String,
    val description: String? = null,
    val priority: Priority,
    val category: String = "Work",
    val timeLabel: String,
    val isCompleted: Boolean
)

enum class Priority { HIGH, LOW, MEDIUM }
