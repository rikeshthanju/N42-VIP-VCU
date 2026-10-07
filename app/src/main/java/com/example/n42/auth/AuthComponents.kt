package com.example.n42.auth

import com.example.n42.R
import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Gabarito is the font used across the Prenatal designs.
 * Download it from Google Fonts and add these files to app/src/main/res/font/:
 *   gabarito_regular.ttf, gabarito_medium.ttf, gabarito_bold.ttf
 */
val Gabarito = FontFamily(
    Font(R.font.gabarito_regular, FontWeight.Normal),
    Font(R.font.gabarito_medium, FontWeight.Medium),
    Font(R.font.gabarito_bold, FontWeight.Bold),
)

/** Tokens taken from the Prenatal frames and "Prenatal Palette" in the Figma file. */
object AuthTheme {
    // Prenatal palette
    val Background = Color(0xFFF4E4E8)   // screen background
    val Primary = Color(0xFFD9A2B6)      // buttons, links, focus
    val PlumDark = Color(0xFF713B50)     // palette accent (not used on auth screens yet)

    // Text
    val Title = Color(0xFF1E293B)
    val Label = Color(0xFF1E293B)
    val Body = Color(0xFF475569)
    val InputText = Color(0xFF334155)
    val Placeholder = Color(0xFF94A3B8)
    val Supporting = Color(0xFF64748B)

    // Input
    val InputFill = Color.White
    val InputBorder = Color(0xFFE2E8F0)
    val InputBorderPassword = Color(0xFF94A3B8)

    // Status
    val Error = Color(0xFFD12E34)
    val Success = Color(0xFF317D35)

    // Layout (dp)
    val ContentMaxWidth = 358.dp
    val ScreenHorizontalPadding = 24.dp
    val FieldGap = 12.dp
    val LabelGap = 6.dp
    val InputHeight = 46.dp
    val InputRadius = 8.dp
    val InputBorderWidth = 1.5.dp
    val ButtonHeight = 44.dp
    val ButtonRadius = 6.dp
    val IconSize = 20.dp
}

object AuthType {
    val Title = TextStyle(
        fontFamily = Gabarito, fontWeight = FontWeight.Bold, fontSize = 30.sp,
        lineHeight = 34.sp, letterSpacing = (-0.15).sp, color = AuthTheme.Title, textAlign = TextAlign.Center,
    )
    val Label = TextStyle(
        fontFamily = Gabarito, fontWeight = FontWeight.Normal, fontSize = 14.sp,
        lineHeight = 22.sp, color = AuthTheme.Label,
    )
    val Input = TextStyle(
        fontFamily = Gabarito, fontWeight = FontWeight.Normal, fontSize = 14.sp,
        lineHeight = 22.sp, color = AuthTheme.InputText,
    )
    val Body = TextStyle(
        fontFamily = Gabarito, fontWeight = FontWeight.Normal, fontSize = 14.sp,
        lineHeight = 22.sp, letterSpacing = 0.14.sp, color = AuthTheme.Body, textAlign = TextAlign.Center,
    )
    val Button = TextStyle(
        fontFamily = Gabarito, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp,
    )
    val Link = TextStyle(
        fontFamily = Gabarito, fontWeight = FontWeight.Medium, fontSize = 14.sp,
        lineHeight = 22.sp, letterSpacing = 0.175.sp, color = AuthTheme.Primary,
    )
}

enum class SupportingType { Default, Error, Success }

@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false,
    isError: Boolean = false,
    supportingText: String? = null,
    supportingType: SupportingType = if (isError) SupportingType.Error else SupportingType.Default,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    var focused by remember { mutableStateOf(false) }

    val borderColor = when {
        isError -> AuthTheme.Error
        focused -> AuthTheme.Primary
        isPassword -> AuthTheme.InputBorderPassword
        else -> AuthTheme.InputBorder
    }
    val shape = RoundedCornerShape(AuthTheme.InputRadius)

    Column(modifier, verticalArrangement = Arrangement.spacedBy(AuthTheme.LabelGap)) {
        Text(label, style = AuthType.Label)

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            textStyle = AuthType.Input,
            cursorBrush = SolidColor(AuthTheme.Primary),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
            visualTransformation =
                if (isPassword && !passwordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            modifier = Modifier
                .fillMaxWidth()
                .height(AuthTheme.InputHeight)
                .background(AuthTheme.InputFill, shape)
                .border(BorderStroke(AuthTheme.InputBorderWidth, borderColor), shape),
            decorationBox = { inner ->
                Row(
                    Modifier.fillMaxSize().padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart) {
                        if (value.isEmpty()) {
                            Text(placeholder, style = AuthType.Input.copy(color = AuthTheme.Placeholder), maxLines = 1)
                        }
                        inner()
                    }
                    if (isPassword) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = AuthTheme.Placeholder,
                            modifier = Modifier
                                .size(AuthTheme.IconSize)
                                .clickable { passwordVisible = !passwordVisible },
                        )
                    }
                }
            },
        )

        if (supportingText != null) {
            val color = when (supportingType) {
                SupportingType.Default -> AuthTheme.Supporting
                SupportingType.Error -> AuthTheme.Error
                SupportingType.Success -> AuthTheme.Success
            }
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
                if (supportingType == SupportingType.Error) {
                    Icon(Icons.Outlined.Info, contentDescription = null, tint = color, modifier = Modifier.size(AuthTheme.IconSize))
                }
                Text(supportingText, style = AuthType.Label.copy(color = color))
            }
        }
    }
}

@Composable
fun AuthPrimaryButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(AuthTheme.ButtonRadius),
        colors = ButtonDefaults.buttonColors(containerColor = AuthTheme.Primary, contentColor = Color.White),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
        modifier = modifier.fillMaxWidth().height(AuthTheme.ButtonHeight),
    ) {
        Text(text, style = AuthType.Button)
    }
}

fun isValidEmail(email: String): Boolean =
    Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
