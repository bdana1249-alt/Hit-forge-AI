# HITFORGE v0.7 Architecture

## Core loop
Request -> Song Director -> personal retrieval -> local LLM -> hook/structure analysis -> draft -> feedback -> memory.

## Learning
1. Store accepted songs, edits, rejected generations, explicit preferences and mode choices locally.
2. Retrieve relevant memories before generation.
3. Periodically curate approved examples.
4. Optionally train a versioned LoRA/PEFT adapter.
5. Validate against a personal benchmark before activation.
6. Keep the prior adapter for rollback.

This avoids changing base weights after every session and reduces the risk of catastrophic forgetting.

## Runtime
Primary target: llama.cpp + GGUF. The Android project contains a native bridge and optional CMake hook.
A reviewed, pinned llama.cpp checkout must be placed at `app/src/main/cpp/llama.cpp` before full native inference is compiled.

## Content modes
CLEAN, RADIO, MATURE, EXPLICIT LANGUAGE, DARK, HORROR, CRIME NOIR.

These modes allow mature/profane creative writing while retaining normal safety boundaries.

## Privacy
No automatic uploads. Keep models and memory in app-private storage. Provide explicit export/delete controls.

## Model selection
Benchmark RAM, storage, first-token latency, tokens/sec, context size and stability before activation.
