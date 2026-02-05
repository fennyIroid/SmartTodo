package com.example.smarttodo.ui.addtask

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarttodo.data.bean.Priority
import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.data.repository.TaskRepository
import com.example.smarttodo.util.DateTimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(
    private val repository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: Long? = savedStateHandle.get<Long>("taskId")
    val isEditMode = taskId != null && taskId != -1L

    init {
        if (isEditMode) {
            loadTask()
        }
    }

    private fun loadTask() {
        viewModelScope.launch {
            taskId?.let { id ->
                repository.getTaskById(id).let { task ->
                    task?.let {
                        title = it.title
                        description = it.description
                        priority = it.priority
                        category = it.category
                        dueTime = it.dueTime
                        repeat = it.repeat
                        isReminderEnabled = it.isReminderEnabled
                    }
                }
            }
        }
    }

    /* ---------------- UI STATE ---------------- */

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf<String?>(null)
        private set

    var priority by mutableStateOf(Priority.MEDIUM)
        private set

    var category by mutableStateOf("Work")
        private set

    var dueTime by mutableStateOf<String?>(null)
        private set

    var repeat by mutableStateOf("None")
        private set

    var isReminderEnabled by mutableStateOf(false)
        private set

    var categories by mutableStateOf(listOf("Work", "Personal", "Health", "Home"))
        private set

    var selectedDateMillis by mutableStateOf(System.currentTimeMillis())
        private set

    var selectedHour by mutableStateOf(17) // 5 PM default
        private set

    var selectedMinute by mutableStateOf(0)
        private set

    /* ---------------- STATE UPDATERS ---------------- */

    fun onTitleChange(value: String) {
        title = value
    }

    fun onDescriptionChange(value: String?) {
        description = value
    }

    fun onPriorityChange(value: Priority) {
        priority = value
    }

    fun onCategoryChange(value: String) {
        category = value
    }

    fun onDueTimeChange(value: String?) {
        dueTime = value
    }

    fun onDateSelected(millis: Long) {
        selectedDateMillis = millis
        updateDueTimeFromState()
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        updateDueTimeFromState()
    }

    private fun updateDueTimeFromState() {
        dueTime = DateTimeUtils.formatToFullSchedule(selectedDateMillis, selectedHour, selectedMinute)
    }

    fun onRepeatChange(value: String) {
        repeat = value
    }

    fun onReminderToggle(value: Boolean) {
        isReminderEnabled = value
    }

    fun addCategory(name: String) {
        if (name.isNotBlank() && !categories.contains(name)) {
            categories = categories + name
            category = name
        }
    }

    /* ---------------- EXISTING WORKING LOGIC ---------------- */

    fun createTask(
        title: String,
        description: String?,
        priority: Priority,
        category: String,
        dueTime: String?
    ) {
        val task = TaskEntity(
            title = title,
            description = description,
            priority = priority,
            category = category,
            dueTime = dueTime
        )

        viewModelScope.launch {
            repository.addTask(task)
        }
    }

    /* ---------------- SAFE WRAPPER ---------------- */

    fun createTaskFromState(onDone: () -> Unit) {
        viewModelScope.launch {
            val task = if (isEditMode) {
                TaskEntity(
                    id = taskId!!,
                    title = title,
                    description = description,
                    priority = priority,
                    category = category,
                    dueTime = dueTime,
                    repeat = repeat,
                    isReminderEnabled = isReminderEnabled
                )
            } else {
                TaskEntity(
                    title = title,
                    description = description,
                    priority = priority,
                    category = category,
                    dueTime = dueTime,
                    repeat = repeat,
                    isReminderEnabled = isReminderEnabled
                )
            }
            
            if (isEditMode) {
                repository.updateTask(task)
            } else {
                repository.addTask(task)
            }
            onDone()
        }
    }
}
