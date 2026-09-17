#!/usr/bin/env bash
set -e
MANIFEST=app/src/main/AndroidManifest.xml
! grep -q 'android.permission.INTERNET' "$MANIFEST"
test -f app/src/main/java/com/iamlegendz/hitforge/studio/HitforgeStudioActivity.java
test -f app/src/main/java/com/iamlegendz/hitforge/memory/PersonalMemoryStore.java
grep -q 'nativeGenerate' app/src/main/java/com/iamlegendz/hitforge/ai/LocalLlmBridge.java
grep -q 'llama_model_load_from_file' app/src/main/cpp/hitforge_llm.cpp
grep -q 'llama_sampler_sample' app/src/main/cpp/hitforge_llm.cpp
echo "PASS: v0.9 static architecture checks"
