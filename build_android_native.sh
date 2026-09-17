#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
: "${ANDROID_NDK:?Set ANDROID_NDK to your Android NDK root}"
"$ROOT/tools/fetch_llama_cpp.sh" "${LLAMA_TAG:-b11010}"
cd "$ROOT"
if [ ! -x "$ROOT/gradlew" ]; then
  echo "ERROR: Gradle wrapper is missing. Generate it with a matching Gradle 8.x installation before building."
  exit 2
fi
rm -rf app/.cxx app/build
"$ROOT/gradlew" :app:assembleDebug
echo "APK: $ROOT/app/build/outputs/apk/debug/app-debug.apk"
