# HITFORGE v1.3 — Installer Ready

This version prepares the project to hand an APK to Android's system package installer.

User flow:
1. Download the HITFORGE APK.
2. Open the APK.
3. Android Package Installer appears.
4. User confirms Install.
5. HITFORGE is installed.

Important: Android does not permit an ordinary app to silently install itself. PackageInstaller documentation says installation may require user intervention unless the caller is a device owner or affiliated profile owner. This project intentionally uses the normal user-consent flow.

The project uses FileProvider/content:// URIs rather than insecure file:// URIs.

`REQUEST_INSTALL_PACKAGES` is included because this project explicitly supports user-initiated package installation. If distributed through Google Play, its restricted-permission policy must also be satisfied.
