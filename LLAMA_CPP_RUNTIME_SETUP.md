# Full Local Runtime

The project does not redistribute third-party model weights or source.

1. Obtain a reviewed/pinned llama.cpp source tree.
2. Place it in `app/src/main/cpp/llama.cpp`.
3. Build the `arm64-v8a` Android variant.
4. Import a legally licensed GGUF model.
5. Run Device Benchmark.
6. Load the model and run a short generation benchmark.
7. Enable longer context only after memory/thermal testing.

The official llama.cpp Android documentation describes GGUF metadata parsing from a ContentResolver URI or app-private file and model loading through its Android inference binding.
