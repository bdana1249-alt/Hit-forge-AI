package com.iamlegendz.hitforge.ai;

import android.content.Context;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class OfflineGenerationService {
    public interface Callback { void token(String piece); void done(String text); }
    private final ExecutorService pool=Executors.newSingleThreadExecutor();
    public OfflineGenerationService(Context c) {}
    public void generate(File model,File adapter,String prompt,int maxTokens,int contextSize,float temperature,int seed,Callback cb){
        pool.execute(()->{
            if(!LocalLlmBridge.modelLooksUsable(model)){if(cb!=null)cb.done("[HITFORGE] Select a valid local GGUF model first.");return;}
            String result=LocalLlmBridge.nativeGenerateStreaming(model.getAbsolutePath(),adapter==null?"":adapter.getAbsolutePath(),prompt,maxTokens,contextSize,temperature,seed,cb==null?null:new LocalLlmBridge.TokenListener(){public void onToken(String token){cb.token(token);}});
            if(cb!=null)cb.done(result);
        });
    }
    public void shutdown(){pool.shutdownNow();}
}
