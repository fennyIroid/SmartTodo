package com.example.smarttodo.data.repository

import com.example.smarttodo.data.local.dao.TaskDao
import com.example.smarttodo.data.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(
    private val taskDao: TaskDao
) {

    fun getTasks(): Flow<List<TaskEntity>> {
        return taskDao.getAllTasks()
    }

    suspend fun getTaskById(id: Long): TaskEntity? {
        return taskDao.getTaskById(id)
    }

    fun getTaskByIdFlow(id: Long): Flow<TaskEntity?> {
        return taskDao.getTaskByIdFlow(id)
    }

    suspend fun addTask(task: TaskEntity) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: TaskEntity) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: TaskEntity) {
        taskDao.deleteTask(task)
    }
}