# NT42+ Android App

The Android app for the NT42+ study, built with Kotlin and Jetpack Compose. The design follows our NT Figma file.

## What you can do in the app

- **Welcome and Login:** the first screens. (Built by a teammate.)
- **Register and Forgot Password:** create an account or reset a password.
- **Reminders:** a list of study reminders like weekly check-ins, cooking classes and appointments. Tap one to see the details.
- **Tips:** a list of recipes, cooking tips, grocery tips and learning content. Tap a card to read more. Recipes show ingredients and steps.
- **Light and Dark:** Reminders and Tips follow your phone's theme.

Profile, Chat and Trivia show "Coming soon" for now. Other teammate is building them.

> Everything is sample data for now. Login, Register and Forgot Password aren't connected to a server yet.

## Where things are

```
app/src/main/java/com/example/n42/
├─ MainActivity.kt    starts the app and moves between screens
├─ auth/              Register and Forgot Password
├─ login/             Welcome and Login (teammate's code)
└─ study/             Reminders and Tips
    ├─ StudyData.kt       the sample data
    ├─ StudyColors.kt     Light and Dark colors
    └─ StudyScreens.kt    all the Reminders and Tips screens
```

## Adding a backend later
The screens get their data from two functions in `StudyData.kt`: `getReminders()` and `getTips()`. When we have a server, only those two functions need to change.

## About the teammate's login code
We kept it as he wrote it. The few changes we made are marked with `ADDED`, `CHANGED` or `REMOVED` comments in `login/WelcomeLoginScreens.kt`. They connect his screens to Register, Forgot Password and Home.

## Try it
1. Open the project in Android Studio and wait for Gradle to finish.
2. Run it on an emulator or phone.
3. Go Welcome → Login → enter any email and password → Home.
4. Switch your phone to Dark theme to see both looks.

## To do
- Add the Inter font from the Figma (the app uses the default font for now).
- Remove the "Back to Welcome (test only)" link at the top of Home before the final PR.
- Connect Login, Register and the data to a real backend.
- Confirm the Dark colors with the PM, since the Figma doesn't have a dark design.

