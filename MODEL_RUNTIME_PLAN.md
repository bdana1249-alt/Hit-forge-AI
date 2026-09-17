# HITFORGE local LLM runtime plan

The production runtime target is llama.cpp on Android with GGUF models. llama.cpp provides an Android binding and supports loading a GGUF model from an app-private path; its current Android documentation describes an `AiChat`/`InferenceEngine` path and ARM acceleration. It also exposes LoRA adapter loading in its C API.

## Runtime flow
1. Model Manager imports a compatible GGUF file.
2. HITFORGE validates the file and records metadata.
3. User selects a model profile.
4. PromptBuilder combines the system songwriting instructions + current request + retrieved personalization memory.
5. JNI calls the native llama.cpp runtime.
6. Generated result is shown in the studio.
7. The session is logged locally.
8. User can accept/reject/teach.

## Device adaptation
Do not hard-code a model size. On first launch, benchmark RAM/storage and recommend a quantization/model profile. Keep context conservative to avoid memory pressure. The exact Samsung A16 RAM configuration should be detected at runtime.

## No model is bundled in this source archive
Model weights are large and licensing varies by model. HITFORGE accepts a user-supplied GGUF model and keeps the model separate from the APK.
