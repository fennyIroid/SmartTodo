package com.example.smarttodo.ui.HomeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    // Exposed to UI
    val tasks: StateFlow<List<TaskEntity>> =
        repository.getTasks()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    fun toggleTaskCompletion(task: TaskEntity) {
        viewModelScope.launch {
            repository.addTask(task.copy(isCompleted = !task.isCompleted))
        }
    }
}