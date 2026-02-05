package com.example.smarttodo.ui.addtask

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttodo.data.bean.Priority
import com.example.smarttodo.ui.addtask.components.*
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    onCancel: () -> Unit = {},
    onDone: () -> Unit = {},
    viewModel: AddTaskViewModel = hiltViewModel()
) {
    var showRepeatDialog by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Date Picker Dialog
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = viewModel.selectedDateMillis
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let {
                        viewModel.onDateSelected(it)
                    }
                    showDatePicker = false
                    showTimePicker = true // Show time picker after date
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    // Time Picker Dialog
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = viewModel.selectedHour,
            initialMinute = viewModel.selectedMinute,
            is24Hour = false
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onTimeSelected(timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancel")
                }
            },
            title = { Text("Select Time") },
            text = {
                TimePicker(state = timePickerState)
            }
        )
    }

    if (showRepeatDialog) {
        val options = listOf("None", "Daily", "Weekly", "Monthly")
        AlertDialog(
            onDismissRequest = { showRepeatDialog = false },
            title = { Text("Select Repeat") },
            text = {
                Column {
                    options.forEach { option ->
                        TextButton(
                            onClick = {
                                viewModel.onRepeatChange(option)
                                showRepeatDialog = false
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(option, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            },
            confirmButton = {}
        )
    }

    AddTaskContent(
        title = viewModel.title,
        priority = viewModel.priority,
        category = viewModel.category,
        categories = viewModel.categories,
        dueTime = viewModel.dueTime ?: "Today, 5:00 PM",
        repeat = viewModel.repeat,
        isReminderEnabled = viewModel.isReminderEnabled,
        isEditMode = viewModel.isEditMode,
        onTitleChange = viewModel::onTitleChange,
        onPriorityChange = viewModel::onPriorityChange,
        onCategoryChange = viewModel::onCategoryChange,
        onAddCategory = viewModel::addCategory,
        onScheduleClick = { showDatePicker = true },
        onRepeatChange = { showRepeatDialog = true },
        onReminderToggle = viewModel::onReminderToggle,
        onCancel = onCancel,
        onDone = onDone,
        onCreateTask = { viewModel.createTaskFromState(onDone) }
    )
}

@Composable
fun AddTaskContent(
    title: String,
    priority: Priority,
    category: String,
    categories: List<String>,
    dueTime: String,
    repeat: String,
    isReminderEnabled: Boolean,
    isEditMode: Boolean,
    onTitleChange: (String) -> Unit,
    onPriorityChange: (Priority) -> Unit,
    onCategoryChange: (String) -> Unit,
    onAddCategory: (String) -> Unit,
    onScheduleClick: () -> Unit,
    onRepeatChange: () -> Unit,
    onReminderToggle: (Boolean) -> Unit,
    onCancel: () -> Unit = {},
    onDone: () -> Unit = {},
    onCreateTask: () -> Unit = {}
) {
    val isCreateEnabled = title.isNotBlank()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            // Top bar
            AddTaskTopBar(
                title = if (isEditMode) "Edit Task" else "New Task",
                onCancelClick = onCancel,
                onDoneClick = onCreateTask,
                isDoneEnabled = isCreateEnabled
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title & notes
            TaskTextInputSection(
                title = title,
                onTitleChange = onTitleChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Schedule
            ScheduleCard(
                value = dueTime,
                onClick = onScheduleClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Priority
            PrioritySlider(
                priority = priority,
                onPriorityChange = onPriorityChange
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Category
            CategorySelector(
                selectedCategory = category,
                categories = categories,
                onCategoryChange = onCategoryChange,
                onAddCategory = onAddCategory
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Footer
            AddTaskFooter(
                isCreateEnabled = isCreateEnabled,
                buttonText = if (isEditMode) "Update Task" else "Create Task",
                repeat = repeat,
                onRepeatClick = onRepeatChange,
                isReminderEnabled = isReminderEnabled,
                onReminderToggle = onReminderToggle,
                onCreateTaskClick = onCreateTask
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddTaskScreenLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        AddTaskContent(
            title = "",
            priority = Priority.MEDIUM,
            category = "Work",
            categories = listOf("Work", "Personal", "Health"),
            dueTime = "Today, 5:00 PM",
            repeat = "None",
            isReminderEnabled = false,
            isEditMode = false,
            onTitleChange = {},
            onPriorityChange = {},
            onCategoryChange = {},
            onAddCategory = {},
            onScheduleClick = {},
            onRepeatChange = {},
            onReminderToggle = {}
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun AddTaskScreenDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        AddTaskContent(
            title = "Buy Coffee",
            priority = Priority.HIGH,
            category = "Personal",
            categories = listOf("Work", "Personal", "Health"),
            dueTime = "Tomorrow, 8:00 AM",
            repeat = "Weekly",
            isReminderEnabled = true,
            isEditMode = true,
            onTitleChange = {},
            onPriorityChange = {},
            onCategoryChange = {},
            onAddCategory = {},
            onScheduleClick = {},
            onRepeatChange = {},
            onReminderToggle = {}
        )
    }
}
