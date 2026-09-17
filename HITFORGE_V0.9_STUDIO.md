# HITFORGE v0.9 — Personal AI Studio

## What is now implemented
- A dedicated Personal AI Studio Activity.
- Local GGUF model inspection.
- Real native llama.cpp generation bridge.
- Configurable max tokens, context size and temperature.
- Forge modes and mature/explicit-language creative modes.
- Local personal memory retrieval.
- Accept & Learn: stores generated songs as approved examples.
- Teach HITFORGE: stores explicit songwriting instructions.
- Make It Bigger: sends a controlled expansion request to the local model.
- Local session/personalization architecture.

## Learning philosophy
HITFORGE learns immediately through local retrieval. Durable weight adaptation remains
a separate, explicit adapter-training phase. This prevents every generation from
changing the base model and makes rollback possible.

## Model training
PEFT/LoRA is intended for the later adapter trainer. PEFT updates only adapter
parameters while the base model remains frozen. Multiple adapters can be loaded and
switched, which fits HITFORGE's future "styles" and rollback architecture.

## Important Android constraint
Full on-device generation is dependent on the chosen GGUF model fitting the device.
Use the benchmark before increasing context or token limits. llama.cpp's Android
documentation explicitly supports GGUF metadata reading, app-private model loading,
and streaming token generation, and documents Android arm64-v8a cross-compilation.
