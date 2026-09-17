# HITFORGE IAML3G3NDZ — APK Build

This package is prepared to build a real Android APK through GitHub Actions.

## Easiest route

1. Create a new GitHub repository.
2. Upload the contents of this ZIP to the repository root.
3. Open **Actions**.
4. Choose **Build HITFORGE APK**.
5. Choose **Run workflow**.
6. Wait for the build to finish.
7. Open the workflow run and download **HITFORGE-IAML3G3NDZ-debug-apk**.
8. Extract the artifact if GitHub downloads it as a ZIP.
9. Copy `app-debug.apk` to the Samsung A16.
10. Tap the APK and use Android's Package Installer.

The workflow installs Java 17, Android SDK 35, NDK 27.2.12479018 and CMake 3.31.6, fetches the pinned llama.cpp revision, compiles the native ARM64 engine, assembles the APK, and verifies that an ARM64 `.so` is inside the APK.

The build is a debug-signed APK intended for personal sideloading/testing. A release build with a private signing key should be used before public distribution.

llama.cpp's current Android documentation confirms the supported `arm64-v8a` NDK build path and its Android Studio binding. 
