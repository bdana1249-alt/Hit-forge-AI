# HITFORGE IAML3G3NDZ Edition v1.4

**BUILD-READY Android package.**

This is the first package in the series with a complete reproducible GitHub Actions APK build workflow.

It:
- Builds a real APK rather than renaming a ZIP.
- Compiles llama.cpp for Android arm64-v8a.
- Packages the native `.so` into the APK.
- Uses Java 17.
- Uses Android SDK 35.
- Uses NDK 27.2.12479018.
- Uses CMake 3.31.6.
- Fetches llama.cpp b11010.
- Verifies the resulting APK contains an ARM64 native library.
- Does not request INTERNET permission.

The resulting `app-debug.apk` is installable on a compatible ARM64 Android device, subject to Android's normal package-install confirmation.
