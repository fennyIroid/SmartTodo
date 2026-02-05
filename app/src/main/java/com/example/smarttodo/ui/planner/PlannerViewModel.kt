package com.example.smarttodo.ui.planner

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PlannerViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    var selectedDate by mutableStateOf(Calendar.getInstance())
        private set

    private val _tasksForSelectedDate = MutableStateFlow<List<TaskEntity>>(emptyList())
    val tasksForSelectedDate: StateFlow<List<TaskEntity>> = _tasksForSelectedDate.asStateFlow()

    private val _daysWithTasks = MutableStateFlow<Set<String>>(emptySet())
    val daysWithTasks: StateFlow<Set<String>> = _daysWithTasks.asStateFlow()

    init {
        loadTasksForDate(selectedDate)
        observeAllTasks()
    }

    fun onDateSelected(calendar: Calendar) {
        selectedDate = calendar
        loadTasksForDate(calendar)
    }

    private fun observeAllTasks() {
        viewModelScope.launch {
            repository.getTasks().collectLatest { allTasks ->
                val dates = allTasks.mapNotNull { task ->
                    task.dueTime?.split(", ")?.let { parts ->
                        if (parts.size >= 2) parts[0] + ", " + parts[1] else null
                    }
                }.toSet()
                _daysWithTasks.value = dates
            }
        }
    }

    private fun loadTasksForDate(calendar: Calendar) {
        viewModelScope.launch {
            repository.getTasks().collectLatest { allTasks ->
                val filtered = allTasks.filter { task: TaskEntity ->
                    isSameDay(task.dueTime, calendar)
                }
                _tasksForSelectedDate.value = filtered
            }
        }
    }

    private fun isSameDay(dueTime: String?, target: Calendar): Boolean {
        if (dueTime == null) return false
        return try {
            val parts = dueTime.split(", ")
            if (parts.size >= 2) {
                val datePart = parts[0] + ", " + parts[1] // "MMM dd, yyyy"
                val sdf = java.text.SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = sdf.parse(datePart)
                val cal = Calendar.getInstance()
                cal.time = date
                cal.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
                cal.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
            } else false
        } catch (e: Exception) {
            false
        }
    }
}
