package com.example.n42

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.n42.auth.ForgotPasswordScreen
import com.example.n42.auth.LoginPlaceholderScreen
import com.example.n42.auth.RegistrationScreen

/** Temporary auth navigation. Replace with real navigation when the Login screen is integrated. */
private enum class AuthScreen { Register, Login, ForgotPassword }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var screen by remember { mutableStateOf(AuthScreen.Register) }
            when (screen) {
                AuthScreen.Register -> RegistrationScreen(
                    onAccountCreated = { screen = AuthScreen.Login }, // placeholder: no real account created
                )
                AuthScreen.Login -> LoginPlaceholderScreen(
                    onForgotPassword = { screen = AuthScreen.ForgotPassword },
                    onRegister = { screen = AuthScreen.Register },
                )
                AuthScreen.ForgotPassword -> ForgotPasswordScreen(
                    onBackToLogin = { screen = AuthScreen.Login },
                )
            }
        }
    }
}