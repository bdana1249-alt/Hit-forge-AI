# Model Strategy

llama.cpp's official Android documentation now includes an Android binding with GGUF
metadata parsing, app-private model loading, automatic prompt formatting, token
generation as a Kotlin Flow, hardware acceleration, and benchmarking/model-management
examples.

HITFORGE should benchmark candidate models on the actual A16 rather than assuming a
specific parameter count will be optimal.

Start with:
- arm64-v8a
- context 4096
- conservative generation length
- a quantized GGUF model

Then measure:
- load time
- first-token latency
- tokens/sec
- peak memory
- sustained thermal behavior
- battery impact
- quality on the personal benchmark
