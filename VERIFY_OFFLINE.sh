#!/usr/bin/env bash
set -e
MANIFEST=app/src/main/AndroidManifest.xml
! grep -q 'android.permission.INTERNET' "$MANIFEST"
test -f app/src/main/cpp/hitforge_llm.cpp
grep -q 'llama_model_load_from_file' app/src/main/cpp/hitforge_llm.cpp
grep -q 'llama_sampler_sample' app/src/main/cpp/hitforge_llm.cpp
grep -q 'llama_decode' app/src/main/cpp/hitforge_llm.cpp
test -f tools/fetch_llama_cpp.sh
test -f tools/build_android_native.sh
echo "PASS: offline/native-inference static checks"
