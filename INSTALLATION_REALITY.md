# HITFORGE installation on Samsung A16

## What Android allows

HITFORGE can be packaged as a normal signed `.apk`. When you download that APK to the Samsung A16 and open it, Android/Samsung can show the package installer. The user must confirm installation and, if necessary, allow installation from that source.

A normal third-party APK cannot silently install itself merely because it was downloaded/opened. Bypassing that confirmation would require privileged/device-management control that a normal consumer app does not have.

## Intended user flow

1. Download `HITFORGE-IAML3G3NDZ.apk`.
2. Tap the downloaded APK.
3. Android Package Installer verifies it.
4. Tap **Install**.
5. Launch HITFORGE.
6. Import a compatible GGUF model once.
7. Generation then runs locally/offline.

The app itself declares no `INTERNET` permission.

## Important model requirement

The APK is the application. The local AI model is a separate GGUF file because model weights are large and have their own licensing terms. HITFORGE should not silently download a model.

## Validation status

The source package has been statically audited and hardened for the next build. A physical Samsung A16 installation test and a release APK build cannot be truthfully claimed from this environment because the Android SDK/NDK and a connected A16 are not available here.
