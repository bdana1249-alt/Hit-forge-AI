# HITFORGE v0.8 — Real Local Inference

This version crosses the architectural boundary from a mock/local rules engine to an
actual native llama.cpp inference bridge.

## What is real
- `hitforge_llm.cpp` calls the public llama.cpp C API.
- GGUF model loading is native.
- Prompt tokenization is native.
- Prompt decoding is native.
- Token sampling is native.
- Token-to-piece streaming is native.
- Generation is performed without a network request.

## Build
1. Install Android Studio, SDK, NDK and CMake.
2. Set `ANDROID_NDK`.
3. Run `tools/build_android_native.sh`.
4. The script pins llama.cpp to `b10982`.
5. Build the APK.
6. Import a legally licensed GGUF model.
7. Benchmark context and generation speed before long generations.

## Why b10982?
It is a recent published llama.cpp nightly visible in the current release list. Pinning
a known revision prevents the native API from silently changing underneath the app.
Update deliberately after testing.

## Important
The ZIP intentionally does not redistribute third-party llama.cpp source or model weights.
The fetch script obtains the exact pinned source when building.
