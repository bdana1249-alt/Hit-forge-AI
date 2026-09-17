# Personal Learning

Generation and personalization are separated.

### Immediate learning
Accepted songs, edits, explicit preferences and rejected generations are stored locally
and retrieved into future prompts.

### Durable adaptation
After the user explicitly approves a dataset, a separate LoRA/PEFT training pipeline can
create a versioned adapter. Keep the base model frozen and validate each adapter against
a personal benchmark before activation.

### Rollback
Never overwrite the last-known-good adapter. Store:
adapter_001, adapter_002, ...
and allow activation/rollback.

### Privacy
No automatic upload. Training data is local unless the user explicitly exports it.
