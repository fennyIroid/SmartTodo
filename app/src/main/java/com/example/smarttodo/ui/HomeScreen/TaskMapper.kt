package com.example.smarttodo.ui.HomeScreen

import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.data.bean.TaskUi
import com.example.smarttodo.util.DateTimeUtils

fun TaskEntity.toTaskUi(): TaskUi =
    TaskUi(
        id = id,
        title = title,
        description = description,
        priority = priority,
        category = category,
        timeLabel = DateTimeUtils.getRelativeLabel(dueTime),
        isCompleted = isCompleted
    )