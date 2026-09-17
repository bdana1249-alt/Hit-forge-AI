#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
DEST="$ROOT/app/src/main/cpp/llama.cpp"
TAG="${1:-b11010}"
if [ -d "$DEST/.git" ]; then
  git -C "$DEST" fetch --depth 1 origin "refs/tags/$TAG:refs/tags/$TAG"
  git -C "$DEST" checkout --detach "$TAG"
else
  rm -rf "$DEST"
  git clone --depth 1 --branch "$TAG" https://github.com/ggml-org/llama.cpp.git "$DEST"
fi
echo "Pinned llama.cpp: $(git -C "$DEST" rev-parse HEAD)"
