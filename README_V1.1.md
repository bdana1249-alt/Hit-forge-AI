# HITFORGE — IAML3G3NDZ Edition v1.1

This milestone turns the v1.0 foundation into a real offline studio workflow:

- Native llama.cpp GGUF inference on Android arm64.
- Real token-by-token UI streaming through a JNI callback.
- Local GGUF model import, private storage, selection, inspection and persistence.
- Local GGUF LoRA adapter import, validation against the selected base model, activation and base-model fallback.
- Approved-example export now exports the actual accepted examples instead of an empty placeholder.
- Accepted songs remain in the local Song Library.
- Personal preferences and retrieval memory remain app-private.
- No INTERNET permission is requested.
- llama.cpp build pin updated to b11010, a current nightly that publishes an Android arm64 CPU asset.

## Important

The project is source/build-ready, not a precompiled Samsung A16 APK. A compatible GGUF model still must be supplied by the user, and Android requires the user to approve APK installation. The app does not silently install software or download model weights.

LoRA adapters must be GGUF adapters compatible with the selected model architecture. llama.cpp's current C API supports loading a LoRA adapter, setting adapters on a context, and freeing the adapter.

## Build

1. Install Android Studio, Android SDK, and NDK.
2. Set `ANDROID_NDK`.
3. Run `tools/build_android_native.sh`.
4. Install `app/build/outputs/apk/debug/app-debug.apk` on the phone.
5. Import a compatible `.gguf` model from the phone's file picker.
6. Generate fully offline.

The app never needs an API key or cloud endpoint.
