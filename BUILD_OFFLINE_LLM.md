# Building the true local LLM runtime

The repository intentionally does not ship model weights. Download and distribute only models whose licenses permit your use.

For llama.cpp Android arm64-v8a, configure with the Android NDK and use the upstream CMake guidance. A portable configuration uses arm64-v8a, Android API 28+, GGML_NATIVE=OFF, and can enable KleidiAI on supported ARM devices.

Example upstream pattern:
cmake -S llama.cpp -B build-android \
  -DCMAKE_TOOLCHAIN_FILE="$ANDROID_NDK/build/cmake/android.toolchain.cmake" \
  -DANDROID_ABI=arm64-v8a \
  -DANDROID_PLATFORM=android-28 \
  -DGGML_CPU_KLEIDIAI=ON \
  -DGGML_NATIVE=OFF \
  -DGGML_OPENMP=OFF \
  -DGGML_LLAMAFILE=OFF \
  -DLLAMA_OPENSSL=OFF

Then compile the native library and connect it to LocalLlmBridge through JNI. Benchmark context length because larger contexts increase memory use.
