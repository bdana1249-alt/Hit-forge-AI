# HITFORGE Learning Policy

### Immediate
Every accepted song can become a retrieval example.

### Explicit
Every Teach instruction is stored as a preference.

### Negative feedback
Rejected generations should be stored only if the user chooses "Learn from rejection";
this avoids teaching the model accidental mistakes.

### Adapter training
Future trainer should:
1. Collect only user-approved examples.
2. Deduplicate.
3. Remove accidental boilerplate.
4. Split train/validation sets.
5. Train a versioned LoRA adapter.
6. Benchmark against the previous adapter and base model.
7. Activate only if the personal benchmark improves.
8. Keep rollback copies.

This follows the parameter-efficient approach documented by Hugging Face PEFT, where
only adapter parameters are trained while the pretrained base remains frozen.
