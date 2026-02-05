package com.example.smarttodo.di

import com.example.smarttodo.data.local.dao.TaskDao
import com.example.smarttodo.data.repository.TaskRepository
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository =
        TaskRepository(taskDao)
}