# NT42+ Auth Screens (Registration and Forgot Password)

Jetpack Compose screens for the NT42+ Android app.

## Screens
- **RegistrationScreen**: first/last name, email, password, confirm password (with visibility toggles), Create Account button, terms and privacy text.
- **ForgotPasswordScreen**: email field, Send Reset Link button, simulated confirmation message, Back to Login.

No backend, account creation or email sending is implemented. Login navigation is a placeholder callback.

## Files
- `AuthComponents.kt`: shared tokens (`AuthTheme`), `AuthTextField`, `AuthPrimaryButton`
- `RegistrationScreen.kt`
- `ForgotPasswordScreen.kt`

## Styling
Colors, spacing, radii and type come from the Prenatal frames and "Prenatal Palette" in the Figma file (background `#F4E4E8`, primary `#D9A2B6`, Gabarito font). They live in `AuthTheme` and `AuthType` in `AuthComponents.kt`.

The Forgot Password screen has no final design yet (Figma "Prenatal 5" is empty), so it uses the shared auth styling.

### Font setup
Download **Gabarito** from Google Fonts and add `gabarito_regular.ttf`, `gabarito_medium.ttf` and `gabarito_bold.ttf` to `app/src/main/res/font/`.

## Dependencies (app/build.gradle.kts)
```kotlin
implementation(platform("androidx.compose:compose-bom:2024.09.00"))
implementation("androidx.compose.material3:material3")
implementation("androidx.compose.material:material-icons-extended")
implementation("androidx.compose.ui:ui-tooling-preview")
```

## Run
Open in Android Studio, then run the `app` configuration on an emulator. Use the `@Preview` functions to view each screen.
