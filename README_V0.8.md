# HITFORGE IAML3G3NDZ v0.8

## Real local LLM milestone

This Android project contains an actual llama.cpp native inference bridge.

### Build
`tools/build_android_native.sh`

The script fetches a pinned llama.cpp revision (`b10982` by default), builds arm64-v8a,
and assembles the Android app.

### Offline behavior
Once the APK and GGUF model are installed, generation is local. The project does not
request INTERNET permission.

### Personal learning
Memory/retrieval is immediate. Optional LoRA/PEFT training is a separate, explicit
personalization stage to avoid continually modifying base weights.

### Model licensing
You are responsible for verifying the license and redistribution terms of any model you
install. Model weights are not included in this repository.
