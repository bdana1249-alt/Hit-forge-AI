# v1.1 Finish Line

## What is real now

### Generation
The app calls llama.cpp's native C API, loads a user-supplied GGUF model, tokenizes the prompt, decodes it, samples tokens and sends each generated text piece to the Android UI through a JNI callback.

### Model manager
Models are copied into app-private storage under `files/models`. The selected model path is persisted locally. No model download service is included.

### Adapter manager
LoRA GGUF adapters are copied into app-private storage. The selected adapter is registered with its base-model name and native validation result. The native runtime loads the adapter, checks compatibility, applies it to the context, and frees it after generation. The user can switch back to the base model.

### Learning
`ACCEPT & LEARN` writes an accepted example to the private memory store and saves the song to the local song library. `EXPORT APPROVED TRAINING DATA` now exports those accepted examples as JSONL. The base model is never silently retrained.

## Deliberate safety/engineering boundary

Personalization is separated from the frozen base model. Periodic adapter training remains an explicit, external training step. This prevents every phone interaction from damaging a working base model and gives HITFORGE a rollback point.

## Device validation

This package has not been physically installed and benchmarked on the user's Samsung A16 in this environment. The correct next step after building is a real-device benchmark using the smallest suitable GGUF model, then tuning context length, threads and token budget from measured results.
