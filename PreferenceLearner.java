package com.iamlegendz.hitforge.ai;

import java.util.*;

/** Local preference learner: stores explicit feedback as durable facts, never silently changes model weights. */
public final class PreferenceLearner {
    private final Map<String,Integer> signals = new LinkedHashMap<>();
    public void record(String feature, boolean liked) {
        int v = signals.getOrDefault(feature, 0);
        signals.put(feature, Math.max(-20, Math.min(20, v + (liked ? 1 : -1))));
    }
    public String profile() {
        StringBuilder b = new StringBuilder();
        for (Map.Entry<String,Integer> e : signals.entrySet()) {
            if (e.getValue() != 0) b.append(e.getKey()).append("=").append(e.getValue()).append("; ");
        }
        return b.toString();
    }
}
