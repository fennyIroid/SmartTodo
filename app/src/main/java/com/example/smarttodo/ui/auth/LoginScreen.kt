package com.example.smarttodo.ui.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smarttodo.R
import com.example.smarttodo.ui.theme.*

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFEBEBEB) // Light grey background like in design
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar shim
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Home Task List",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // White Card Container
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))

                    // Icon Circle
                    Box(
                        modifier = Modifier
                            .align(Alignment.Start)
                            .size(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(SoftPeach),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check, // Placeholder for the exact icon
                            contentDescription = "Logo",
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Welcome Text
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Welcome back",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "Login",
                            style = MaterialTheme.typography.displaySmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontFamily = SerifFontFamily
                            ),
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // Email Input
                    LoginTextField(
                        label = "EMAIL ADDRESS",
                        value = email,
                        onValueChange = { email = it },
                        icon = Icons.Default.Email,
                        placeholder = "alex@example.com"
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password Input
                    LoginTextField(
                        label = "PASSWORD",
                        value = password,
                        onValueChange = { password = it },
                        icon = Icons.Default.Lock,
                        placeholder = "........",
                        isPassword = true
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Forgot Password
                    Text(
                        text = "Forgot Password?",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = SageGreen,
                        modifier = Modifier.align(Alignment.End).clickable { /* TODO */ }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Sign In Button
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = SageGreen)
                    ) {
                        Text(
                            text = "Sign In",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    // OR Divider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                        Text(
                            text = "OR CONTINUE WITH",
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                            color = Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                        HorizontalDivider(modifier = Modifier.weight(1f), color = Color.LightGray.copy(alpha = 0.5f))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Social Buttons
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                         SocialLoginButton(
                            text = "Google",
                            modifier = Modifier.fillMaxWidth()
                        ) { /* TODO */ }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Sign Up Footer
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Don't have an account? ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "Sign Up",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = SageGreen,
                            modifier = Modifier.clickable { /* TODO */ }
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun LoginTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    placeholder: String,
    isPassword: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            ),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f),
                focusedBorderColor = SageGreen,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent
            ),
            leadingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.LightGray
                )
            },
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else KeyboardType.Email
            ),
            singleLine = true
        )
    }
}

@Composable
fun SocialLoginButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(56.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f)),
        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
    ) {
        // Placeholder for Google Logo
        // Ideally replaced with R.drawable.ic_google_logo
        Icon(
            imageVector = Icons.Default.AccountCircle, // Better generic placeholder than Check/Lock
            contentDescription = "Google Logo",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified // Or Color.Red if we want to simulate brand color roughly
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "Continue with $text",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    SmartTodoTheme {
        LoginScreen()
    }
}
