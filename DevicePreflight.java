package com.iamlegendz.hitforge.model;

import android.app.ActivityManager;
import android.content.Context;
import java.io.File;

/** Conservative runtime guardrails for offline LLM inference on mid-range Android phones. */
public final class DevicePreflight {
  private DevicePreflight() {}

  public static String checkModel(Context c, File model, int context) {
    if (model == null || !model.isFile()) return "No local GGUF model selected.";
    if (model.length() < 1024L * 1024L) return "GGUF file is too small or incomplete.";
    ActivityManager am=(ActivityManager)c.getSystemService(Context.ACTIVITY_SERVICE);
    int memClass=am==null?0:am.getMemoryClass();
    if (memClass > 0 && memClass <= 4096 && model.length() > 2500L*1024L*1024L)
      return "This model is too large for a conservative 4 GB-RAM configuration.";
    if (context < 512 || context > 8192) return "Context must be between 512 and 8192.";
    return "";
  }
}
