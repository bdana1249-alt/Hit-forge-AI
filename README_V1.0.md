# HITFORGE IAML3G3NDZ v1.0

The Personal AI Engine milestone.

## Local loop
GGUF model -> native llama.cpp -> Song Director -> personal retrieval -> generation ->
hook analysis -> user approval -> song library -> approved training data -> future adapter.

## Important
This project does not bundle third-party model weights or claim that adapter training is
safe/effective on every Android device. Training is separated from inference so the A16
can focus on local generation.

llama.cpp's current Android docs support GGUF metadata parsing, app-private model loading,
token streaming, hardware-aware kernels, and benchmark/model-management patterns.
PEFT supports LoRA and multiple switchable adapters for parameter-efficient adaptation.

The user controls what becomes training material.
