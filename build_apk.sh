#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
: "${ANDROID_SDK_ROOT:?Set ANDROID_SDK_ROOT}"
: "${ANDROID_NDK:?Set ANDROID_NDK}"
command -v gradle >/dev/null || { echo "Gradle 8.9+ required"; exit 2; }
cd "$ROOT"
tools/fetch_llama_cpp.sh b11010
gradle --no-daemon --stacktrace :app:assembleDebug
APK="$ROOT/app/build/outputs/apk/debug/app-debug.apk"
test -s "$APK"
echo "APK: $APK"
