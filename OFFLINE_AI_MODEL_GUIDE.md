# HITFORGE v0.4 — Offline AI model guide

HITFORGE is offline-first and requests no network permission. v0.4 adds a local model manager so a compatible on-device model can be copied/imported into the app's private storage.

## Important
This project does **not** bundle a large language model. Model weights can be hundreds of MB to multiple GB and must be selected for the phone's available RAM/storage and the chosen inference runtime. Do not assume every GGUF/ONNX file is compatible.

## Current behavior
- Built-in songwriting engine works without a model and without internet.
- Model Manager lets the user import local `.gguf`, `.bin`, or `.onnx` files.
- The imported file is stored locally; no upload occurs.
- A future inference adapter can connect the imported model to the SongEngine.

## Recommended next engineering step
Integrate a native Android on-device inference runtime (for example a llama.cpp Android build for GGUF models) behind a `LocalModelRuntime` interface. Keep the UI independent of the runtime so models can be swapped without changing HITFORGE.

## Samsung A16 target
Use a small quantized model first and benchmark generation speed and memory before attempting larger models. Keep the context window moderate and make model loading optional so the app remains usable on lower-memory variants.
