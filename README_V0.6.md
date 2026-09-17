# HITFORGE v0.6 — Personal Offline LLM Architecture

This build advances HITFORGE toward a serious private on-device songwriting assistant.

### Core principles
- Offline-first: no network is required for local generation.
- Base model stays immutable.
- Immediate learning uses local memory + retrieval.
- Periodic personalization uses optional LoRA/PEFT adapters.
- Every learned preference is inspectable and removable.
- Models are modular and license-aware.
- Mature creative language is supported; safety boundaries remain.

### The target pipeline
Request → Song Director → Personal Profile → Retrieval Memory → Local LLM → Hook/Structure Analyzer → Draft → User Feedback → Memory → Curated Training Set → Optional LoRA Adapter.

This is a source project, not a claim that model weights or a compiled native runtime are included. Model weights must be obtained separately under their applicable licenses.
