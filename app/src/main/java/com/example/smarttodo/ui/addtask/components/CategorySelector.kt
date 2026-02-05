package com.example.smarttodo.ui.addtask.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.smarttodo.R

data class CategoryUi(
    val name: String,
    val iconRes: Int? = null
)

@Composable
fun CategorySelector(
    selectedCategory: String,
    onCategoryChange: (String) -> Unit,
    categories: List<String>,
    onAddCategory: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryItems = categories.map { 
        CategoryUi(it, when(it) {
            "Work" -> R.drawable.work_briefcase_vector
            "Personal" -> R.drawable.person_user_vector
            "Health" -> R.drawable.health_heart_vector
            else -> R.drawable.work_briefcase_vector
        })
    } + CategoryUi("New")

    var showDialog by remember { mutableStateOf(false) }
    var newCategoryName by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("New Category") },
            text = {
                OutlinedTextField(
                    value = newCategoryName,
                    onValueChange = { newCategoryName = it },
                    label = { Text("Category Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (newCategoryName.isNotBlank()) {
                            onAddCategory(newCategoryName)
                            newCategoryName = ""
                            showDialog = false
                        }
                    }
                ) {
                    Text("Add")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "CATEGORY",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(categoryItems) { category ->
                CategoryItem(
                    category = category,
                    isSelected = category.name == selectedCategory,
                    onClick = { 
                        if (category.name == "New") {
                            showDialog = true
                        } else {
                            onCategoryChange(category.name)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: CategoryUi,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val isNew = category.name == "New"
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(72.dp)
    ) {
        Surface(
            onClick = onClick,
            shape = RoundedCornerShape(16.dp),
            color = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
            modifier = Modifier
                .size(56.dp)
                .then(
                    if (isNew)
                        Modifier.drawBehind {
                            val stroke = Stroke(
                                width = 1.dp.toPx(),
                                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                            )
                            val outline = RoundedCornerShape(16.dp).createOutline(size, layoutDirection, this)
                            drawOutline(
                                outline = outline,
                                color = onSurfaceColor.copy(alpha = 0.3f),
                                style = stroke
                            )
                        }
                    else Modifier
                )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if (isNew) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Add,
                        contentDescription = "Add New Category",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                } else {
                    Icon(
                        painter = painterResource(category.iconRes ?: R.drawable.work_briefcase_vector),
                        contentDescription = category.name,
                        tint = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = category.name,
            style = MaterialTheme.typography.labelSmall,
            color = if (isSelected)
                MaterialTheme.colorScheme.onBackground
            else
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            fontWeight = if (isSelected) androidx.compose.ui.text.font.FontWeight.Bold else androidx.compose.ui.text.font.FontWeight.Normal
        )
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CategorySelectorLightPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = false) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CategorySelector(
                selectedCategory = "Work",
                onCategoryChange = {},
                categories = listOf("Work", "Personal", "Health"),
                onAddCategory = {},
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CategorySelectorDarkPreview() {
    com.example.smarttodo.ui.theme.SmartTodoTheme(darkTheme = true) {
        Surface(color = MaterialTheme.colorScheme.background) {
            CategorySelector(
                selectedCategory = "Health",
                onCategoryChange = {},
                categories = listOf("Work", "Personal", "Health"),
                onAddCategory = {},
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
    }
}