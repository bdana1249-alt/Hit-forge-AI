# HITFORGE v1.2 Prelaunch Audit

Date: 2026-09-17

## Static checks completed

- No `android.permission.INTERNET` declaration.
- ARM64 (`arm64-v8a`) only.
- Android minSdk 28 / targetSdk 35.
- Local GGUF model storage is app-private.
- Model import now validates GGUF magic and uses a temporary file before finalizing.
- Native generation caps context at 8192 and generated tokens at 2048.
- Native thread count is capped at 6 to avoid excessive thread creation.
- Prompt/context overflow is converted to a user-facing error instead of attempting an oversized KV cache.
- Conservative 4-GB-RAM guard rejects very large model files.
- Current llama.cpp b11010 exists and provides an Android arm64 CPU release.
- Current llama.cpp API still exposes the LoRA initialization/application functions used by HITFORGE.

## Known release blockers

1. This environment does not contain the Android SDK/NDK or Gradle wrapper, so an APK cannot be compiled here.
2. No physical Samsung A16 is connected, so install/runtime/crash testing cannot be claimed.
3. A compatible GGUF model still must be selected and tested on the exact A16 RAM configuration.
4. Android will not silently install a downloaded third-party APK without user confirmation.

## Recommended A16 baseline

Start with a small, instruction-tuned GGUF and conservative settings:
- context: 2048-4096
- generation: 512-1024 tokens
- temperature: 0.75-0.90
- CPU threads: automatically capped by native runtime

Do not start with a 7B/8B model simply because it is more capable. The exact A16 RAM variant matters; Samsung sells A16 5G configurations with 4/6/8 GB RAM depending on market/model.

## Release gate

The app should not be called production-ready until:
- Debug APK installs on the A16.
- Model import succeeds.
- 10 consecutive generations complete without crash.
- Long prompt generation succeeds.
- Context overflow is handled.
- App background/foreground cycle succeeds.
- Low-storage condition is handled.
- Adapter import/activation is tested with a compatible LoRA.
- A release-signed APK is installed and tested.


## v1.3 installer audit

- Added secure FileProvider configuration.
- Added `REQUEST_INSTALL_PACKAGES` for user-initiated package installation.
- Added system `ACTION_INSTALL_PACKAGE` launcher.
- No silent-install mechanism is present.
- Installation remains subject to Android/Samsung confirmation and security checks.
