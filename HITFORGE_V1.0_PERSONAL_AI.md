# HITFORGE v1.0 — Personal AI Engine

## Implemented architecture
- Song library
- Hook Analyzer
- Personal memory
- Accept & Learn
- Teach
- Approved training dataset export
- Adapter registry
- Personal benchmark primitives
- Native llama.cpp inference from v0.8/v0.9

## Personal AI loop
1. Generate locally.
2. Analyze.
3. Accept, reject, or edit.
4. Store approved examples.
5. Retrieve relevant examples on the next generation.
6. Export an approved JSONL dataset.
7. Train a LoRA/PEFT adapter in a controlled training environment.
8. Benchmark the new adapter against the prior version.
9. Register and activate only an approved version.
10. Keep rollback copies.

PEFT is designed for parameter-efficient adaptation: only a small set of extra parameters
is trained while the base model remains frozen. It also supports multiple adapters that
can be switched for different use cases.

## Why training is separate
The A16 is the target inference device. Full LLM adapter training can be much more
demanding than inference. HITFORGE therefore keeps the training dataset and adapter
registry local, while the actual trainer can initially run on a more capable machine.
A future Android training path can be added after profiling memory, thermal and battery
constraints.

## No automatic learning from rejected material
Rejections are not treated as positive training examples. A user must explicitly choose
to learn from a correction/rejection.
