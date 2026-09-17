# HITFORGE v0.5 — continual personalization design

## What “learn from every session” means
HITFORGE does **not** rewrite the base model after every song. That is deliberate. Continual fine-tuning can cause catastrophic forgetting and unstable sequential personalization. Current research recommends monitoring retention and using replay/stability checks when adapting models.

HITFORGE therefore uses four layers:

1. **Session memory** — stores the user's concepts, accepted songs, rejected outputs, edits, ratings, and explicit style notes locally.
2. **Retrieval memory** — before generation, the app retrieves relevant prior examples and injects a compact personalization context into the local model prompt.
3. **Preference learning** — explicit thumbs-up/down, saved songs, and “teach HITFORGE” notes become weighted memories.
4. **Periodic adapter training** — an optional LoRA training pipeline can create a small personal adapter from accumulated examples. The base model stays unchanged.

## Why not retrain after every use?
Full training is computationally expensive, and repeated sequential adaptation can overwrite earlier capabilities. The 2026 continual-learning literature specifically highlights catastrophic forgetting and the need for stability monitoring. LoRA/PEFT is appropriate because it trains a small set of adapter parameters instead of the whole model.

## Personalization record types
- `generation`: what HITFORGE produced
- `accepted`: outputs the user explicitly kept
- `rejected`: outputs/sections the user rejected and why
- `preference`: explicit instructions such as “more internal rhyme” or “less repetition”

## Privacy
All memory is stored in the app's private Android storage. No network permission is requested. The app never uploads the memory database.

## Adapter lifecycle
Collect -> curate -> export training JSONL -> train LoRA on a capable computer -> validate against a fixed personal reference set -> import adapter -> enable/disable adapter.

Do not automatically train and activate an adapter without validation. Keep the base model and adapter separate so the user can roll back.
