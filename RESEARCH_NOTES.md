# Research notes — September 2026

- Android Developers: Gemini Nano/AICore provides on-device generative AI with no network requirement on compatible devices. ML Kit GenAI APIs expose prompt/rewrite/summarization capabilities. This is an alternative runtime, but device compatibility and model availability vary.
- ggml-org/llama.cpp: current Android documentation supports Android builds, GGUF metadata/model loading, ARM acceleration, and app-private model paths. It recommends starting with a modest context size because context can increase memory pressure.
- llama.cpp C API currently exposes LoRA adapter loading and GGUF metadata APIs.
- Hugging Face PEFT documentation describes LoRA as parameter-efficient fine-tuning: only adapter parameters are trained, reducing optimizer memory/state requirements.
- 2025 ACM survey and 2026 continual-learning research document catastrophic forgetting as a central problem in sequential LLM adaptation. Therefore HITFORGE uses retrieval memory every session and reserves weight adaptation for periodic validated adapters.
- 2026 SLoRA research reports that noisy LoRA updates can interfere with prior learning, reinforcing the need for stability checks before activating a new personal adapter.

Sources are kept in the project documentation and should be rechecked when the native runtime is pinned to a specific release.

## Primary references
- https://developer.android.com/ai/gemini-nano
- https://github.com/ggml-org/llama.cpp/blob/master/docs/android.md
- https://github.com/ggml-org/llama.cpp/blob/master/include/llama.h
- https://huggingface.co/docs/peft/main/methods/overview
- https://doi.org/10.1145/3735633
- https://aclanthology.org/2026.acl-long.247/
