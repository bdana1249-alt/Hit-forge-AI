package com.iamlegendz.hitforge.ai;

import java.io.File;

public final class LocalLlmBridge {
    static { try { System.loadLibrary("hitforge_llm"); } catch (Throwable ignored) {} }
    public interface TokenListener { void onToken(String token); }
    private LocalLlmBridge() {}
    public static native String nativeRuntimeInfo();
    public static native String nativeModelInfo(String modelPath);
    public static native String nativeGenerate(String modelPath,String prompt,int maxTokens,int contextSize,float temperature,int seed);
    public static native String nativeGenerateStreaming(String modelPath,String adapterPath,String prompt,int maxTokens,int contextSize,float temperature,int seed,TokenListener listener);
    public static native String nativeAdapterInfo(String modelPath,String adapterPath);
    public static boolean modelLooksUsable(File model){return model!=null&&model.isFile()&&model.getName().toLowerCase().endsWith(".gguf")&&model.length()>1024L*1024L;}
    public static boolean adapterLooksUsable(File adapter){return adapter!=null&&adapter.isFile()&&adapter.getName().toLowerCase().endsWith(".gguf")&&adapter.length()>1024L;}
}
