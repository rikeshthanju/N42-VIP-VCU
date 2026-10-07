package com.example.n42

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.n42.auth.ForgotPasswordScreen
import com.example.n42.auth.RegistrationScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var showRegister by remember { mutableStateOf(true) }
            Box(Modifier.fillMaxSize()) {
                if (showRegister) RegistrationScreen()
                else ForgotPasswordScreen(onBackToLogin = { showRegister = true })

                // Temporary: lets you switch screens while testing
                TextButton(
                    onClick = { showRegister = !showRegister },
                    modifier = Modifier.align(Alignment.TopEnd).statusBarsPadding(),
                ) { Text(if (showRegister) "→ Forgot" else "→ Register") }
            }
        }
    }
}