package com.example.n42.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * PLACEHOLDER ONLY. The real Login screen is built and integrated separately.
 * This stub exists so "Back to Login" and "Create Account" have somewhere clearly
 * labelled to go. Delete it (and its use in MainActivity) when Login is integrated.
 */
@Composable
fun LoginPlaceholderScreen(
    onForgotPassword: () -> Unit = {},
    onRegister: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthTheme.Background)
            .systemBarsPadding()
            .padding(horizontal = AuthTheme.ScreenHorizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Login", style = AuthType.Title)
        Spacer(Modifier.height(12.dp))
        Text("Placeholder: the Login screen will be integrated later.", style = AuthType.Body)
        Spacer(Modifier.height(32.dp))
        Text(
            "Forgot Password?",
            style = AuthType.Link,
            modifier = Modifier.clickable(onClick = onForgotPassword).padding(8.dp),
        )
        Text(
            "Create an account",
            style = AuthType.Link,
            modifier = Modifier.clickable(onClick = onRegister).padding(8.dp),
        )
    }
}

@Preview(showBackground = true, widthDp = 411, heightDp = 891)
@Composable
private fun LoginPlaceholderPreview() = LoginPlaceholderScreen()