# NT42+ Auth Screens (Registration and Forgot Password)

Jetpack Compose screens for the NT42+ Android app.

## Screens
- **RegistrationScreen**: first/last name, email, password, confirm password (with visibility toggles), Create Account button, terms and privacy text.
- **ForgotPasswordScreen**: email field, Send Reset Link button, simulated confirmation message, Back to Login.
- **LoginPlaceholderScreen**: a clearly labelled placeholder so "Back to Login" has a destination. The real Login screen is integrated separately; delete this stub then.

No backend, account creation or email sending is implemented.

## Navigation (temporary)
`MainActivity` uses a small state-based switch: Register, then Login (placeholder), then Forgot Password, and back. There is no test-only switch button. Replace this with real navigation when the Login screen is integrated.

## Files (`app/src/main/java/com/example/n42/auth/`)
- `AuthComponents.kt`: shared tokens (`AuthTheme`, `AuthType`), `AuthTextField`, `AuthPrimaryButton`, email validation
- `RegistrationScreen.kt`
- `ForgotPasswordScreen.kt`
- `LoginPlaceholderScreen.kt`

## Styling
Styling is based on the **NT & NT+** frames (NT 1 to NT 3) and the "NT & NT+ Palette" in the Figma file:
only colors from the "NT & NT+ Palette" are used.

Gabarito font. Tokens live in `AuthTheme` and `AuthType` in `AuthComponents.kt`.

The Figma file has no final Forgot Password frame yet, so that screen uses the shared auth styling until the design is confirmed.

### Font setup
The Gabarito files (`gabarito_regular.ttf`, `gabarito_medium.ttf`, `gabarito_bold.ttf`) are in `app/src/main/res/font/`.

## Build
Open the project in Android Studio and run the `app` configuration. The Gradle wrapper (`gradlew`, `gradlew.bat`, `gradle/wrapper/gradle-wrapper.jar` and `.properties`) is committed, so a fresh clone builds without extra setup:

```
./gradlew assembleDebug
```