#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
MANIFEST=app/src/main/AndroidManifest.xml
! grep -q 'android.permission.INTERNET' "$MANIFEST"
grep -q "abiFilters 'arm64-v8a'" app/build.gradle
grep -q "versionName '1.2.0'" app/build.gradle
grep -q 'GGUF' app/src/main/java/com/iamlegendz/hitforge/model/ModelManager.java
grep -q '8192' app/src/main/cpp/hitforge_llm.cpp
grep -q 'min(6u' app/src/main/cpp/hitforge_llm.cpp
grep -q 'DevicePreflight' app/src/main/java/com/iamlegendz/hitforge/studio/HitforgeStudioActivity.java
grep -q 'b11010' tools/fetch_llama_cpp.sh
test -f PRELAUNCH_AUDIT.md
test -f INSTALLATION_REALITY.md
echo 'PASS: HITFORGE v1.2 static prelaunch checks'
