#!/usr/bin/env bash
set -euo pipefail
MANIFEST=app/src/main/AndroidManifest.xml
! grep -q 'android.permission.INTERNET' "$MANIFEST"
test -f app/src/main/java/com/iamlegendz/hitforge/quality/HookAnalyzer.java
test -f app/src/main/java/com/iamlegendz/hitforge/library/SongLibrary.java
test -f app/src/main/java/com/iamlegendz/hitforge/adapters/AdapterRegistry.java
test -f app/src/main/java/com/iamlegendz/hitforge/export/TrainingDatasetExporter.java
grep -q 'approvedExamples' app/src/main/java/com/iamlegendz/hitforge/memory/PersonalMemoryStore.java
grep -q 'nativeGenerateStreaming' app/src/main/java/com/iamlegendz/hitforge/ai/LocalLlmBridge.java
grep -q 'nativeGenerateStreaming' app/src/main/cpp/hitforge_llm.cpp
grep -q 'llama_set_adapters_lora' app/src/main/cpp/hitforge_llm.cpp
grep -q 'llama_adapter_lora_init' app/src/main/cpp/hitforge_llm.cpp
grep -q 'b11010' tools/fetch_llama_cpp.sh tools/build_android_native.sh
grep -q "versionName '1.1.0'" app/build.gradle
grep -q 'ACCEPT & LEARN' app/src/main/java/com/iamlegendz/hitforge/studio/HitforgeStudioActivity.java
grep -q 'EXPORT APPROVED TRAINING DATA' app/src/main/java/com/iamlegendz/hitforge/studio/HitforgeStudioActivity.java
echo 'PASS: HITFORGE v1.1 static checks'
