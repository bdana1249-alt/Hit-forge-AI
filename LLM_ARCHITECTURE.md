# HITFORGE Personal LLM Architecture

## Objective
A local-first songwriting system that becomes more useful for one creator over time without rewriting the base model after every session.

## Runtime
Primary runtime: llama.cpp + GGUF on Android arm64-v8a. The app should benchmark the device and select a model/context configuration that fits available memory. See the upstream Android and build documentation before packaging a runtime binary.

## Learning loop
1. Capture explicit feedback (accept/reject/edit/teach).
2. Normalize feedback into preference signals.
3. Store song exemplars and preference facts locally.
4. Retrieve relevant exemplars before generation.
5. Compose a system prompt from stable preferences + relevant memory + current request.
6. Periodically export a curated training set.
7. Optionally train a LoRA/PEFT adapter on a separate capable machine, then import the adapter to the phone.
8. Keep base model and adapters versioned so the user can roll back.

## Why not train after every prompt?
Continuous weight updates on-device are expensive and can cause forgetting or degrade general capabilities. Retrieval memory gives immediate personalization; periodic PEFT/LoRA gives controlled weight adaptation.

## Content modes
Clean, Radio, Mature, Explicit Language, Dark, Horror, Crime/Noir. The mature system allows ordinary profanity and adult themes while retaining safety boundaries.

## Model abstraction
The app should expose a LocalLlmBridge interface with implementations for:
- llama.cpp/GGUF
- Android AICore where the device supports the required APIs
- future local runtimes

AICore/Gemini Nano is optional rather than required because device/API availability varies.
