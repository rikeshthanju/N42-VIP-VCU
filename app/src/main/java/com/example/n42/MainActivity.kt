package com.example.n42

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.n42.auth.ForgotPasswordScreen
import com.example.n42.auth.RegistrationScreen
import com.example.n42.login.WelcomeLoginApp
import com.example.n42.login.theme.NTWelcomeLoginScreenTheme
import com.example.n42.study.StudyHome

// The four "places" the app can show. "screen" below holds one of these words.
private enum class AppScreen { WelcomeLogin, Register, ForgotPassword, Home }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            var screen by rememberSaveable { mutableStateOf(AppScreen.WelcomeLogin) }
            // Which of his two screens to open first: "welcome" or "login"
            var startScreen by rememberSaveable { mutableStateOf("welcome") }


            BackHandler(enabled = screen != AppScreen.WelcomeLogin) {
                startScreen = if (screen == AppScreen.ForgotPassword) "login" else "welcome"
                screen = AppScreen.WelcomeLogin
            }

            when (screen) {

                AppScreen.WelcomeLogin -> NTWelcomeLoginScreenTheme {
                    WelcomeLoginApp(
                        onRegisterClick = { screen = AppScreen.Register },
                        onForgotPasswordClick = {
                            startScreen = "login"
                            screen = AppScreen.ForgotPassword
                        },
                        onLoginSuccess = { screen = AppScreen.Home },
                        startScreen = startScreen
                    )
                }


                AppScreen.Register -> RegistrationScreen(
                    onBackClick = {
                        startScreen = "welcome"
                        screen = AppScreen.WelcomeLogin
                    },
                    onAccountCreated = {
                        startScreen = "login"
                        screen = AppScreen.WelcomeLogin
                    }
                )

                AppScreen.ForgotPassword -> ForgotPasswordScreen(
                    onBackToLogin = {
                        startScreen = "login"
                        screen = AppScreen.WelcomeLogin
                    }
                )

                // Reminders + Tips (with the bottom tab bar)
                AppScreen.Home -> StudyHome(
                    // TEMPORARY test link: remove together with the parameter in StudyHome.kt
                    onBackToWelcome = {
                        startScreen = "welcome"
                        screen = AppScreen.WelcomeLogin
                    }
                )
            }
        }
    }
}