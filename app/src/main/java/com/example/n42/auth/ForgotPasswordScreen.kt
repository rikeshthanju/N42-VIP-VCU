package com.example.n42.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * Forgot Password screen. The Figma file has no final Forgot Password frame yet, so this
 * follows the shared NT & NT+ auth styling (frames NT 2 and NT 3). No email is actually sent.
 */
@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit = {}, // goes to the Login placeholder until the real Login screen is integrated
) {
    var email by remember { mutableStateOf("") }
    var submitted by remember { mutableStateOf(false) }
    var linkSent by remember { mutableStateOf(false) }

    val emailMessage = if (submitted) emailProblem(email) else null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AuthTheme.Background)
            .systemBarsPadding()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = AuthTheme.ScreenHorizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(Modifier.widthIn(max = AuthTheme.ContentMaxWidth).fillMaxWidth()) {
            Spacer(Modifier.height(96.dp))
            Text("Forgot Password", style = AuthType.Title, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(12.dp))
            Text(
                "Enter your email and we'll send you a link to reset your password.",
                style = AuthType.Body,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(37.dp))

            AuthTextField(
                label = "E-mail",
                value = email,
                onValueChange = { email = it; linkSent = false },
                placeholder = "Enter your email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                isError = emailMessage != null,
                supportingText = when {
                    emailMessage != null -> emailMessage
                    linkSent -> "Reset link sent. Please check your email."
                    else -> null
                },
                supportingType = when {
                    emailMessage != null -> SupportingType.Error
                    linkSent -> SupportingType.Success
                    else -> SupportingType.Default
                },
            )

            Spacer(Modifier.height(32.dp))
            AuthPrimaryButton("Send Reset Link", ready = isValidEmail(email), onClick = {
                submitted = true
                linkSent = isValidEmail(email) // simulated only
            })

            Spacer(Modifier.height(24.dp))
            Text(
                "Back to Login",
                style = AuthType.Link,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = onBackToLogin)
                    .padding(8.dp),
            )
            Spacer(Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, widthDp = 411, heightDp = 891)
@Composable
private fun ForgotPasswordPreview() = ForgotPasswordScreen()