package com.example.smarttodo.data.local.converter

import androidx.room.TypeConverter
import com.example.smarttodo.data.bean.Priority

class PriorityConverter {

    @TypeConverter
    fun fromPriority(priority: Priority): String = priority.name

    @TypeConverter
    fun toPriority(value: String): Priority = Priority.valueOf(value)
}