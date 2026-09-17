#!/usr/bin/env bash
set -e
MANIFEST=app/src/main/AndroidManifest.xml
! grep -q 'android.permission.INTERNET' "$MANIFEST"
test -f app/src/main/java/com/iamlegendz/hitforge/quality/HookAnalyzer.java
test -f app/src/main/java/com/iamlegendz/hitforge/library/SongLibrary.java
test -f app/src/main/java/com/iamlegendz/hitforge/adapters/AdapterRegistry.java
test -f app/src/main/java/com/iamlegendz/hitforge/export/TrainingDatasetExporter.java
grep -q 'ACCEPT & LEARN' app/src/main/java/com/iamlegendz/hitforge/studio/HitforgeStudioActivity.java
echo "PASS: HITFORGE v1.0 static checks"
