package com.example.smarttodo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smarttodo.data.local.converter.PriorityConverter
import com.example.smarttodo.data.local.dao.TaskDao
import com.example.smarttodo.data.local.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(PriorityConverter::class)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}