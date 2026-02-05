package com.example.smarttodo.ui.stats

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttodo.data.bean.Priority
import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _completionRate = MutableStateFlow(0f)
    val completionRate: StateFlow<Float> = _completionRate.asStateFlow()

    private val _finishedTasksCount = MutableStateFlow(0)
    val finishedTasksCount: StateFlow<Int> = _finishedTasksCount.asStateFlow()

    private val _totalTasksCount = MutableStateFlow(0)
    val totalTasksCount: StateFlow<Int> = _totalTasksCount.asStateFlow()

    private val _weeklyActivity = MutableStateFlow<List<Int>>(listOf(0, 0, 0, 0, 0, 0, 0))
    val weeklyActivity: StateFlow<List<Int>> = _weeklyActivity.asStateFlow()

    private val _currentStreak = MutableStateFlow(0)
    val currentStreak: StateFlow<Int> = _currentStreak.asStateFlow()

    private val _focusTime = MutableStateFlow(0f)
    val focusTime: StateFlow<Float> = _focusTime.asStateFlow()

    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            repository.getTasks().collectLatest { allTasks ->
                calculateTodayMetrics(allTasks)
                calculateWeeklyActivity(allTasks)
                calculateStreak(allTasks)
                calculateFocusTime(allTasks)
                calculateAchievements(allTasks)
            }
        }
    }

    private fun calculateTodayMetrics(allTasks: List<TaskEntity>) {
        val today = Calendar.getInstance()
        val targetTasks = allTasks.filter { 
            it.dueTime == null || isSameDay(it.dueTime, today)
        }
        
        val finished = targetTasks.count { it.isCompleted }
        val total = targetTasks.size
        
        _totalTasksCount.value = total
        _finishedTasksCount.value = finished
        _completionRate.value = if (total > 0) {
            finished.toFloat() / total
        } else 0f
    }

    private fun calculateWeeklyActivity(allTasks: List<TaskEntity>) {
        val counts = mutableListOf<Int>()
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -6) // Start from 7 days ago

        for (i in 0..6) {
            val count = allTasks.count { task ->
                if (task.dueTime != null) {
                    isSameDay(task.dueTime, cal)
                } else {
                    isSameDay(task.createdAt, cal)
                }
            }
            counts.add(count)
            cal.add(Calendar.DAY_OF_YEAR, 1)
        }
        _weeklyActivity.value = counts
    }

    private fun calculateStreak(allTasks: List<TaskEntity>) {
        // Group completed tasks by day string "yyyy-D"
        val completedDates = allTasks.filter { it.isCompleted }
            .mapNotNull { task ->
                getCalendarForTask(task)?.let { cal ->
                    "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.DAY_OF_YEAR)}"
                }
            }.toSet()

        if (completedDates.isEmpty()) {
            _currentStreak.value = 0
            return
        }

        var streak = 0
        val cal = Calendar.getInstance()
        
        // Check if we have a streak continuing from today OR yesterday
        val todayKey = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.DAY_OF_YEAR)}"
        
        cal.add(Calendar.DAY_OF_YEAR, -1)
        val yesterdayKey = "${cal.get(Calendar.YEAR)}-${cal.get(Calendar.DAY_OF_YEAR)}"

        // Reset cal to start checking
        val checkCal = Calendar.getInstance()
        
        // If we missed yesterday and today, streak is 0
        if (!completedDates.contains(todayKey) && !completedDates.contains(yesterdayKey)) {
             _currentStreak.value = 0
             return
        }

        // Start checking backwards from Today if Today is done, else start from Yesterday
        if (!completedDates.contains(todayKey)) {
             checkCal.add(Calendar.DAY_OF_YEAR, -1) // Start from yesterday
        }

        while (true) {
            val key = "${checkCal.get(Calendar.YEAR)}-${checkCal.get(Calendar.DAY_OF_YEAR)}"
            if (completedDates.contains(key)) {
                streak++
                checkCal.add(Calendar.DAY_OF_YEAR, -1)
            } else {
                break
            }
        }
        _currentStreak.value = streak
    }

    private fun calculateFocusTime(allTasks: List<TaskEntity>) {
        val today = Calendar.getInstance()
        val todayCompletedTasks = allTasks.filter { 
            it.isCompleted && (it.dueTime == null || isSameDay(it.dueTime, today))
        }

        // Heuristic: High = 60m, Med = 30m, Low = 15m
        var minutes = 0
        todayCompletedTasks.forEach { task ->
            minutes += when (task.priority) {
                Priority.HIGH -> 60
                Priority.MEDIUM -> 30
                Priority.LOW -> 15
            }
        }
        _focusTime.value = minutes / 60f
    }

    private fun calculateAchievements(allTasks: List<TaskEntity>) {
        val list = mutableListOf<Achievement>()
        val completedCount = allTasks.count { it.isCompleted }
        
        if (completedCount >= 1) {
            list.add(Achievement("First Steps", "Completed your first task!"))
        }
        if (completedCount >= 5) {
             list.add(Achievement("Getting Serious", "Completed 5 tasks."))
        }
        if (completedCount >= 10) {
            list.add(Achievement("Productivity Master", "Completed 10 tasks."))
        }
        if (_currentStreak.value >= 3) {
            list.add(Achievement("On Fire", "3 day streak!"))
        }

        _achievements.value = list
    }

    // Helper to get Calendar for a task (dueTime or createdAt)
    private fun getCalendarForTask(task: TaskEntity): Calendar? {
        if (task.dueTime != null) {
             // Parse dueTime
             return parseDateString(task.dueTime)
        } else {
            val c = Calendar.getInstance()
            c.timeInMillis = task.createdAt
            return c
        }
    }

    private fun parseDateString(dateStr: String): Calendar? {
        if (dateStr.startsWith("Today")) return Calendar.getInstance()
        if (dateStr.startsWith("Tomorrow")) return Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }
        
        return try {
            val parts = dateStr.split(", ")
            if (parts.size >= 2) {
                val datePart = parts[0] + ", " + parts[1]
                val sdf = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                val date = sdf.parse(datePart)
                if (date != null) {
                    Calendar.getInstance().apply { time = date }
                } else null
            } else null
        } catch (e: Exception) { null }
    }

    private fun isSameDay(dueTime: String?, target: Calendar): Boolean {
        if (dueTime == null) return false
        val taskCal = parseDateString(dueTime) ?: return false
        return taskCal.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
               taskCal.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
    }

    private fun isSameDay(timestamp: Long, target: Calendar): Boolean {
        val cal = Calendar.getInstance()
        cal.timeInMillis = timestamp
        return cal.get(Calendar.YEAR) == target.get(Calendar.YEAR) &&
               cal.get(Calendar.DAY_OF_YEAR) == target.get(Calendar.DAY_OF_YEAR)
    }
}

data class Achievement(val title: String, val description: String)
