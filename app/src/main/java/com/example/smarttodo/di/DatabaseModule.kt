package com.example.smarttodo.di

import android.content.Context
import androidx.room.Room
import com.example.smarttodo.data.local.dao.TaskDao
import com.example.smarttodo.data.local.database.TaskDatabase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.Provides
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): TaskDatabase =
        Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            "task_db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideTaskDao(db: TaskDatabase): TaskDao =
        db.taskDao()
}