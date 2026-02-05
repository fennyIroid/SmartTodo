package com.example.smarttodo.ui.taskdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.smarttodo.R
import com.example.smarttodo.data.local.entity.TaskEntity
import com.example.smarttodo.data.bean.Priority

@Composable
fun TaskDetailsScreen(
    onBack: () -> Unit,
    onEdit: (Long) -> Unit,
    viewModel: TaskDetailsViewModel = hiltViewModel()
) {
    val task by viewModel.task.collectAsState()

    task?.let {
        TaskDetailsContent(
            task = it,
            onBack = onBack,
            onEdit = { onEdit(it.id) },
            onComplete = { 
                viewModel.markAsComplete()
                onBack()
            },
            onDelete = {
                viewModel.deleteTask()
                onBack()
            }
        )
    } ?: Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun TaskDetailsContent(
    task: TaskEntity,
    onBack: () -> Unit,
    onEdit: () -> Unit,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    val isDark = isSystemInDarkTheme()

    Scaffold(
        topBar = {
            TaskDetailsTopBar(
                onBack = onBack,
                onEdit = onEdit
            )
        },
        bottomBar = {
            TaskDetailsBottomBar(
                isCompleted = task.isCompleted,
                onComplete = onComplete,
                onDelete = onDelete
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Category tag
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.work_briefcase_vector),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(16.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = task.category.uppercase(),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                    letterSpacing = 1.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = task.title,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description
            task.description?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    lineHeight = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Info Cards Row
            Row(modifier = Modifier.fillMaxWidth()) {
                val priorityBg = if (!isDark) Color(0xFFE8F5E9) else Color(0xFF1A1A1A)
                val priorityTint = if (!isDark) Color(0xFF2E7D32) else when(task.priority) {
                    Priority.HIGH -> Color.Red
                    Priority.MEDIUM -> Color.Yellow
                    Priority.LOW -> Color.Green
                }

                InfoCard(
                    modifier = Modifier.weight(1f),
                    label = "PRIORITY",
                    value = task.priority.name.lowercase().capitalize(),
                    icon = Icons.Outlined.Info,
                    backgroundColor = priorityBg,
                    contentColor = priorityTint
                )
                Spacer(modifier = Modifier.width(16.dp))
                
                val dueDateBg = if (!isDark) Color(0xFFFFF3E0) else Color(0xFF1A1A1A)
                val dueDateTint = if (!isDark) Color(0xFFEF6C00) else Color.Yellow

                InfoCard(
                    modifier = Modifier.weight(1f),
                    label = "DUE DATE",
                    value = task.dueTime ?: "Not set",
                    icon = Icons.Outlined.DateRange,
                    backgroundColor = dueDateBg,
                    contentColor = dueDateTint
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Details section
            Surface(
                color = if (isDark) Color(0xFF1A1A1A) else Color(0xFFF5F5F5),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    DetailRow(
                        icon = painterResource(id = R.drawable.work_briefcase_vector),
                        label = "Category",
                        value = task.category,
                        iconBg = Color(0xFF4A4AFF)
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    DetailRow(
                        icon = Icons.Outlined.Notifications,
                        label = "Reminders",
                        value = if (task.isReminderEnabled) "On" else "Off",
                        iconBg = Color(0xFF4CAF50)
                    )
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    DetailRow(
                        icon = painterResource(id = R.drawable.repeat_vector),
                        label = "Repeat",
                        value = task.repeat,
                        iconBg = Color(0xFF2196F3)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailsTopBar(onBack: () -> Unit, onEdit: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("Details", fontWeight = FontWeight.SemiBold, fontSize = 18.sp) },
        navigationIcon = {
            TextButton(onClick = onBack) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Tasks")
                }
            }
        },
        actions = {
            TextButton(onClick = onEdit) {
                Text("Edit")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun TaskDetailsBottomBar(isCompleted: Boolean, onComplete: () -> Unit, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = onComplete,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                enabled = !isCompleted,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSystemInDarkTheme()) Color(0xFF4A8AFF) else Color(0xFF2E7D32),
                    disabledContainerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(if (isCompleted) "Completed" else "Mark as Complete", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Surface(
                onClick = onDelete,
                modifier = Modifier.size(56.dp),
                shape = RoundedCornerShape(16.dp),
                color = if (isSystemInDarkTheme()) Color(0xFF1E1E1E) else Color(0xFFFFEBEE)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                }
            }
        }
    }
}

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: Any, // ImageVector or Painter
    backgroundColor: Color,
    contentColor: Color
) {
    Surface(
        modifier = modifier,
        color = backgroundColor,
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                when (icon) {
                    is ImageVector -> Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(16.dp))
                    is Int -> Icon(painterResource(id = icon), contentDescription = null, tint = contentColor, modifier = Modifier.size(16.dp))
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall,
                    color = contentColor.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = if (isSystemInDarkTheme()) Color.White else Color.Black,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun DetailRow(icon: Any, label: String, value: String, iconBg: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            when (icon) {
                is ImageVector -> Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                is androidx.compose.ui.graphics.painter.Painter -> Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
