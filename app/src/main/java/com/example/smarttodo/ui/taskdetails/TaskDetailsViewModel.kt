package com.example.smarttodo.ui.taskdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailsViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Long = checkNotNull(savedStateHandle["taskId"])

    val task: StateFlow<TaskEntity?> = repository.getTaskByIdFlow(taskId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    fun markAsComplete() {
        viewModelScope.launch {
            task.value?.let {
                repository.updateTask(it.copy(isCompleted = true))
            }
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            task.value?.let {
                repository.deleteTask(it)
                // Navigation handled in Screen
            }
        }
    }
}
